# Description: This is a Classifier Code, he is responsible for capturing information passed by the 
# collector and relaying it to subscribers who wish that subject.
# Author: Vitor Bueno de Camargo, Gabriel David Sacca
# Created at: October, 25th. 2019
# Updated at: October, 30th. 2019

import pika
import json

connection = pika.BlockingConnection(pika.ConnectionParameters(host = 'localhost'))
channel = connection.channel()

channel.queue_declare(queue = 'tweets', durable = True)
print(' [*] Waiting for tweets. To exit press CTRL+C')

def callback(ch, method, properties, body):
  keys = json.loads(body)['entities']['hashtags']

  for key in keys:
    connectionTopics = pika.BlockingConnection(pika.ConnectionParameters(host = 'localhost'))
    channelTopics = connection.channel()

    channelTopics.exchange_declare(exchange = 'topic_logs', exchange_type = 'topic')

    channel.basic_publish(exchange = 'topic_logs', routing_key = key['text'], body = body)
    print(' [x] Receving Tweet and Replying to Topic %r' % (key['text']))
    connectionTopics.close()

  ch.basic_ack(delivery_tag = method.delivery_tag)


channel.basic_qos(prefetch_count = 1)
channel.basic_consume(queue = 'tweets', on_message_callback = callback)

channel.start_consuming()