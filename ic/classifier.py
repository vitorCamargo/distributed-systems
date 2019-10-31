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