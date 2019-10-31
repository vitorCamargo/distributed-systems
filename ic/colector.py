# Description: This is a Collector Code, It communicates directly with the API, passing 
# its information through hashtags and passing it to the classifier.
# Author: Vitor Bueno de Camargo, Gabriel David Sacca
# Created at: October, 25th. 2019
# Updated at: October, 30th. 2019

from tweepy import OAuthHandler, Stream, StreamListener
import pika
import json

consumer_key = '9mXYsgrPl2SEeCl98AduXasHL'
consumer_secret = 'Pt3z0JPcvlnu3JYOgqnZnAzKeLHkn6s2iqZGM17eFp90MVMvEt'
access_token = '977606359700172801-dVr10sHhhHjteV8KuAF94UknfSUwVOV'
access_token_secret = 'wjg0KAxNDt1eeiiEG1RDAPIMbU8tBrzhTYgMod7exJw4P'

class StdOutListener(StreamListener):
  def on_data(self, data):
    if(len(json.loads(data)['entities']['hashtags']) > 0):
      connection = pika.BlockingConnection(pika.ConnectionParameters(host = 'localhost'))
      channel = connection.channel()

      channel.queue_declare(queue = 'tweets', durable = True)

      channel.basic_publish(
        exchange = '',
        routing_key = 'tweets',
        body = data,
        properties = pika.BasicProperties(delivery_mode = 2)
      )

      print(' [x] New Tweet Sent')
      connection.close()
    return True

  def on_error(self, status):
    print(status)

if __name__ == '__main__':
  l = StdOutListener()
  auth = OAuthHandler(consumer_key, consumer_secret)
  auth.set_access_token(access_token, access_token_secret)

  stream = Stream(auth, l)
  stream.filter(track = ['kpop'])

