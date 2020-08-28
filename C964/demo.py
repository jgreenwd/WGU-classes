import argparse, sys
from utils import encode_p_throws, filter_features, load_object, STD_FEATURES_REMOVED
from Pitch import PITCH_KEYS_REVERSE
import pandas as pd
import numpy as np
import matplotlib.pyplot as plt

# ----- PARSE USER INPUT -----
parser = argparse.ArgumentParser(add_help=False)
parser.add_argument('-p', metavar='<filepath>', type=str, required=True, help='file path')
parser.add_argument('-n', metavar='<samplesize>', type=int, required=True, help='sample size')
args = parser.parse_args()


# ----- LOAD PITCH FILE -----
try:
    if args.n < 4 or args.n > 10:
        raise ValueError
    pitch_csv = pd.read_csv(args.p)
    pitch_df = pd.DataFrame(pitch_csv)
except FileNotFoundError:
    print(f'File not found: {args.p}')
    print('demo.py --p <filepath> --n <samplesize>')
    sys.exit(1)
except ValueError:
    print(f'Invalid -n {args.n}: Must be between 4 and 10.')
    print('demo.py --p <filepath> --n <samplesize>')
    sys.exit(1)

pitch_df = encode_p_throws(pitch_df)


# ----- MAKE & ADD PITCH PREDICTIONS TO DATAFRAME -----
X = filter_features(pitch_df, STD_FEATURES_REMOVED)

clf = load_object('pitch_type_final')
y_preds = clf.predict(X)
y_keys = [PITCH_KEYS_REVERSE[y_val] for y_val in y_preds]
pitch_df['pitch_type'] = y_keys
probs = clf.predict_proba(X)


# ----- PLOT RESULTING DATA -----
length = args.n // 2 if args.n % 2 == 0 else args.n // 2 + 1

fig, axis = plt.subplots(nrows=length, ncols=2, figsize=(6, length * 2))
for i, ax in enumerate(axis):
    ax[0].bar(np.arange(probs[i].size), probs[i].tolist())
    max_index_left = np.argmax(probs[i])
    ax[0].get_children()[max_index_left].set_color('r')
    ax[0].set_xticks(np.arange(probs[i].size))
    ax[0].set_xticklabels(['CH', 'CU', 'FC', 'FF', 'FT', 'SI', 'SL'])

    j = i + length
    if j < args.n:
        ax[1].bar(np.arange(probs[j].size), probs[j].tolist())
        max_index_right = np.argmax(probs[j])
        ax[1].get_children()[max_index_right].set_color('r')
        ax[1].set_xticks(np.arange(probs[j].size))
        ax[1].set_xticklabels(['CH', 'CU', 'FC', 'FF', 'FT', 'SI', 'SL'])

plt.show()
