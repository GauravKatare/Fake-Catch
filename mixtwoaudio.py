from rev_ai import apiclient, JobStatus
from pydub import AudioSegment

def fun(file_path,sample):
    access_token = '02UMSGJJclAaCjsV2JhZn2cVfRiFTo4NBlQtKycPT6k5IxaGNumOSU7a33Bc-uaGXjnXKbo8y1IaVjMHOchz6ua68Lb7U'

    # Create client with your access token
    client = apiclient.RevAiAPIClient(access_token)

    file_job = client.submit_job_local_file(filename= file_path, metadata="This_is_some_job_metadata", callback_url="", skip_diarization=False)

    job_id = file_job.id

    while file_job.status == JobStatus.IN_PROGRESS :
        file_job=client.get_job_details(job_id)
    mylist = []
    file = client.get_transcript_json(job_id)

    for i in file['monologues']:
        for j in i['elements']:
            if j['type'] == "text" and j["value"] == sample:
                new_tuple = (j["ts"], j["end_ts"])
                mylist.append(new_tuple)
    return mylist

mylist=fun("/home/gaurav/Documents/raj.wav", "Enter the text to be replaced")
song=AudioSegment.from_mp3("/home/gaurav/Documents/raj.wav")
song1=AudioSegment.from_mp3("/home/gaurav/Downloads/downloads.wav")
print(mylist)
ten_seconds = (mylist[0][0]-1)*1000
first_2_seconds = song[:ten_seconds]

beginning = first_2_seconds + 6
end = song1 - 3
without_the_middle = beginning + end
without_the_middle.export("/home/gaurav/Downloads/combined.wav", format='wav')
