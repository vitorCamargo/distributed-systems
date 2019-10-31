# Description: This is a Subscriber Code, they are the users who will receive the information
# passed by the collector, ie the tweets they are interested in.
# Author: Vitor Bueno de Camargo, Gabriel David Sacca
# Created at: October, 25th. 2019
# Updated at: October, 30th. 2019

import pika
import sys
import json
import datetime

connection = pika.BlockingConnection(pika.ConnectionParameters(host = 'localhost'))
channel = connection.channel()

channel.exchange_declare(exchange = 'topic_logs', exchange_type = 'topic')

result = channel.queue_declare('', exclusive = True)
queue_name = result.method.queue

binding_keys = sys.argv[1:]
if not binding_keys:
  sys.stderr.write('Usage: %s [binding_key]...\n' % sys.argv[0])
  sys.exit(1)

for binding_key in binding_keys:
  channel.queue_bind(exchange = 'topic_logs', queue = queue_name, routing_key = binding_key)

print(' [*] Waiting for logs. To exit press CTRL+C')

def callback(ch, method, properties, body):
  tweet = json.loads(body)
  print('#%s \n\t[TWEET]:%s \n\t[USER]: %s (@%r)  \n\t[DATE]: %s, %s - %s\n\n' % (method.routing_key, tweet['text'], tweet['user']['name'], tweet['user']['screen_name'], tweet['created_at'][8:10], tweet['created_at'][4:7], tweet['created_at'][11:19]))

channel.basic_consume(queue = queue_name, on_message_callback = callback, auto_ack = True)

channel.start_consuming()