# Distributed Systems
👨🏽‍💻👁 Assignment for 'Distributed Systems' subject about Implementation of Protocol buffer with sqlite

## More Information
This was a project developed to learn concepts about Protocols on Distributed Systems with Java Programming Language and python, however, the speaking language was Portuguese 🇧🇷.

### Data Foreigner Representation
For this project it was created a server and a client TCP, but te server was in python, and the client in Javascript with nodejs, bouth was comunication to Protocol buffer, but in this project we need to use gRPC protocol.

### Compile
    - pip install grpcio-tools
    - python -m grpc_tools.protoc -I../../protos --python_out=. --grpc_python_out=. ../../protos/route_guide.proto

### Execute
- To execute python
    - python3 server.py
- To execute Web
    - npm i
    - node client.js