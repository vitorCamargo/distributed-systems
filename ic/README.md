# Distributed Systems
👨🏽‍💻👁 Assignment for 'Distributed Systems' subject about Implementation of a indirect data communication, the RabbitMQ tool was used for this purpose, which provides the creation of a FIFO that will help to create this type of communication.

## More Information
This was a project developed to learn concepts about Protocols on Distributed Systems with Python Programming Language, however, the speaking language was Portuguese 🇧🇷.

### IC
For this project 3 distinct files were created. The first one that gives the tweeter API the gateway is the collector file, which in turn filters the tweet information of a subject and sends it to the classifier that finally creates rows of distinct topics and sends them to subscribers.


The library used (different from the usual) was:
```python
import pika
import json
from tweepy import OAuthHandler, Stream, StreamListener
```

The pika import is used to all tools of RabbitMQ, the json is to facilitate the handling of all results provenient of pika and the tweet is the API to get all the tweets.