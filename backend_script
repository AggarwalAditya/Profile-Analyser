from flask import Flask, redirect, url_for, request
import pickle
from nltk.classify import ClassifierI
from nltk.tokenize import word_tokenize
import nltk
from nltk.corpus import movie_reviews
import random
from bs4 import BeautifulSoup
import requests
import re




app = Flask(__name__)

@app.route('/')
def hello_world():
    return 'Hello'


class VoteClassifier(ClassifierI):
    def __init__(self, *classifiers):
        self._classifiers = classifiers

    def classify(self, features):
        votes = []
        for c in self._classifiers:
            v = c.classify(features)
            votes.append(v)
            return max(set(votes), key=votes.count)

    def confidence(self, features):
        votes = []
        for c in self._classifiers:
            v = c.classify(features)
            votes.append(v)

        choice_votes = votes.count(max(set(votes), key=votes.count))
        conf = choice_votes / len(votes)
        return conf


documents_f = open("documents.pickle", "rb")
documents = pickle.load(documents_f)
documents_f.close()




word_features5k_f = open("word_features5k.pickle", "rb")
word_features = pickle.load(word_features5k_f)
word_features5k_f.close()


def find_features(document):
    words = word_tokenize(document)
    features = {}
    for w in word_features:
        features[w] = (w in words)

    return features



# featuresets_f = open("featuresets.pickle", "rb")
# featuresets = pickle.load(featuresets_f)
# featuresets_f.close()
#
# random.shuffle(featuresets)
# print(len(featuresets))
#
# testing_set = featuresets[10000:]
# training_set = featuresets[:10000]



open_file = open("originalnaivebayes5k.pickle", "rb")
classifier = pickle.load(open_file)
open_file.close()


open_file = open("MNB_classifier5k.pickle", "rb")
MNB_classifier = pickle.load(open_file)
open_file.close()



open_file = open("BernoulliNB_classifier5k.pickle", "rb")
BernoulliNB_classifier = pickle.load(open_file)
open_file.close()


open_file = open("LogisticRegression_classifier5k.pickle", "rb")
LogisticRegression_classifier = pickle.load(open_file)
open_file.close()


open_file = open("LinearSVC_classifier5k.pickle", "rb")
LinearSVC_classifier = pickle.load(open_file)
open_file.close()


open_file = open("SGDC_classifier5k.pickle", "rb")
SGDC_classifier = pickle.load(open_file)
open_file.close()




voted_classifier = VoteClassifier(
                                  classifier,
                                  LinearSVC_classifier,
                                  MNB_classifier,
                                  BernoulliNB_classifier,
                                  LogisticRegression_classifier)


def sentiment(text):
    feats = find_features(text)
    return voted_classifier.classify(feats),voted_classifier.confidence(feats)


@app.route('/senti',methods = ['POST', 'GET'])
def senti():
     if request.method == 'POST':
       print("reached server")
       data =request.data
       print(data)


       pos=0
       neg=0

       y = data.strip().split("$")
       for i in range(0,len(y)):

           if "null" not in y[i]:
               print(y[i])
               var=sentiment(str(y[i]))
               print(var[0])
               if(var[0] == 'pos'):
                   pos=pos+1
               else:
                   neg=neg+1

       #var=sentiment("terrible")

       return (str(pos)+" "+str(neg))

     else:
        pos = 0
        neg = 0

        data=["positive","happy","lovely","terrible"]
        for i in range(0, len(data)):
             if (data[i] != "null"):
                 var = sentiment(str(data[i]))
                 if (var[0] == 'pos'):
                     pos = pos + 1
                 else:
                     neg = neg + 1

         # var=sentiment("terrible")

        return ("positive= "+str(pos) + "   negative=" + str(neg))

if __name__ == '__main__':
   app.run(host='0.0.0.0')




