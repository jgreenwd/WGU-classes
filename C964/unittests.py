## -------------------------------------------------------- ##
## ----------------      UNIT TESTING      ---------------- ##
## -------------------------------------------------------- ##

import pandas as pd
import numpy as np
import utils
import Pitcher
import unittest

test_data = pd.read_csv('/content/drive/My Drive/Colab Notebooks/capstone-data/unittest_data.csv')
    
class TestPreprocessFunctions(unittest.TestCase):
    def test_filter_wild_pitches(self):
        lengths = (34521, 43197, 44652, 44798)
        
        for i in range(3,6):
            result = len(utils.filter_wild_pitches(test_data, i))
            self.assertEqual(result, lengths[i - 3])


    def test_get_batters_faced(self):
        pitchers = {657228:11, 591672:2,    656887:150,
                    458676:52, 501992:1227, 594311:85}
        
        for pitcher in pitchers.keys():
            result = utils._get_batters_faced(test_data, pitcher)
            self.assertEqual(result, pitchers[pitcher])

        
    def test_filter_batters_faced(self):
        batter_totals = [0, 50, 100, 1000, 1000000]
        
        for i in batter_totals:
            result = utils.filter_batters_faced(test_data, i)
            for pitcher in np.unique(result.pitcher_id):
                self.assertIs(utils._get_batters_faced(test_data, pitcher) >= i, True)


    def test_get_pitch_count(self):
        pitchers = {541650:91,   571035:459, 657681:374,
                    571710:4363, 573244:344, 573204:89}
                        
        for pitcher in pitchers.keys():
            result = utils._get_pitch_count(test_data, pitcher)
            self.assertEqual(result, pitchers[pitcher])


    def test_filter_pitch_count(self):
        pitch_counts = [10, 1000, 3000, 1000000]
        
        for i in pitch_counts:
            result = utils.filter_pitch_count(test_data, i)
            for pitcher in np.unique(result.pitcher_id):
                self.assertIs(utils._get_pitch_count(test_data, pitcher) >= i, True)
        
        
    def test_filter_pitches(self):
        pitches_to_remove = ['KN', 'IN', 'FO', 'EP', 'PO', 'SC', 'UN', 'FA', 'AB', 'FF', 'FT']
        result = utils.filter_pitches(test_data, pitches_to_remove)
        
        for pitch in pitches_to_remove:
            self.assertIs(pitch in result.pitch_type.values, False)


    def test_filter_features(self):
        features = ['ab_id', 'nasty', 'zone', 'pitcher_id', 'pitch_type']
        result = utils.filter_features(test_data, features)
        
        for feature in features:
            self.assertIs(feature in result.columns, False)
            

    def test_encode_p_throws(self):
        result = utils.encode_p_throws(test_data[:10]).p_throws
        for p in result: self.assertEqual(p, 0)
        result = utils.encode_p_throws(test_data[-10:]).p_throws
        for p in result: self.assertEqual(p, 1)


    def test_encode_pitch_type(self):
        result = utils.encode_pitch_type(test_data)
        self.assertEqual(result.pitch_type.dtype.type, np.float64)


class TestGMMFunctions(unittest.TestCase):
    def test_get_h_and_c_scores(self):
        scores = ((-4.500709709983194e-16, 1.0),
                  (0.3688315500742831, 0.7941811489239383),
                  (0.45743269563349215, 0.7464763540098532),
                  (0.8342610181899319, 0.8846129151741444),
                  (0.5669327077974722, 0.47231240985788103),
                  (0.5755287581768893, 0.4311275041462787),
                  (0.8577701400881321, 0.42573867686813505),
                  (0.9276492905941274, 0.463063468716429))
                 
        x, y = utils.preprocess(test_data)
        start = 0
        
        for index, end in enumerate(range(3000,27000,3000)):
            result = Pitcher._get_h_and_c_scores(x[start:end], y[start:end], index + 1)
            start = end
            self.assertEquals(scores[index], result)


    def test_estimate_n_clusters(self):
        homo_dict = { 2: 0.4470766933289132,  3: 0.47808042883291746, 
                      4: 0.5018182271669309,  5: 0.5442452822405283, 
                      6: 0.5211664518186989,  7: 0.7170894102690551, 
                      8: 0.722867528024443,   9: 0.7164576007108636, 
                     10: 0.7162035849643891, 11: 0.6662504414068767}

        comp_dict = { 2: 0.9650297257285013,  3: 0.7191888565136932, 
                      4: 0.6415045936683161,  5: 0.730688517115183, 
                      6: 0.6214551668634116,  7: 0.7106572225068184, 
                      8: 0.6756289502990558,  9: 0.6800344545167626, 
                     10: 0.6839596096681522, 11: 0.6552245464674255}

        results = [2,3,4,4,6,7,7,7,7]
        
        for i in range(3,12):
            test_dict1 = {k:v for k,v in homo_dict.items() if k < i}
            test_dict2 = {k:v for k,v in comp_dict.items() if k < i}

            result = Pitcher._estimate_n_clusters(test_dict1, test_dict2)
        
            self.assertEquals(result, results[i-3])
