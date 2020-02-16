from pydub import AudioSegment

sound=AudioSegment.from_wav('/home/gaurav/Downloads/tions.wav')
sound1=sound[251000:269000]
sound1.export('/home/gaurav/Downloads/audio2.wav',format='wav')
