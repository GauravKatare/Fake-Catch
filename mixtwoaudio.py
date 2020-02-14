from rev_ai import apiclient, JobStatus
from pydub import AudioSegment

def mergetwoaudio(mylist,end):

    sound1 = AudioSegment.from_wav("/home/kunal/Desktop/audio.wav")
    sound2 = AudioSegment.from_wav("/home/kunal/Desktop/downloads.wav")

    fro = 0.0
    to = 0.0
    mixed_sound = sound1[:0]
    for ts in mylist:
        to = ts[0] * 1000
        mixed_sound = mixed_sound + sound1[fro:to] + sound2
        fro = ts[1] * 1000

    end = end * 1000

    mixed_sound = mixed_sound + sound1[fro:end]
    mixed_sound.export("/home/kunal/Desktop/combined.wav", format='wav')



def changeAudio(file_path,sample):
    access_token = '02UMSGJJclAaCjsV2JhZn2cVfRiFTo4NBlQtKycPT6k5IxaGNumOSU7a33Bc-uaGXjnXKbo8y1IaVjMHOchz6ua68Lb7U'

    # Create client with your access token
    client = apiclient.RevAiAPIClient(access_token)

    file_job = client.submit_job_local_file(filename= file_path, metadata="This_is_some_job_metadata", callback_url="", skip_diarization=False)

    job_id = file_job.id

    while file_job.status == JobStatus.IN_PROGRESS :
        file_job=client.get_job_details(job_id)
    mylist = []
    file = client.get_transcript_json(job_id)

    end_time = 0.0

    for i in file['monologues']:
        for j in i['elements']:
            if j['type'] == "text":
                if j["value"] == sample:
                    new_tuple = (j["ts"], j["end_ts"])
                    mylist.append(new_tuple)
                end_time = j["end_ts"]

    print(end_time)
    mergetwoaudio(mylist,end_time)

changeAudio("/home/kunal/Desktop/audio.wav", "institution")



