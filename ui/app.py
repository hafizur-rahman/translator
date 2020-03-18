#!/bin/python3
import json
import os

import requests
from flask import Flask, render_template, request

app = Flask(__name__)
VOCABULARY_SERVICE_URI = os.getenv('VOCABULARY_SERVICE_URI', '')


@app.route('/')
def index():
    return render_template('index.html',
                           title='Japanese Vocabulary Builder',
                           VOCABULARY_SERVICE_URI=VOCABULARY_SERVICE_URI)


@app.route('/api/parse-url', methods=['POST'])
def parseUrl():
    response = requests.post('/'.join([VOCABULARY_SERVICE_URI, 'parse-url']),
                         headers={'Content-type': 'application/json'},
                         data=json.dumps(request.json, ensure_ascii=False).encode("UTF-8"))

    return response.text


@app.route('/api/parse-text', methods=['POST'])
def parseSite():
    response = requests.post('/'.join([VOCABULARY_SERVICE_URI, 'word-list']),
                         headers={'Content-type': 'application/json'},
                         data=json.dumps(request.json, ensure_ascii=False).encode("UTF-8"))

    return response.text


if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5001, debug=True)
