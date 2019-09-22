# Multicast Protocol
👨🏽‍💻👁 Assignment for 'Distributed Systems' subject about Implementation of Multicast Protocols

## More Information
This was a project developed to learn concepts about Protocols on Distributed Systems with Java Programming Language, however, the speaking language was Portuguese 🇧🇷.

### Compile And Execute
To compile we do this with support of netbeans plataform, just need to execute the project from the IDE.


### Multicast Chat

The library used (different from the usual) was:
```java
import java.net.MulticastSocket;
import java.util.HashMap;
```

The Multicast library was used to use diferents methods to send mensage in multicast format, and the HashMap library was used to create a mapped list for the users and their IPs.

### Criptography

As an addition of this project we created a kind of simple criptography key. The Method consist in get the byte of messagens before send them and change the byte string. When the message arrives, it is decrypted and back to what it was, thus becoming the original message. This ensures that the communication channel will be secure if no one has access to the code.


