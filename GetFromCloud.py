#!/usr/bin/env python
import datetime
import sys
import requests


import os
import wget

proxy = 'http://edcguest:edcguest@172.31.100.14:3128'

os.environ['http_proxy'] = proxy
os.environ['HTTP_PROXY'] = proxy
os.environ['https_proxy'] = proxy
os.environ['HTTPS_PROXY'] = proxy

import firebase_admin
from firebase_admin import credentials
from firebase_admin import storage

cred = credentials.Certificate('kuchhbhi-b275f-firebase-adminsdk-qkdpu-d89833904b.json')
app = firebase_admin.initialize_app(cred, {
    'storageBucket': 'kuchhbhi-b275f.appspot.com',
}, name='storage')

bucket = storage.bucket(app=app)
blob = bucket.blob("gaurav.jpg")

file_url = blob.generate_signed_url(datetime.timedelta(seconds=300), method='GET')
print(file_url)
output_directory=""
filename = wget.download(file_url, out=output_directory)
