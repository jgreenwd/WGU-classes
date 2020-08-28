from Pitcher import Pitcher, get_n_clusters, select_random_pitcher
from Pitch import Pitch, PITCH_KEYS, PITCH_KEYS_REVERSE, PITCH_LABELS, STD_PITCHES_REMOVED
from utils import preprocess, filter_pitches, load_object, encode_p_throws
import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
import ipywidgets as widgets
from IPython import display
from sklearn.mixture import GaussianMixture
from sklearn.metrics import v_measure_score, adjusted_rand_score, homogeneity_score, completeness_score

PALETTE = ((0.1, 0.5, 0.1, 1.0),  # Changeup
           (1.0, 0.7, 0.1, 0.8),  # Curveball
           (0.4, 0.0, 0.8, 0.5),  # Cutter
           (0.1, 0.1, 1.0, 1.0),  # Four-seam
           (0.1, 0.6, 0.9, 1.0),  # Two-seam
           (0.1, 0.8, 0.5, 1.0),  # Sinker
           (1.0, 0.0, 0.0, 1.0),  # Slider
           (0.0, 0.0, 0.0, 0.3),  # Splitter
           (0.8, 0.4, 0.1, 1.0),  # Knucklecurve
           (0.1, 0.5, 0.1, 0.5),  # Knuckleball
           (1.0, 0.7, 0.1, 0.4),  # Eephus
           (0.4, 0.0, 0.8, 0.25),  # Forkball
           (0.1, 0.1, 1.0, 0.5),  # Screwball
           (0.1, 0.6, 0.9, 0.5),  # Unlabeled Fastball
           (0.1, 0.8, 0.5, 0.5),  # Unlabeled Atbat
           (1.0, 0.0, 0.0, 0.5),  # Pitchout
           (0.0, 0.0, 0.0, 0.15),  # Intentional ball
           (0.8, 0.4, 0.1, 0.5))  # Unknown


class Dashboard:
    def __init__(self, df, pitchers):
        # State
        self.data = df
        self.current_pitcher = 'All'
        self._selected_season = 2015
        self.pitch = Pitch()
        self.pitchers = {k: Pitcher(v[0], v[1]) for k, v in pitchers.items()}
        self.pitchers['Random'] = (select_random_pitcher(df))

        # Components
        self._controls = self._build_control_pane()
        self._tab_layout = widgets.Layout(display='flex', flex_flow='column nowrap', align_content='center',
                                          align_items='center', justify_content='space-between',
                                          height='418px', width='486px', margin='0 0 0 0')
        self._output = self._build_output_pane()
        self._output._titles = {0: 'Pitch Counts', 1: 'n-Clusters', 2: 'Cluster Plots', 3: 'Pitch Predict'}
        self._loading_gif = None
        self._set_loading_gif()

        self._dashboard = widgets.HBox(children=[self._controls, self._output],
                                       layout={'width': '700px', 'height': '480px'})

    def _set_loading_gif(self):
        file = open(f'./capstone-data/loading2.gif', 'rb')
        self._loading_gif = widgets.Image(value=file.read(), format='gif')
        file.close()

    def set_selected_season(self, season):
        self._selected_season = season

    def _build_control_pane(self):
        def _update_player(x):
            self.current_pitcher = player_select.value
            if self.current_pitcher == 'All':
                cutoff_slider_label.layout = {'width': '130px', 'margin': '4px 0 0 0', 'visibility': 'visible'}
                cutoff_slider.layout = {'width': '150px', 'margin': '0 0 0 20px', 'visibility': 'visible'}
            else:
                cutoff_slider_label.layout = {'width': '130px', 'margin': '4px 0 0 0', 'visibility': 'hidden'}
                cutoff_slider.layout = {'width': '150px', 'margin': '0 0 0 20px', 'visibility': 'hidden'}

        player_select_label = widgets.Label(value='Select Player:', layout={'width': '75%', 'margin': '1px 0 0 0'})
        player_select = widgets.ToggleButtons(options=list(self.pitchers.keys()), value='All')
        player_select.observe(_update_player, names='value')

        cutoff_slider_label = widgets.Label(value='Outer number:', layout={'width': '130px', 'margin': '4px 0 0 0'})
        cutoff_slider = widgets.IntSlider(value=7, min=5, max=10, step=1, orientation='horizontal', readout=True,
                                          layout={'width': '150px', 'margin': '0 0 0 20px'})

        def _update_seasons(x):
            seasons = sorted(list(self.pitchers[self.current_pitcher].get_seasons()))

            if len(seasons) > 1:
                seasons.append('All')

            with season_select:
                display.clear_output(wait=True)
                selector = widgets.RadioButtons(options=seasons, layout={'width': '88px', 'margin': '0 0 0 45px'})
                selector.observe((lambda s: self.set_selected_season(s.new)), names='value')
                display.display(selector)

            self._selected_season = seasons[0]

        season_select_label = widgets.Label(value='Season:', layout={'width': '133px'})
        season_select = widgets.Output()
        seasons_sub_display = widgets.VBox(children=[season_select_label, season_select],
                                           layout={'width': ' 190px', 'height': '140px'})
        _update_seasons('All')

        player_select.observe(_update_seasons, names='value')

        controls_layout = widgets.Layout(display='flex', flex_flow='column nowrap', align_content='center',
                                         align_items='flex-start', justify_content='flex-start', width='190px')

        return widgets.HBox(layout=controls_layout, children=[player_select_label, player_select,
                                                              cutoff_slider_label, cutoff_slider, seasons_sub_display])

    def _build_output_pane(self):
        return widgets.Tab(children=[self._build_tab1(), self._build_tab2(), self._build_tab3(), self._build_tab4()],
                           layout=widgets.Layout(display='flex', flex_flow='column nowrap', align_content='center',
                                                 align_items='center', justify_content='flex-end', width='505px',
                                                 height='476px'),
                           style={'description_width': 'initial'})

    def _build_tab1(self):
        output = widgets.Output()

        def _display_button_clicked(x):
            threshold = self._controls.children[3].value
            seasons = sorted(list(self.pitchers[self.current_pitcher].get_seasons()))

            ab_id_start, ab_id_end = self._get_season_range(seasons)

            with output:
                if self.current_pitcher == 'All':
                    display_pitch_counts(self.data[self.data.ab_id.between(ab_id_start, ab_id_end)], threshold)
                else:
                    p_id = self.pitchers[self.current_pitcher].get_p_id()
                    display_pitch_counts(self.data[(self.data.ab_id.between(ab_id_start, ab_id_end)) &
                                                   (self.data.pitcher_id == p_id)], threshold)
                output.clear_output(wait=True)

        output_container = widgets.HBox(children=[output], layout={'width': '440px', 'height': '410px'})
        display_button = widgets.Button(description='Display',
                                        layout={'width': '130px', 'height': '28px', 'margin': '1px 0 1px 0'})
        display_button.on_click(_display_button_clicked)

        return widgets.VBox(children=[output_container, display_button], layout=self._tab_layout)

    def _build_tab2(self):
        output = widgets.Output()

        def _estimate_button_clicked(x):
            output.clear_output(wait=True)
            player = self.pitchers[self.current_pitcher]
            p_id = player.get_p_id()
            seasons = player.get_seasons()

            if self.current_pitcher == 'All':
                with output:
                    display.display_pretty('n-Clusters for "All" pitchers is not available.', raw=True)

                return

            ab_id_start, ab_id_end = self._get_season_range(seasons)

            with output:
                display.display(self._loading_gif)
                data = self.data[(self.data.pitcher_id == p_id) & (self.data.ab_id.between(ab_id_start, ab_id_end))]
                output.clear_output(wait=True)
                plot_n_clusters(data)

        output_container = widgets.HBox(layout=widgets.Layout(display='flex', flex_flow='column nowrap', height='410px',
                                                              align_content='center', align_items='center',
                                                              justify_content='center', width='440px'),
                                        children=[output])
        estimate_button = widgets.Button(description='Estimate',
                                         layout={'width': '130px', 'height': '28px', 'margin': '1px 0 1px 0'})
        estimate_button.on_click(_estimate_button_clicked)

        return widgets.VBox(children=[output_container, estimate_button], layout=self._tab_layout)

    def _build_tab3(self):
        output = widgets.Output()

        def _render_button_clicked(x):
            output.clear_output(wait=True)
            player = self.pitchers[self.current_pitcher]
            p_id = player.get_p_id()
            seasons = player.get_seasons()

            if self.current_pitcher == 'All':
                with output:
                    display.display_pretty('Cluster plot for "All" pitchers is not available.', raw=True)

                return

            ab_id_start, ab_id_end = self._get_season_range(seasons)

            with output:
                display.display(self._loading_gif)
                data = self.data[(self.data.pitcher_id == p_id) & (self.data.ab_id.between(ab_id_start, ab_id_end))]
                output.clear_output(wait=True)
                n_clusters = get_n_clusters(data)
                plot_gmm(data, n_clusters)

        output_container = widgets.HBox(children=[output],
                                        layout=widgets.Layout(display='flex', flex_flow='column nowrap', height='410px',
                                                              align_content='center', align_items='center',
                                                              justify_content='center', width='470px'))
        render_button = widgets.Button(description='Render',
                                       layout={'width': '130px', 'height': '28px', 'margin': '1px 0 1px 0'})
        render_button.on_click(_render_button_clicked)

        return widgets.VBox(children=[output_container, render_button], layout=self._tab_layout)

    def _build_tab4(self):
        # output block
        model = load_object('pitch_type_final')
        prediction_proba = widgets.Output()
        output_container = widgets.VBox(children=[prediction_proba],
                                        layout={'width': '418px', 'height': '235px', 'margin': '0 0 0 0'})

        # control block
        row_layout = {'margin': '0 0 0 0'}
        bft = widgets.BoundedFloatText
        bft_layout = widgets.Layout(width='100px', margin='0 8px 0 0')
        bft_style = {'description_width': '28px'}
        break_layout = widgets.Layout(width='120px', margin='0 8px 0 0')
        break_style = {'description_width': '45px'}

        # -- row 1
        loc_label = widgets.Label(value='Location:', layout={'width': '98px', 'margin': '0 0 0 36px'})
        velo_label = widgets.Label(value='Velocity:', layout={'width': '98px', 'margin': '0 0 0 10px'})
        accel_label = widgets.Label(value='Accel:', layout={'width': '98px', 'margin': '0 0 0 15px'})
        break_label = widgets.Label(value='Break:', layout={'width': '98px', 'margin': '0 0 0 30px'})
        row1_container = widgets.HBox(children=[loc_label, velo_label, accel_label, break_label], layout=row_layout)

        # -- row 2
        x0 = bft(value=-0.69, disabled=True, description=' x0', layout=bft_layout, style=bft_style)
        vx0 = bft(value=2.21, min=-20, max=20, step=0.01, description='vx0', layout=bft_layout, style=bft_style)
        ax = bft(value=-2.21, min=-40, max=40, step=0.01, description=' ax', layout=bft_layout, style=bft_style)
        ba = bft(value=5.58, min=-90, max=270, step=0.01, description='angle', layout=break_layout, style=break_style)
        row2_container = widgets.HBox(children=[x0, vx0, ax, ba], layout=row_layout)

        # -- row 3
        y0 = bft(value=50, disabled=True, description=' y0', layout=bft_layout, style=bft_style)
        vy0 = bft(value=-128, min=-150, max=-50, step=0.01, description='vy0', layout=bft_layout, style=bft_style)
        ay = bft(value=26.3, min=0.001, max=50, step=0.01, description=' ay', layout=bft_layout, style=bft_style)
        bl = bft(value=6.67, disabled=True, step=0.01, description='length', layout=break_layout, style=break_style)
        row3_container = widgets.HBox(children=[y0, vy0, ay, bl], layout=row_layout)

        # -- row 4
        z0 = bft(value=5.8, disabled=True, description=' z0', layout=bft_layout, style=bft_style)
        vz0 = bft(value=-4.35, min=-20, max=20, step=0.01, description='vz0', layout=bft_layout, style=bft_style)
        az = bft(value=-22.8, min=-50, max=10, step=0.01, description=' az', layout=bft_layout, style=bft_style)
        by = bft(value=23.7, disabled=True, description='y', layout=break_layout, style=break_style)
        row4_container = widgets.HBox(children=[z0, vz0, az, by], layout=row_layout)

        # -- row 5
        spin_label = widgets.Label(value='_____________Spin:____________',
                                   layout={'width': '200px', 'margin': '0 0 0 16px'})
        speed_label = widgets.Label(value='______________Speed:_____________',
                                    layout={'width': '240px', 'margin': '0 0 0 10px'})
        row5_container = widgets.HBox(children=[spin_label, speed_label], layout=row_layout)

        # -- row 6
        spn_dr = bft(value=178.9, min=0, max=360, step=0.01, description='dir', layout=bft_layout, style=bft_style)
        spn_rt = bft(value=1725.7, min=2, max=6500, step=0.01, description='rate', layout=bft_layout, style=bft_style)
        start_speed = bft(value=88.25, description='Start', disabled=True, style={'description_width': '35px'},
                          layout={'width': '100px', 'margin': '0 0 0 0'})
        end_speed = bft(value=81, description='End', disabled=True, style={'description_width': '28px'},
                        layout={'width': '100px', 'margin': '0 0 0 30px'})
        row6_container = widgets.HBox(children=[spn_dr, spn_rt, start_speed, end_speed], layout=row_layout)

        # -- row 7
        pfx_x = widgets.HTML(value='0.00', layout={'width': '100px', 'margin': '10px 0 0 0'}, description='pfx_x',
                             style={'description_width': '45px'})
        pfx_z = widgets.HTML(value='0.00', layout={'width': '70px', 'margin': '10px 0 0 0'}, description='pfx_z',
                             style={'description_width': '45px'})
        p_throws = widgets.RadioButtons(options=['R', 'L'], description='Hand:', layout={'width': '120px', })
        calculate_button = widgets.Button(description='Calculate', layout={'width': '120px', 'margin': '5px 0 0 20px'})
        row7_container = widgets.HBox(children=[pfx_x, pfx_z, p_throws, calculate_button],
                                      layout={'margin': '10px 0 0 10px'})

        def _get_prediction_results(probs, label):
            max_index = np.argmax(probs)
            fig, axis = plt.subplots(figsize=(9, 3))
            axis.set_title(label, fontsize=16, y=-0.3)
            axis.set_ylabel('Pitch_Type Probability')
            axis.bar(np.arange(probs.size), probs[0].tolist())
            axis.set_ylim((0, 1))
            axis.get_children()[max_index].set_color('r')
            axis.set_xticks(np.arange(probs.size))
            axis.set_xticklabels(['CH', 'CU', 'FC', 'FF', 'FT', 'SI', 'SL'])
            plt.show()

        def _calculate_button_clicked(x):
            self.pitch = Pitch(x0.value, y0.value, z0.value, vx0.value, vy0.value, vz0.value,
                               ax.value, ay.value, az.value)
            pfx_x.value = f'{self.pitch.get_pfx_x():3.2f}'
            pfx_z.value = f'{self.pitch.get_pfx_z():3.2f}'

            p_df = pd.DataFrame(np.array([[az.value, vy0.value, p_throws.value, pfx_z.value,
                                           spn_dr.value, spn_rt.value, ba.value]]),
                                columns=['az', 'vy0', 'p_throws', 'pfx_z', 'spin_dir', 'spin_rate', 'break_angle'])

            p_df = encode_p_throws(p_df)

            self.pitch.set_pitch_type(PITCH_KEYS_REVERSE[model.predict(p_df)[0]])

            label = PITCH_LABELS[self.pitch.get_pitch_type()]
            proba = model.predict_proba(p_df)

            with prediction_proba:
                graph = (_get_prediction_results(proba, label),)
                prediction_proba.clear_output(wait=True)
                display.display_png(graph)

        calculate_button.on_click(_calculate_button_clicked)

        control_container = widgets.VBox(children=[row1_container, row2_container, row3_container, row4_container,
                                                   row5_container, row6_container, row7_container],
                                         layout={'width': '472px', 'height': '325px', 'margin': '0 0 0 0'})

        result_layout = widgets.Layout(display='flex', flex_flow='column nowrap', align_content='center',
                                       align_items='center', justify_content='flex-end',
                                       height='418px', width='472px', margin='0 0 0 0')

        return widgets.VBox(children=[output_container, control_container], layout=result_layout)

    def _get_season_range(self, seasons):
        if self._selected_season == 'All':
            begin = seasons[0] * 1000000
            end = (seasons[len(seasons) - 1] + 1) * 1000000
        else:
            begin = self._selected_season * 1000000
            end = (self._selected_season + 1) * 1000000

        return begin, end

    def render_dashboard(self):
        return self._dashboard


def display_pitch_counts(df, cutoff=9):
    """ Render concentric pie graphs of pitch_types in dataframe.

        :param df:     (DataFrame) valid pitch data
        :param cutoff: (Float) minimum threshold value to include in plot (Default 0.01)
    """
    _pitch_total = len(df)

    _num_pitchers = len(np.unique(df.pitcher_id))

    _pitch_dict = df.pitch_type.value_counts().to_dict()

    _seasons_start = df.ab_id.min() // 1000000
    _seasons_end = df.ab_id.max() // 1000000
    _seasons = _seasons_start if (_seasons_start == _seasons_end) else f'{_seasons_start} - {_seasons_end}'

    width = 0.3
    fig = plt.figure(figsize=(9, 8.4))
    fig.suptitle(f'Pitch Type Distribution According to MLB-AM Neural Net ({_seasons})\n'
                 f'Total pitches in sample: {_pitch_total}', fontsize=14, y=0.86)
    ax = fig.add_subplot(111)

    if _num_pitchers > 1:
        _outer_pitch_dict = {k: v for k, v in _pitch_dict.items() if k in list(PITCH_KEYS.keys())[:cutoff]}
        _inner_pitch_dict = {k: v for k, v in _pitch_dict.items() if k in list(PITCH_KEYS.keys())[cutoff:-6]}
        _high_x, _high_y = zip(*[[k, v] for k, v in _outer_pitch_dict.items()])
        _low_x, _low_y = zip(*[[k, v] for k, v in _inner_pitch_dict.items()])

        inner_pie, _, _ = ax.pie(x=_low_y, autopct=lambda pct: f'{pct * 0.01 * len(_low_x):2.2f}%', pctdistance=0.5,
                                 labels=_low_x, colors=[PALETTE[PITCH_KEYS[pitch]] for pitch in _low_x],
                                 labeldistance=0.0, textprops={'fontsize': 12, 'weight': 'bold', 'color': 'white'})
        plt.setp(inner_pie, width=width, edgecolor='snow', radius=1 - width - 0.01)

    else:
        _pitch_dict = {k: v for k, v in _pitch_dict.items() if k in list(PITCH_KEYS.keys())[:13]}
        _high_x, _high_y = zip(*[[k, v] for k, v in _pitch_dict.items()])

    outer_pie, _, x = ax.pie(x=_high_y, labels=_high_x, labeldistance=0.0, pctdistance=0.88, autopct='%1.1f%%',
                             textprops={'fontsize': 12, 'weight': 'bold', 'color': 'white'},
                             colors=[PALETTE[PITCH_KEYS[pitch]] for pitch in _high_x])
    plt.setp(outer_pie, width=width, edgecolor='snow', radius=1)
    ax.legend(loc='center', ncol=2, framealpha=0.0)

    plt.show()


def plot_n_clusters(df, n_min=2, n_max=10):
    """ Render estimate of n_clusters using Gaussian Mixture Model

        :param df:    (DataFrame) valid pitch data
        :param n_min: (Integer) minimum number of clusters to test
        :param n_max: (Integer) maximum number of clusters to test
    """
    from sklearn.preprocessing import robust_scale as scale
    from utils import preprocess
    from Pitcher import _get_h_and_c_scores
    import matplotlib.ticker as ticker

    try:
        if len(df) > 250000:
            raise OverflowError('plot_n_clusters(): DataFrame is too large to process. Limit 250,000 entries')

        _x, _y = preprocess(df, accuracy=True, outliers=True)
        _x = scale(_x)

        if len(_x) < 1000:
            raise EOFError('plot_n_clusters(): DataFrame contains insufficient number of quality data points')

        # clustering metrics
        _homogeneity = dict({i: 0 for i in range(n_min, n_max)})
        _completeness = dict({i: 0 for i in range(n_min, n_max)})

        for n in range(n_min, n_max):
            _homogeneity[n], _completeness[n] = _get_h_and_c_scores(_x, _y, n)

        # ---- estimated number of pitch types in sample ----
        n_clusters = get_n_clusters(df)

        # ----- PLOT METRICS -----
        fig, ax = plt.subplots(figsize=(10, 3))
        fig.suptitle(f'Gaussian Mixture Model    Sample size: {len(_x)}', fontsize=15)

        ax.set_ylim((0, 1))
        ax.set_xlabel('Number of Pitch Types', fontsize=13)
        ax.xaxis.set_major_locator(ticker.MaxNLocator(integer=True))
        ax.plot(list(_homogeneity.keys()), list(_homogeneity.values()))
        ax.plot(list(_completeness.keys()), list(_completeness.values()))
        leg1 = ax.legend(['Homogeneity', 'Completeness'], loc='lower left', fontsize=12, frameon=False)
        plt.gca().add_artist(leg1)
        vert1 = plt.axvline(len(np.unique(_y)), color='#005D00', linestyle=(0, (1, 1.5)), zorder=5,
                            linewidth=5, label='n-Clusters: MLB-AM')
        vert2 = plt.axvline(n_clusters, color='#D3D3D3', linestyle='-', zorder=1,
                            linewidth=3, label='n-Clusters: TLaP')
        ax.legend(handles=[vert1, vert2], loc='lower right', fontsize=12, frameon=False)

        plt.show()

    except EOFError as err:
        print(err)
    except OverflowError as err:
        print(err)


def _sync_pitch_colors(dictionary):
    """ Return a List of Integers representing color indices in the Palette

        :param dictionary: (Dict) pitch counts
        """
    return [PITCH_KEYS[k] for k in dictionary]


def _draw_ellipse(position, covariance, ax, **kwargs):
    """ Draw an ellipse with a given position and covariance """
    # SOURCE: https://jakevdp.github.io/PythonDataScienceHandbook/05.12-gaussian-mixtures.html

    from matplotlib.patches import Ellipse

    # Convert covariance to principal axes
    if covariance.shape == (2, 2):
        u, s, vt = np.linalg.svd(covariance)
        angle = np.degrees(np.arctan2(u[1, 0], u[0, 0]))
        width, height = 2 * np.sqrt(s)
    else:
        angle = 0
        width, height = 2 * np.sqrt(covariance)

    # Draw the Ellipse
    for nsig in range(1, 4):
        ax.add_patch(Ellipse(position, nsig * width, nsig * height, angle, **kwargs))


def _get_cluster_centers(model, arr):
    """ Find & return cluster centers

        :param model: (GMM model instance)
        :param arr:   (ndarray) preprocessed, filtered, & scaled data

        :return: (ndarray) cluster centers
    """
    import scipy.stats

    centers = np.empty(shape=(model.n_components, arr.shape[1]))
    for i in range(model.n_components):
        density = scipy.stats.multivariate_normal(cov=model.covariances_[i], mean=model.means_[i]).logpdf(arr)
        centers[i, :] = arr[np.argmax(density)]

    return centers


def _build_gmm_plot(x, y, model, scores, counts, opacity):
    """ Render 2d plot of DataFrame using Gaussian Mixture Model with metrics and pitch counts

        :param x:       (Ndarray)
        :param y:       (Ndarray)
        :param model:   (GMM model reference)
        :param scores:  (Dictionary) of {Metric: Result} clustering metrics
        :param counts:  (Dictionary) of {Name: Count} pitch counts
        :param opacity: (Float)
    """
    from matplotlib.colors import ListedColormap

    # ----- PLOT FIGURE -----
    fig = plt.figure(figsize=(8, 8), facecolor='black')

    _centers = _get_cluster_centers(model, x)
    _scaled_dot_size = (3 * model.predict_proba(x).max(1) ** 3)

    # -- Cluster display
    ax0 = fig.add_subplot(111, facecolor='black')
    ax0.scatter(x[:, 0], x[:, 1], c=y, s=_scaled_dot_size, cmap=ListedColormap(PALETTE, N=(int(np.max(y) + 1))))
    ax0.scatter(_centers[:, 0], _centers[:, 1], s=100, marker='o', color=(1, 0.5, 0.8, 1),
                zorder=3, label='Cluster centers')
    legend = ax0.legend(bbox_to_anchor=(0, 0.01, 0.2, 0.05), framealpha=0, prop={'size': 14, 'weight': 600})
    ax0.set_ylim((ax0.get_ylim()[0], (np.max(_centers[:, 1]) * 2)))
    for text in legend.get_texts():
        plt.setp(text, color='snow')

    # -- add Decision Boundaries
    w_factor = opacity / model.weights_.max()
    for pos, covar, w in zip(model.means_, model.covariances_, model.weights_):
        _draw_ellipse(pos, covar, ax0, alpha=w * w_factor, color='snow', zorder=2)

    # -- Metrics overlay
    ax1 = fig.add_subplot(331, facecolor=(0, 0, 0, 0))
    ax1.barh(list(scores.keys()), scores.values(), color=(0.6, 0.6, 0.6, 0.5), alpha=0.55, edgecolor=(0, 0, 0, 0))
    ax1.tick_params(axis='x', labelsize=0)
    ax1.set_yticklabels([f'{v:0.2f} - {k}' for k, v in scores.items()], x=0.07,
                        c='snow', weight=600, ha='left', fontsize=14)
    ax1.set_position([0, 0.65, 0.98, 0.25])

    # -- Pitch counts overlay
    ax2 = fig.add_subplot(339, facecolor=(0, 0, 0, 0))
    ax2.barh(list(counts.keys()), [-1 * x for x in counts.values()], edgecolor=(0, 0, 0, 0),
             color=[PALETTE[c] for c in _sync_pitch_colors(counts)], alpha=0.65)
    ax2.set_yticks(np.arange(len(counts)))
    ax2.set_yticklabels(counts.keys(), c='snow', ha='center', x=0.975, weight=600, fontsize=14)
    ax2.tick_params(axis='x', labelsize=0)
    ax2.set_position([0.5, 0.1, 0.5, 0.25])
    for spine in ['top', 'right', 'bottom', 'left']:
        ax1.spines[spine].set_visible(False)
        ax2.spines[spine].set_visible(False)

    plt.show()


def plot_gmm(df, n_clusters=None, xaxis='pfx_x', yaxis='vy0', opacity=0.25):
    """ Render DataFrame using Gaussian Mixture Model

        :param df:         (DataFrame) pitch data
        :param n_clusters: (Integer) number of clusters to plot (Default None)
        :param xaxis:      (String) column label in df to use for x-axis in plot (Default pfx_x)
        :param yaxis:      (String) column label in df to use for y-axis in plot (Default vy0)
        :param opacity:    (Float) decision boundary opacity
    """
    if xaxis not in df.columns:
        raise AttributeError(f'plot_gmm: {xaxis} not found in DataFrame')
    if yaxis not in df.columns:
        raise AttributeError(f'plot_gmm: {yaxis} not found in DataFrame')

    from sklearn.preprocessing import robust_scale as scale

    try:
        # Record & sort pitch counts alphabetically
        _pitch_counts = filter_pitches(df).pitch_type.value_counts().to_dict()
        _pitch_counts = {k: v for k, v in _pitch_counts.items() if k not in STD_PITCHES_REMOVED}

        _x, _y = preprocess(df, confidence=False, accuracy=False, outliers=True)

        # Remove all unused features
        _x = _x.filter([xaxis, yaxis])
        _x = scale(_x)

        n_clusters = n_clusters if n_clusters else len(np.unique(_y))

        # Instantiate and score model
        _model = GaussianMixture(n_components=n_clusters, covariance_type='full')
        _preds = _model.fit_predict(_x, _y)
        _scores = {'V-measure': v_measure_score(_y, _preds),
                   'Completeness': completeness_score(_y, _preds),
                   'Homogeneity': homogeneity_score(_y, _preds),
                   'ARI': adjusted_rand_score(_y, _preds)}

        _build_gmm_plot(_x, _y, _model, _scores, _pitch_counts, opacity)

    except EOFError:
        raise EOFError('plot_gmm: DataFrame lacks sufficient quality data.')
