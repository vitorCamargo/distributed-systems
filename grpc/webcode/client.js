var PROTO_PATH = __dirname + '/../matricula.proto';

var async = require('async');
var fs = require('fs');
var parseArgs = require('minimist');
var path = require('path');
var _ = require('lodash');
var grpc = require('grpc');
var protoLoader = require('@grpc/proto-loader');
var packageDefinition = protoLoader.loadSync(
    PROTO_PATH,
    {keepCase: true,
     longs: String,
     enums: String,
     defaults: true,
     oneofs: true
    });
var matricula = grpc.loadPackageDefinition(packageDefinition).matricula;
var client = new matricula.MatriculaService('localhost:50051', grpc.credentials.createInsecure());
console.log('foooooo', client);

function main() {
  client.cadastraNota({
    RA: 1921959,
    cod_disciplina: 'MB',
    ano: 2019,
    semestre: 3,
    nota: 10,
    faltas: 0
  }, function(err, response) {
    console.log('Greeting:', response);
  });
  // var client = matricula_proto.ServiceClient('localhost:50051', grpc.credentials.createInsecure(), {});
  // var user;

  // if (process.argv.length >= 3) {
  //   user = process.argv[2];
  // } else {
  //   user = 'world';
  // }
  // client.sayHello({ name: user }, function(err, response) {
  //   console.log('Greeting:', response.message);
  // });
}

main();