from random import randint
import pandas as pd
import numpy as np
import nltk
from nltk.corpus import stopwords
from pymystem3 import Mystem
from string import punctuation
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.naive_bayes import ComplementNB
import joblib

nltk.download("stopwords")
mystem = Mystem()
russian_stopwords = stopwords.words("russian")

model = joblib.load('model.pkl')
vectorizer = joblib.load('vectorize.pkl')
model_ton = joblib.load('model_ton.pkl')
vectorizer_ton = joblib.load('vectorize_ton.pkl')


def classify(text: str):
    texts = [text]

    for i in range(0, len(texts)):
        texts[i] = ' '.join(texts[i].split('\n'))
        texts[i] = texts[i].strip().lower()
        for item in punctuation:
            texts[i] = ' '.join(texts[i].split(item))
        texts[i] = texts[i].split(' ')
        texts[i] = ' '.join(list(filter(None, texts[i])))

    batch = np.arange(0, len(texts), 5000, dtype=int)
    batch = np.concatenate((batch, np.array([len(texts)])))

    all_texts = []
    for i in range(0, batch.size - 1):
        cur = '|'.join(texts[batch[i]: batch[i + 1]])
        words = mystem.lemmatize(cur)

        words = ' '.join((' '.join(words)).strip().split('\n'))
        sents = words.split('|')
        for sen in sents:
            sen = list(filter(None, sen.split(' ')))
            all_texts.append(' '.join(sen))

    for i in range(0, len(all_texts)):
        all_texts[i] = all_texts[i].split(' ')
        all_texts[i] = ' '.join([token for token in all_texts[i] if token not in russian_stopwords])

    X = vectorizer.transform(all_texts)
    X_ton = vectorizer_ton.transform(all_texts)

    answer = model.predict(X)
    answer_ton = model_ton.predict(X_ton)

    return int(answer[0]), int(answer_ton[0])



def save_to_retrain(text:str, rating: int, right_class: int):
    # TODO Save to Hadoop/SQL
    with open("retrain.csv", "a") as fout:
        print(text, rating, right_class, sep=',', file=fout)