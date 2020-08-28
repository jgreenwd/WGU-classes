import warnings
import Pitch
import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
import seaborn as sns

from pickle import dump, load

# preprocessing tools
from sklearn.pipeline import make_pipeline
from sklearn.preprocessing import RobustScaler
from sklearn.kernel_approximation import RBFSampler

# model selection & training tools
from sklearn.model_selection import RandomizedSearchCV, GridSearchCV, train_test_split

# classifier models
from sklearn.neighbors import KNeighborsClassifier
from sklearn.ensemble import RandomForestClassifier
from sklearn.svm import LinearSVC
from sklearn.linear_model import SGDClassifier, LogisticRegression
from sklearn.neural_network import MLPClassifier

# classification metrics
from sklearn.metrics import confusion_matrix, precision_score, accuracy_score, balanced_accuracy_score

# -------------------------------------------------------- #
#                     GLOBAL CONSTANTS                     #
# -------------------------------------------------------- #

STD_FEATURES_REMOVED = ['ab_id', 'pitcher_id', 'type_confidence', 'sz_top', 'sz_bot', 'nasty', 'zone',
                        'type_confidence', 'inning', 'x', 'y', 'x0', 'y0', 'z0', 'vx0', 'vy0', 'ax', 'ay',
                        'px', 'pz', 'start_speed', 'end_speed', 'break_length', 'break_y', 'pfx_x']

# -------------------------------------------------------- #
# FUNCTIONS FOR BUILDING SQL DATABASE FROM KAGGLE/MLB INFO #
# -------------------------------------------------------- #

def insert_player_names(file_loc, cur, db):
    """ Parse file_loc.csv and insert contents to MySQL DB

        :param file_loc: (String) filename path
        :param cur:      (Object) reference to active MySQL cursor object
        :param db:       (Object) reference to active MySQL DB
    """
    player_names = pd.read_csv(file_loc)
    for player in player_names.values:
        cur.execute(f'INSERT INTO player_names (id, first_name, last_name) '
                    f'VALUES ({player[0]}, \"{player[1]}\", \"{player[2]}\");')
        db.commit()


def insert_ejections(file_loc, cur, db):
    """ Parse file_loc.csv and insert contents to MySQL DB

        :param file_loc: (String) filename path
        :param cur:      (Object) reference to active MySQL cursor object
        :param db:       (Object) reference to active MySQL DB
    """
    ejections = pd.read_csv(file_loc)
    for ej in ejections.values:
        cur.execute(f'INSERT INTO ejections (ab_id, description, event_num, g_id, '
                    f'player_id, incident_date, balls_and_strikes, valid, team, is_home_team) '
                    f'VALUES ({ej[0]}, \"{ej[1]}\", {ej[2]}, {ej[3]}, {ej[4]}, '
                    f'STR_TO_DATE("{ej[5]}", "%m/%d/%Y"), '
                    f'\"{ej[6]}\", \"{ej[7]}\", \"{ej[8]}\", {ej[9] == "True"});')
        db.commit()


def insert_games(file_loc, cur, db):
    """ Parse file_loc.csv and insert contents to MySQL DB

        :param file_loc: (String) filename path
        :param cur:      (Object) reference to active MySQL cursor object
        :param db:       (Object) reference to active MySQL DB
    """
    games = pd.read_csv(file_loc)
    for g in games.values:
        cur.execute(f'INSERT INTO games (attendance, away_final_score, '
                    f'away_team, g_id, home_final_score, home_team, '
                    f'umpire_1B, umpire_2B, umpire_3B, umpire_HP, venue_name, '
                    f'weather, wind, delay, game_datetime, elapsed_time) '
                    f'VALUES ({g[0]}, {g[1]}, "{g[2]}", {g[5]}, {g[6]}, "{g[7]}", '
                    f'"{g[9]}", "{g[10]}", "{g[11]}", "{g[12]}", "{g[13]}", "{g[14]}", '
                    f'"{g[15]}", {g[16]}, STR_TO_DATE("{g[3] + " " + g[8]}", '
                    f'"%Y-%m-%d %h:%i %p"), {g[4]}); ')
        db.commit()


def insert_atbats(file_loc, cur, db):
    """ Parse file_loc.csv and insert contents to MySQL DB

        :param file_loc: (String) filename path
        :param cur:      (Object) reference to active MySQL cursor object
        :param db:       (Object) reference to active MySQL DB
    """
    at_bats = pd.read_csv(file_loc)
    for x in at_bats.values:
        cur.execute(f'INSERT INTO atbats (ab_id, batter_id, result, g_id, '
                    f'inning, outs, p_score, p_throws, pitcher_id, b_stands, top_of_inning) '
                    f'VALUES ({x[0]}, {x[1]}, "{x[2]}", {x[3]}, {x[4]}, {x[5]}, '
                    f'{x[6]}, "{x[7]}", {x[8]}, "{x[9]}", {x[10] == "True"});')
        db.commit()


def insert_pitches(file_loc, cur, db):
    """ Parse file_loc.csv and insert contents to MySQL DB

        :param file_loc: (String) filename path
        :param cur:      (Object) reference to active MySQL cursor object
        :param db:       (Object) reference to active MySQL DB
    """
    pitches = pd.read_csv(file_loc)
    for x in pitches.values:
        cur.execute(f'INSERT INTO pitches (px, pz, start_speed, end_speed, spin_rate, spin_dir,'
                    f'break_angle, break_length, break_y, ax, ay, az,'
                    f'sz_bot, sz_top, type_confidence, vx0, vy0, vz0, '
                    f'x, x0, y, y0, z0, pfx_x, pfx_z, nasty, zone, pitch_code, '
                    f'pitch_class, pitch_type, event_num, b_score, ab_id, '
                    f'b_count, s_count, outs, pitch_num, on_1b, on_2b, on_3b) '
                    f'VALUES ({x[0]}, {x[1]}, {x[2]},{x[3]}, {x[4]}, {x[5]}, '
                    f'{x[6]}, {x[7]}, {x[8]}, {x[9]}, {x[10]}, {x[11]}, '
                    f'{x[12]}, {x[13]}, {x[14]}, {x[15]}, {x[16]}, {x[17]}, '
                    f'{x[18]}, {x[19]}, {x[20]}, {x[21]}, {x[22]}, {x[23]}, '
                    f'{x[24]}, {x[25]}, {x[26]}, "{x[27]}", "{x[28]}", "{x[29]}", '
                    f'{x[30]}, {x[31]}, {x[32]}, {x[33]}, {x[34]}, {x[35]}, {x[36]}, '
                    f'{int(x[37])}, {int(x[38])}, {int(x[39])});')
        db.commit()


def export_pitches_csv(file_loc):
    """ Export measured & derived pitch data from all seasons into CSV file

        :param file_loc: (String) filename path
    """
    import csv
    import mysql.connector

    conn = mysql.connector.connect(
        host='localhost',
        user='python',
        passwd='123456',
        database='capstone'
    )

    cursor = conn.cursor()
    cursor.execute(f'SELECT ab_id, pitcher_id, p_throws, pitch_type, type_confidence, sz_bot, sz_top, nasty, zone, '
                   f'start_speed, end_speed, spin_rate, spin_dir, break_angle, break_length, break_y, pfx_x, pfx_z, '
                   f'x0, y0, z0, vx0, vy0, vz0, ax, ay, az, px, pz, x, y, inning '
                   f'FROM pitches JOIN atbats USING (ab_id);')

    with open(file_loc, "w", newline='') as csv_file:
        csv_writer = csv.writer(csv_file)
        csv_writer.writerow([i[0] for i in cursor.description])
        csv_writer.writerows(cursor)

    conn.disconnect()


# -------------------------------------------------------- #
# ------ FUNCTIONS USED IN PROJECT JUPYTER NOTEBOOK ------ #
# -------------------------------------------------------- #

# *************** INFO DISPLAY FUNCTIONS ***************** #
def display_mvmt_velo(df):
    """ Render scatter plot of pitch types ( pfx_x by vy0 ) """
    _seasons_start = str(df.ab_id.min())[:4]
    _seasons_end = str(df.ab_id.max())[:4]

    _seasons = _seasons_start if (_seasons_start == _seasons_end) else (_seasons_start + ' - ' + _seasons_end)

    _pitcher_id = np.unique(df.pitcher_id)
    _pitcher_throws = np.unique(df.p_throws)

    if len(_pitcher_id) == 1:
        _figsize = (7, 7)
        _plot_grid = (111,)
        _title = (f'Pitcher ID: {_pitcher_id}',)
        _scatter = ((df.pfx_x, df.vy0),)
    elif len(_pitcher_throws) == 1:
        _figsize = (7, 7)
        _plot_grid = (111,)
        _title = ('Right-Handed',) if _pitcher_throws == 'R' else ('Left-Handed',)
        _scatter = ((df.pfx_x, df.vy0),)
    else:
        _figsize = (14, 7)
        _plot_grid = (121, 122)
        _title = ('Left-Handed', 'Right-Handed')
        _df_left = df[df.p_throws == 'L']
        _df_right = df[df.p_throws == 'R']
        _scatter = ((_df_left.pfx_x, _df_left.vy0), (_df_right.pfx_x, _df_right.vy0))

    fig = plt.figure(figsize=_figsize)
    fig.suptitle(f'Movement / Velocity Plot ({_seasons})', fontsize=15)

    for i in range(len(_plot_grid)):
        ax = fig.add_subplot(_plot_grid[i], ylim=(-160, -80), xlabel='pfx_x', ylabel='vy0',
                             title=_title[i])
        ax.tick_params(labelsize=0)
        ax.scatter(_scatter[i][0], _scatter[i][1], s=1)

    plt.show()


def display_pitcher_summary(df):
    """ Render scatter plot & pie graph of pitch_types for individual pitcher in DataFrame

        :param df:      (DataFrame) valid pitch data for a single pitcher
    """
    _pitcher_id = np.unique(df.pitcher_id)
    if len(_pitcher_id) > 1:
        raise AttributeError('display_pitcher_summary: Invalid request. DataFrame contains > 1 pitcher_id')

    df = df[df.type_confidence == 2]  # limit to quality pitches
    _pitch_total = len(df)  # total numer of pitches
    _pitch_dict = df.pitch_type.value_counts().to_dict()
    _pitch_dict['other'] = 0

    # Tally pitch_types that represent less than 5% of the total
    for k in list(_pitch_dict.keys()):
        if _pitch_dict[k] < 5:
            _pitch_dict['other'] += _pitch_dict.pop(k)
    if _pitch_dict['other'] == 0:
        del _pitch_dict['other']

    fig = plt.figure(figsize=(14, 7))
    fig.suptitle(f'Pitcher Summary for Pitcher_id: {_pitcher_id[0]}  -  Sample size: {len(df)}', fontsize=15, y=1.01)
    ax0 = fig.add_subplot(121, ylim=(-160, -80))
    ax0.tick_params(labelsize=0)
    ax0.scatter(df.pfx_x, df.vy0, s=2)
    ax1 = fig.add_subplot(122)
    ax1.pie(_pitch_dict.values(), labels=_pitch_dict.keys(), wedgeprops={'width': 0.4},
            startangle=350, autopct='%1.1f%%', pctdistance=0.88, shadow=True,
            textprops={'fontsize': 12, 'weight': 'bold', 'color': 'white'})
    ax1.legend(loc='center', edgecolor='None', fontsize=12, labelspacing=0.2)

    plt.show()


# ****************** DATA PREPROCESSING ****************** #
def _get_batters_faced(df, pid):
    """ Return number of batters faced in gameplay by pid

        :param df:  (DataFrame) valid pitch data
        :param pid: (Integer) valid pitcher_id

        :return:    (Integer) number of batters faced in gameplay by pid
    """
    if len(df) == 0:
        raise EOFError('_get_batters_faced: DataFrame is empty')

    return len(np.unique(df.ab_id[df.pitcher_id == pid]))


def filter_batters_faced(df, minimum=50):
    """ Return pitches from pitchers with >=minimum batters faced

        :param df:      (DataFrame) valid pitch data
        :param minimum: (Integer) minimum number of batters faced in gameplay (Default 50)

        :return:        (DataFrame) filtered pitch data
    """
    if len(df) == 0:
        raise EOFError('_filter_batters_faced: DataFrame is empty')

    for _pid in np.unique(df.pitcher_id):
        if _get_batters_faced(df, _pid) < minimum:
            df = df[df.pitcher_id != _pid]

    return df


def _get_pitch_count(df, pid):
    """ Return number of pitches thrown in gameplay by pid

        :param df:  (DataFrame) valid pitch data
        :param pid: (Integer) valid pitcher_id

        :return:    (Integer) number of pitches thrown in gameplay by pid
    """
    if len(df) == 0:
        raise EOFError('_get_pitch_count: DataFrame is empty')

    return len(df[df.pitcher_id == pid])


def filter_pitch_count(df, minimum=100):
    """ Return pitches from pitchers with >=minimum pitches thrown in gameplay

        :param df:      (DataFrame) valid pitch data
        :param minimum: (Integer) minimum number of pitches thrown (Default 100)

        :return:        (DataFrame)
    """
    if len(df) == 0:
        raise EOFError('_filter_pitch_count: DataFrame is empty')

    for _pid in np.unique(df.pitcher_id):
        if _get_pitch_count(df, _pid) < minimum:
            df = df[df.pitcher_id != _pid]

    return df


def filter_outliers(df):
    """ Return pitches with attributes within MINMAX_ATTRIBUTES

        :param df: (DataFrame) valid pitch data

        :return:   (DataFrame) filtered pitch data
    """

    minmax = {
        'start_speed': (30, 105),
        'end_speed': (30, 100),
        'spin_dir': (0, 360),
        'spin_rate': (0, 6000),
        'break_angle': (-90, 270),
        'break_length': (0, 60),
        'break_y': (0, 25),
        'x0': (-10, 10),
        'y0': (45, 50),
        'z0': (0, 10),
        'vx0': (-25, 25),
        'vy0': (-155, -50),
        'vz0': (-20, 20),
        'ax': (-40, 40),
        'ay': (0, 50),
        'az': (-75, 20)
    }

    for column, val in minmax.items():
        df = df[df[column].between(val[0], val[1])]

    return df


def filter_wild_pitches(df, dist=5):
    """ Return pitches within 'dist' feet of the strike zone center

        :param df:   (DataFrame) valid pitch data
        :param dist: (Integer) limiting distance from strike zone center (Default 4)

        :return:     (DataFrame) filtered pitch data
    """
    if len(df) == 0:
        raise EOFError('_filter_wild_pitches: DataFrame is empty')

    return df[(df.px.abs() < dist) & (df.pz.abs() < dist)]


def filter_features(df, remove=None):
    """ Return pitches with unwanted features removed

        :param df:     (DataFrame) valid pitch data
        :param remove: (List) features to remove
    """
    if len(df) == 0:
        raise EOFError('_filter_features: DataFrame is empty')

    # <List> of typical pitch features to retain
    _features = ['pitch_type', 'start_speed', 'end_speed', 'sz_top', 'sz_bot', 'spin_rate', 'spin_dir',
                 'break_length', 'break_angle', 'break_y', 'x0', 'y0', 'z0', 'vx0', 'vy0', 'vz0',
                 'ax', 'ay', 'az', 'x', 'y', 'px', 'pz', 'pfx_x', 'pfx_z', 'p_throws']

    return df.filter(_features if not remove else list(set(_features) - set(remove)))


def encode_p_throws(df):
    """ Return DataFrame with p_throws categorical strings encoded as integers """
    if len(df) == 0:
        raise EOFError('_encode_p_throws: DataFrame is empty')

    return df.replace({'p_throws': {'R': 0, 'L': 1}})


def filter_pitches(df, remove=None):
    """ Return pitches whose pitch_type is not found in STD_PITCHES_REMOVED

        :param df:     (DataFrame) valid pitch data
        :param remove: (List) pitch types to remove from df
    """
    if len(df) == 0:
        raise EOFError('_filter_pitches: DataFrame is empty')

    _pitches_to_remove = set(Pitch.STD_PITCHES_REMOVED).union(remove) if remove else set(Pitch.STD_PITCHES_REMOVED)

    return df[~df.pitch_type.isin(list(_pitches_to_remove))]


def encode_pitch_type(df):
    """ Return DataFrame with pitch_type categorical strings encoded as integers

        :param df:   (DataFrame) valid pitch data
    """
    if len(df) == 0:
        raise EOFError('_encode_pitch_type: DataFrame is empty')

    return df.replace(Pitch.PITCH_KEYS)


def separate_target_values(df):
    """ Return tuple of (modified DataFrame, extracted target values) """
    if len(df) == 0:
        raise EOFError('_separate_target_values(): DataFrame is empty')

    y = df.pitch_type.values
    x = df.drop('pitch_type', axis=1)

    return x, y


def preprocess(df, remove=None, confidence=False, accuracy=False, outliers=False):
    """ Return (X, y) processed for training/testing

        :param df:         (DataFrame) valid pitch data
        :param remove:     (List) features to be removed from DataFrame prior to processing
        :param confidence: (Boolean) remove pitches with low type_confidence?
        :param accuracy:   (Boolean) remove wild pitches?
        :param outliers:   (Boolean) remove pitches with attributes that are likely errors

        :return:           (Tuple: (X, y)) pitch data processed & encoded for training/testing
     """
    columns = df.columns

    for feature in ['type_confidence', 'pitcher_id', 'px', 'pz', 'p_throws', 'pitch_type']:
        if feature not in columns:
            raise AttributeError(f'preprocess: Invalid DataFrame - {feature} feature is not present.')

    try:
        _x = df.dropna()

        if confidence:
            _x = _x[_x.type_confidence >= 1]

        if len(np.unique(_x.pitcher_id)) > 1:
            _x = filter_batters_faced(_x)
            _x = filter_pitch_count(_x)

        if accuracy:
            _x = filter_wild_pitches(_x)

        if outliers:
            _x = filter_outliers(_x)

        _x = filter_features(_x, remove=remove)
        _x = encode_p_throws(_x)
        _x = filter_pitches(_x)
        _x = encode_pitch_type(_x)
        _x, _y = separate_target_values(_x)

        return _x, _y

    except EOFError:
        raise EOFError('preprocess(): DataFrame lacks sufficient quality data.')


# ****************** FEATURE  SELECTION ****************** #
def plot_corr_matrix(df):
    """ Render diagonal correlation matrix

        :param df:    (DataFrame) valid pitch data
    """
    df = filter_features(df, ['ab_id', 'pitcher_id', 'type_confidence', 'sz_top', 'sz_bot', 'nasty', 'zone', 'x', 'y',
                              'type_confidence', 'inning'])
    df = encode_p_throws(df)
    df = encode_pitch_type(df)
    corr = df.corr()

    fig, ax = plt.subplots(figsize=(10, 8))
    fig.suptitle(f'Masked Correlation Matrix', fontsize=12, y=0.9)
    mask = np.triu(np.ones_like(corr, dtype=np.bool))
    cmap = sns.diverging_palette(250, 150, s=100, sep=10, as_cmap=True)
    sns.heatmap(corr, mask=mask, cmap=cmap, center=0, linewidths=1, cbar_kws={"shrink": .5}, ax=ax)


def _identify_min_variance(df):
    """ Return List of columns with variance below threshold

        :param df:       (DataFrame) valid pitch data
    """
    df = df.drop(['p_throws', 'pitch_type'], axis=1)

    _variance = df.var()
    _columns = df.columns

    return [(_columns[i], _variance[i]) for i in range(len(_variance))]


def plot_min_variance(df, cutoff=10.0):
    """ Render plot of columns with variance below threshold

        :param df:     (DataFrame) valid pitch data
        :param cutoff: (Float) threshold value
    """
    # filter known irrelevant/unused features
    df = filter_features(df, ['ab_id', 'pitcher_id', 'type_confidence', 'sz_top', 'sz_bot', 'nasty', 'zone',
                              'type_confidence', 'inning', 'x', 'y'])
    mins = _identify_min_variance(df)

    fig = plt.figure(figsize=(12, 4))
    fig.suptitle(f'Variance for DataFrame Features     (Threshold Value: {cutoff} )', fontsize=15)
    ax = fig.add_subplot(xlabel='Feature', ylabel='Variance')
    ax.set_yscale('log')
    ax.axhline(cutoff, linestyle='--', color='red')
    ax.scatter([x[0] for x in mins], [x[1] for x in mins], s=100)
    ax.xaxis.labelpad = 10
    ax.tick_params(axis='x', labelrotation=90)
    plt.show()


# ************** MODEL COMPARISON/SELECTION ************** #
def _compare_default_models(df):
    """ Return accuracy & balanced_accuracy results for candidate OVR multi-classifiers

        :param df: (DataFrame) valid pitch data - training set

        :return:   (Dictionary) {name: accuracy score}
    """
    with warnings.catch_warnings():
        warnings.simplefilter('ignore')

        x, y = preprocess(df, ['ab_id', 'pitcher_id', 'type_confidence', 'sz_top', 'sz_bot', 'nasty', 'zone',
                               'type_confidence', 'inning', 'x', 'y', 'px', 'pz', 'x0', 'y0', 'z0',
                               'vx0', 'vz0', 'ax', 'ay', 'az', 'start_speed', 'end_speed', 'break_angle',
                               'break_length', 'break_y', 'spin_rate', 'spin_dir', 'pfx_z'])
        x_train, x_test, y_train, y_test = train_test_split(x, y, test_size=0.3, random_state=42, shuffle=False)

        pipeline = make_pipeline(RobustScaler(), RBFSampler(random_state=42)).fit(x_train, y_train)

        clfs = {
            'Multi-Layer Perceptron': MLPClassifier(random_state=42),
            'Logistic Regression': LogisticRegression(random_state=42, n_jobs=-1, multi_class='ovr'),
            'Stochastic Gradient Descent': SGDClassifier(random_state=42, n_jobs=-1, loss='modified_huber'),
            'Linear Support Vector': LinearSVC(random_state=42, multi_class='ovr', loss='squared_hinge'),
            'Random Forest': RandomForestClassifier(random_state=42, n_jobs=-1),
            'K-Neighbors': KNeighborsClassifier(n_jobs=-1, n_neighbors=7)}

        preds = {name: clf.fit(pipeline.transform(x_train), y_train).predict(pipeline.transform(x_test))
                 for name, clf in clfs.items()}

        return {
            name: {
                'acc': accuracy_score(y_test, y_preds),
                'bal': balanced_accuracy_score(y_test, y_preds)}
            for name, y_preds in preds.items()}


def plot_default_model_comparison(df):
    """ Render plot of model accuracy scores & names with default parameters

        :param df:   (DataFrame) pitch training data
    """
    results = _compare_default_models(df)

    fig, ax = plt.subplots(figsize=(10, 8))
    accuracy = [results[key]['acc'] for key in results]
    balacc = [results[key]['bal'] for key in results]
    ax.barh(range(len(results)), accuracy, align='edge', height=0.4, label='Accuracy', color='navy')
    ax.barh(range(len(results)), balacc, align='edge', height=-0.4, label='Balanced Accuracy', color='grey')
    ax.set(xlim=(min(min(accuracy), min(balacc)) * 0.6, 1), yticks=(range(len(results))),
           title='Multi-Classifier Accuracy and Balanced Accuracy with Default Models')
    ax.set_yticklabels(results, x=0.02, color='white', fontweight='bold', va='center', ha='left')
    ax.legend()

    plt.show()


def initialize_model(df, model='final'):
    """ Create & Save baseline Classifier model

        :param df:    (DataFrame) valid pitch data
        :param model: (String) selected classifier to initialize: ['sgd', 'svc', 'mlp', 'final']
    """
    if model not in ['sgd', 'svc', 'mlp', 'final']:
        raise AttributeError(f'initialize_model: {model} not found in model library. Must be in ["sgd", "svc", "mlp"]')
    else:
        if model == 'final':
            clf = MLPClassifier(random_state=42, solver='lbfgs', learning_rate='invscaling', activation='identity',
                                hidden_layer_sizes=50, alpha=1e-4, tol=1e-4, max_iter=15000, max_fun=15000)
        elif model == 'svc':
            clf = LinearSVC(random_state=42, multi_class='ovr', loss='squared_hinge', class_weight='balanced')
        elif model == 'mlp':
            clf = MLPClassifier(random_state=42)
        else:
            clf = SGDClassifier(random_state=42, n_jobs=-1, loss='log', class_weight='balanced')

        _x_train, _y_train = preprocess(df, STD_FEATURES_REMOVED)

        pipeline = make_pipeline(RobustScaler(), RBFSampler(random_state=42), clf).fit(_x_train, _y_train)

        save_object(pipeline, 'pitch_type_' + model)
        print(f'pitch_type_{model} initialized to default state')

        return pipeline


def compare_confusion_matrices(df):
    """ Render confusion matrices using Seaborn's heatmap()

        :param df:  (DataFrame) valid pitch data
    """
    features_removed = ['start_speed', 'end_speed', 'sz_bot', 'sz_top', 'x0', 'y0', 'z0', 'ax', 'ay', 'vx0', 'vy0',
                        'break_y', 'break_length', 'x', 'y', 'px', 'pz', 'pfx_x']
    _x, _y = preprocess(df, features_removed)

    _clfs = {'SGDClassifier': load_object('pitch_type_sgd'),
             'LinearSVC': load_object('pitch_type_svc'),
             'MLPClassifier': load_object('pitch_type_mlp')}

    _preds = {name: clf.predict(_x) for name, clf in _clfs.items()}

    _tick_text = ['Changeup', 'Curve', 'Cutter', '4-Seam', '2-Seam', 'Sinker', 'Slider']

    fig, axes = plt.subplots(1, len(_preds), figsize=(len(_preds) * 4.25, 5))
    fig.suptitle(f'Confusion Matrix Using Pitches from Mixed Pitchers    Test size: {len(_y)}',
                 fontsize=14, y=1.03)
    fig.subplots_adjust(wspace=1e-7)
    for i, preds in enumerate(_preds.items()):
        _ax = sns.heatmap(confusion_matrix(_y, preds[1]), annot=True, cmap=sns.hls_palette(4, 0.2, l=0.3),
                          center=round(np.mean(np.unique(_y, return_counts=True)[1][:-1]) / 8),
                          annot_kws={'fontsize': 8, 'rotation': 45}, cbar=False, linewidths=5, ax=axes[i])
        prec_scores = ['  {:4.2f}  '.format(score * 100) for score in precision_score(_y, preds[1], average=None)]
        _ax.set_xlabel(''.join(prec_scores) + '\nPrecision Scores')
        _ax.set_xticklabels(_tick_text, {'fontsize': 8})
        if i == 0:
            _ax.set_yticklabels(_tick_text, {'fontsize': 8, 'verticalalignment': 'center'})
            _ax.set_ylabel('Predicted Label')
        else:
            _ax.set_yticks([])
            _ax.set_yticklabels([])
        title = f'{preds[0]}      Acc: {accuracy_score(_y, preds[1]) * 100:3.2f}  '\
                f'BalAcc: {balanced_accuracy_score(_y, preds[1]) * 100:3.2f}'
        _ax.set_title(title, fontdict={'fontsize': 11})

    axes[0].get_shared_y_axes().join(axes[0], axes[1], axes[2])
    plt.tight_layout()
    plt.show()


def _get_roc_auc_scores(x, y, model):
    from sklearn.preprocessing import label_binarize
    from sklearn.metrics import roc_curve, auc

    clf = load_object('pitch_type_' + model)

    classes = np.unique(y)
    n_classes = len(classes)

    # Binarize the output
    y_test = label_binarize(y, classes=classes)
    y_score = clf.decision_function(x) if model == 'svc' else clf.predict_proba(x)

    # Compute ROC curve and ROC area for each class
    fpr = dict()
    tpr = dict()
    roc_auc = dict()

    for i in range(n_classes):
        fpr[i], tpr[i], _ = roc_curve(y_test[:, i], y_score[:, i])
        roc_auc[i] = auc(fpr[i], tpr[i])

    # First aggregate all false positive rates
    all_fpr = np.unique(np.concatenate([fpr[i] for i in range(n_classes)]))

    # Then interpolate all ROC curves at this points
    mean_tpr = np.zeros_like(all_fpr)
    for i in range(n_classes):
        mean_tpr += np.interp(all_fpr, fpr[i], tpr[i])

    # Finally average it and compute AUC
    mean_tpr /= n_classes

    fpr["macro"] = all_fpr
    tpr["macro"] = mean_tpr
    roc_auc["macro"] = auc(fpr["macro"], tpr["macro"])

    return classes, fpr, tpr, roc_auc


def compare_roc_auc(df):
    """ Render plot of ROC AUC estimations for all classifiers under consideration

        :param df: (DataFrame) valid pitch data
    """
    features_removed = ['start_speed', 'end_speed', 'sz_bot', 'sz_top', 'x0', 'y0', 'z0', 'ax', 'ay', 'vx0', 'vy0',
                        'break_y', 'break_length', 'x', 'y', 'px', 'pz', 'pfx_x']

    x_test, y_test = preprocess(df, features_removed)

    fig = plt.figure(figsize=(15, 5))
    fig.suptitle(f'Comparison of Receiver Operating Characteristic for Multi-Classification    '
                 f'Test size: {len(y_test)}',
                 fontsize=14, y=1.01)

    for index, model in enumerate(['sgd', 'svc', 'mlp']):
        classes, fpr, tpr, roc_auc = _get_roc_auc_scores(x_test, y_test, 'final')

        # Plot all ROC curves for each model
        titles = ('SGDClassifier', 'LinearSVC', 'MLPClassifier')
        ylabel = 'True Positive Rate'
        ax = fig.add_subplot(1, 3, index + 1, xlim=(0, 1), ylim=(0, 1.05), xlabel='False Positive Rate',
                             ylabel=ylabel if index == 0 else '', title=titles[index])
        for i, v in enumerate(classes):
            ax.plot(fpr[i], tpr[i], lw=2,
                    label=f'Area: {roc_auc[i]:0.2f} - {Pitch.PITCH_LABELS[Pitch.PITCH_KEYS_REVERSE[v]][:9].title()}')
        ax.plot(fpr["macro"], tpr["macro"], color='lightgrey', linestyle=':', linewidth=4,
                label=f'Area: {roc_auc["macro"]:0.2f} - Macro-Avg')
        ax.plot([0, 1], [0, 1], 'k--', lw=2)
        if index != 0:
            ax.set_yticks([])
        ax.legend(loc="lower right")

    plt.show()


# ********************* MODEL TUNING ********************* #
def _tune_clf(df, clf, mode, p_grid):
    """ Return best Classifier from cross-validated f1_macro results

        :param df:     (DataFrame) valid pitch data
        :param clf:    (Classifier) valid sklearn classifier model
        :param mode:   (String) tuning mode ['random', 'grid'] (Default 'random')
        :param p_grid: (Dictionary) tuning parameters to pass to appropriate tuning method

        :return:
    """
    if mode not in ['random', 'grid']:
        raise ValueError(f'_tune_classifier: {mode} mode not supported. Use "random" or "grid"')

    with warnings.catch_warnings():
        warnings.simplefilter("ignore")

        _x, _y = preprocess(df, STD_FEATURES_REMOVED)

        pipeline = make_pipeline(RobustScaler(), RBFSampler(random_state=42), clf)

        if mode == 'grid':
            search = GridSearchCV(pipeline, param_grid=p_grid, scoring='f1_macro', n_jobs=-1, refit=True)
        else:
            search = RandomizedSearchCV(pipeline, param_distributions=p_grid, scoring='f1_macro', n_jobs=-1,
                                        refit=True, return_train_score=True, random_state=42)

        search.fit(_x, _y)

        print(search.best_estimator_)

        return search


def _random_tune_mlp(df):
    """ Tune MLPClassifier using RandomizedSearchCV

        :param df: (DataFrame) valid pitch data
    """
    return _tune_clf(
        df,
        MLPClassifier(random_state=42),
        'random', {
            'mlpclassifier__activation': ['identity', 'logistic', 'tanh', 'relu'],
            'mlpclassifier__solver': ['adam', 'lbfgs', 'sgd'],
            'mlpclassifier__learning_rate': ['constant', 'invscaling', 'adaptive']})


def _grid_tune_mlp(df):
    """ Tune MLPClassifier using GridSearchCV

        :param df: (DataFrame) valid pitch data
    """
    return _tune_clf(
        df,
        MLPClassifier(random_state=42, solver='lbfgs', learning_rate='invscaling', activation='logistic'),
        'grid', {
            'mlpclassifier__beta_1': np.linspace(0.0001, 0.9999, 5),
            'mlpclassifier__beta_2': np.linspace(0.0001, 0.9999, 5),
            'mlpclassifier__alpha': np.linspace(0.0001, 0.9999, 5)})


def tune_classifier(df, mode='random'):
    """ Train/Tune Classifier & save results

        :param df:   (DataFrame) valid pitch data
        :param mode: (String) tuning mode ['random', 'grid'] (Default 'random')

        :return:     (DataFrame) result Dictionary as a DataFrame
    """
    from time import localtime

    if mode not in ['random', 'grid']:
        raise ValueError(f'tune_clf: {mode} mode not supported. Use "random" or "grid"')

    _clf = _random_tune_mlp(df) if (mode == 'random') else _grid_tune_mlp(df)

    df_result = pd.DataFrame(_clf.cv_results_)

    t = localtime()
    identifier = f'{t.tm_yday}{t.tm_hour}{t.tm_min}'

    df_result.to_csv(WORKING_DIR + f'results/mlp_{mode}_results_{identifier}.csv', index=False)

    return df_result


def plot_tuning_result(df, test1, test2=None, mode='grid'):
    """ Render results of CV model tuning

        :param df:    (DataFrame) tuning results
        :param test1: (String) column identifier for param under test
        :param test2: (string) column identifier for second param under test
        :param mode:  (String) CV tuning mode - GridSearchCV or RandomizedSearchCV
    """
    if test1 not in df.columns:
        raise ValueError(f'plot_tuning_result(): Invalid param - {test1} not found in DataFrame columns.')
    if test2 and test2 not in df.columns:
        raise ValueError(f'plot_tuning_result(): Invalid param - {test2} not found in DataFrame columns.')
    if mode not in ['grid', 'random']:
        raise ValueError(f'plot_tuning_result(): Invalid mode - {mode}. Select from "grid" or "random".')
    if mode == 'random' and 'split0_train_score' not in df.columns:
        raise AttributeError(f'plot_tuning_result(): Incompatible "random" mode DataFrame. No train data present.')

    df = df.sort_values(by=['mean_fit_time'])

    length = len(df) // 2 if len(df) % 2 == 0 else len(df) // 2 + 1

    fig, axes = plt.subplots(ncols=2, nrows=length, figsize=(10, len(df)), sharex='all')

    for i, ax in enumerate(axes):
        # first half of results
        left_col = df[i:i + 1]
        if mode == 'random':  # plot train scores
            ax[0].plot([left_col[f'split{i}_train_score'].values for i in range(5)])
        if test2:
            ax[0].set_title(left_col[test2].values[0], y=0.05)
        ax[0].plot([left_col[f'split{i}_test_score'].values for i in range(5)])  # plot test scores
        ax[0].axhline(left_col['mean_test_score'].values, ls='--')
        ax[0].legend(left_col[test1], loc='upper right')

        # second half of results
        right_col = df[i + (len(df) // 2):i + 1 + (len(df) // 2)]
        if mode == 'random':  # plot train scores
            ax[1].plot([right_col[f'split{i}_train_score'].values for i in range(5)])
        if test2:
            ax[1].set_title(right_col[test2].values[0], y=0.05)
        ax[1].plot([right_col[f'split{i}_test_score'].values for i in range(5)])  # plot test scores
        ax[1].axhline(right_col['mean_test_score'].values, ls='--')
        ax[1].legend(right_col[test1], loc='upper right')

    plt.show()


def final_model_verification(train, tune, test):
    """ 1. Join training & tuning data
        2. Create trained instance of final model using above
        3. Display classification report
        4. Display ROC AUC curves
    """
    from sklearn.metrics import classification_report

    # train final model on 3 seasons of data
    train_tune_data = pd.concat([train, tune])
    model = initialize_model(train_tune_data)

    x, y = preprocess(test, STD_FEATURES_REMOVED)

    # verify model on holdout data (test)
    y_preds = model.predict(x)
    print(classification_report(y, y_preds))


def plot_roc_auc(df):
    """ Render plot of ROC AUC estimations for final classifier

        :param df: (DataFrame) valid pitch data
    """
    x_test, y_test = preprocess(df, STD_FEATURES_REMOVED)

    classes, fpr, tpr, roc_auc = _get_roc_auc_scores(x_test, y_test, 'final')

    # Plot all ROC curves
    fig = plt.figure(figsize=(10, 6))
    fig.suptitle(f'Reciever Operating Characteristic for Multi-Classification using MLPClassifier', y=0.92)
    ax = fig.add_subplot(xlim=(0, 1), ylim=(0, 1.05), xlabel='False Positive Rate', ylabel='True Positive Rate')
    for i, v in enumerate(classes):
        ax.plot(fpr[i], tpr[i], lw=2, label=f'Area: {roc_auc[i]:0.2f} - '
                                            f'{Pitch.PITCH_LABELS[Pitch.PITCH_KEYS_REVERSE[v]][:9].title()}')
    ax.plot(fpr["macro"], tpr["macro"], label=f'Area: {roc_auc["macro"]:0.2f} - Macro-Avg',
            color='lightgrey', linestyle=':', linewidth=4)
    ax.plot([0, 1], [0, 1], 'k--', lw=2)
    ax.legend(loc="lower right")

    plt.show()


# ***************** HELPER FUNCTIONS ***************** #
def save_object(obj, name):
    file = open(f'./capstone-data/{name}.pkl', 'wb')
    dump(obj, file, protocol=4, fix_imports=False)
    file.close()


def load_object(name):
    file = open(f'./capstone-data/{name}.pkl', 'rb')
    obj = load(file, fix_imports=False, encoding='bytes')
    file.close()

    return obj
