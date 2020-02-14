#!/usr/bin/env python
import sys
import requests


import os

proxy = 'http://edcguest:edcguest@172.31.100.14:3128'

os.environ['http_proxy'] = proxy
os.environ['HTTP_PROXY'] = proxy
os.environ['https_proxy'] = proxy
os.environ['HTTPS_PROXY'] = proxy

import firebase_admin
from firebase_admin import credentials
from firebase_admin import storage

image_url ="gaurav.jpg"

cred = credentials.Certificate('kuchhbhi-b275f-firebase-adminsdk-qkdpu-d89833904b.json')
firebase_admin.initialize_app(cred, {
    'storageBucket': 'kuchhbhi-b275f.appspot.com'
})


bucket = storage.bucket()

imageBlob = bucket.blob("/")
imagePath = "gaurav.jpg"
imageBlob = bucket.blob("gaurav.jpg")
imageBlob.upload_from_filename(imagePath)


# blob = bucket.blob('gaurav.jpg')
# outfile='C:\\Users\\blackturtle\\Envs\\tube\\hello.txt'
# with open(outfile, 'rb') as my_file:
#     blob.upload_from_file(my_file)

#
# bucket = storage.bucket()
#
# image_data = requests.get(image_url).content
# blob = bucket.blob('new_cool_image.jpg')
# blob.upload_from_string(
#         image_data,
#         content_type='image/jpg'
#     )
# print(blob.public_url)