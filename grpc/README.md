# Distributed Systems
👨🏽‍💻👁 Assignment for 'Distributed Systems' subject about Implementation of Protocol buffer with sqlite

## More Information
This was a project developed to learn concepts about Protocols on Distributed Systems with Java Programming Language and python, however, the speaking language was Portuguese 🇧🇷.

### Data Foreigner Representation
For this project it was created a server and a client TCP, but te server was in python, and the client in java, bouth was comunication to Protocol buffer.

### Compile
    - alias protoc=~/Downloads/protoaula/protoc/bin/protoc
        - --python_out=pythoncode/ matricula.proto
        - --java_out=javacode/ matricula.proto
    - https://repo1.maven.org/maven2/com/google/protobuf/protobuf-java/3.7.1/protobuf-java-3.7.1.jar
    - pip3 install python3-protobuf
    - pip3 install protobuf

### Execute
- To execute python
    - python3 server.py
- To execute Java
    - javac -classpath .:protobuf-java-3.7.1.jar *.java
    - java -cp .:protobuf-java-3.7.1.jar ClientTcpAddressBook