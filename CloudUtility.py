#!/usr/bin/env python
import datetime
import os
import wget
import firebase_admin
from firebase_admin import credentials
from firebase_admin import storage

proxy = 'http://edcguest:edcguest@172.31.100.14:3128'
os.environ['http_proxy'] = proxy
os.environ['HTTP_PROXY'] = proxy
os.environ['https_proxy'] = proxy
os.environ['HTTPS_PROXY'] = proxy

def GetFromCloud(filename):
    cred = credentials.Certificate('kuchhbhi-b275f-firebase-adminsdk-qkdpu-d89833904b.json')
    app = firebase_admin.initialize_app(cred, {
        'storageBucket': 'kuchhbhi-b275f.appspot.com',
    }, name='storage')

    bucket = storage.bucket(app=app)
    blob = bucket.blob(filename)

    file_url = blob.generate_signed_url(datetime.timedelta(seconds=300), method='GET')
    print(file_url)
    output_directory = ""
    wget.download(file_url, out=output_directory)

def SendToCloud(filename):
    cred = credentials.Certificate('kuchhbhi-b275f-firebase-adminsdk-qkdpu-d89833904b.json')
    firebase_admin.initialize_app(cred, {
        'storageBucket': 'kuchhbhi-b275f.appspot.com'
    })
    bucket = storage.bucket()
    imagePath = filename
    imageBlob = bucket.blob(filename)
    imageBlob.upload_from_filename(imagePath)