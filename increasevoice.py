from pydub import AudioSegment

def increasesound(speechpath,cloneaudio):
    sound=AudioSegment.from_wav(speechpath)
    sound1=AudioSegment.from_wav(cloneaudio)
    print(sound.max_dBFS)
    print(sound1.max_dBFS)
    print((sound.max_dBFS-sound1.max_dBFS))
    sound1=sound1+(sound.max_dBFS-sound1.max_dBFS)
    sound1.export("/home/gaurav/Desktop/combined_loud.wav", format='wav')
    print(sound1.max_dBFS)

