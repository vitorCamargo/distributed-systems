var PROTO_PATH = __dirname + '/../matricula.proto';

var scanf = require('scanf');
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

function main() {
  while(true) {
    console.log("/> ");
    var buffer = scanf("%S").toString();

    if(buffer == "EXIT") break;
    if(buffer == "HELP") {
      console.log("POST [RA] [cod_disciplina] [ano] [semestre] [nota] [falta]");
      console.log("PUT [nota] [RA] [cod_disciplina] [ano] [semestre]");
      console.log("REMOVE [RA] [cod_disciplina] [ano] [semestre]");
      console.log("GETMYNOTA [RA]");
      console.log("GETNOTABYSEMESTRE [cod_disciplina] [ano] [semestre]");
      console.log("GETALUNOSBYANO [cod_disciplina] [ano] [semestre]");
    } else {
      if(buffer.startsWith("POST ")) {
        var request = buffer.split(" ");
        if(request.length == 7) {
          console.log(client)
          client.cadastraNota({ RA: parseInt(request[1]), cod_disciplina: request[2], ano: parseInt(request[3]), semestre: parseInt(request[4]), nota: parseFloat(request[5]), faltas: parseInt(request[6]) }, function(err, response) {
            console.log(response);
          });
        } else {
          console.log("deu errado aqui");
        }
      }
      if(buffer.startsWith("PUT ")) {
        var request = buffer.split(" ");
        if(request.length == 6) {
          client.atualizaNota({ RA: parseInt(request[2]), cod_disciplina: request[3], ano: parseInt(request[4]), semestre: parseInt(request[5]), nota: parseFloat(request[2]) }, function(err, response) {
            console.log(response);
          });
        } else {
          console.log("deu errado aqui");
        }
      }
      if(buffer.startsWith("REMOVE ")) {
        var request = buffer.split(" ");
        if(request.length == 5) {
          client.removeNota({ RA: parseInt(request[1]), cod_disciplina: request[2], ano: parseInt(request[3]), semestre: parseInt(request[4]) }, function(err, response) {
            console.log(response);
          });
        } else {
          console.log("deu errado aqui");
        }
      }
      if(buffer.startsWith("GETMYNOTA ")) {
        var request = buffer.split(" ");
        if(request.length == 1) {
          client.consultaNota({ RA: parseInt(request[1]) }, function(err, response) {
            console.log(response);
          });
        } else {
          console.log("deu errado aqui");
        }
      }
      if(buffer.startsWith("GETNOTABYSEMESTRE ")) {
        var request = buffer.split(" ");
        if(request.length == 3) {
          client.consultaFalta({ cod_disciplina: request[1], ano: parseInt(request[2]), semestre: parseInt(request[3]) }, function(err, response) {
            console.log(response);
          });
        } else {
          console.log("deu errado aqui");
        }
      }
      if(buffer.startsWith("GETALUNOSBYANO ")) {
        var request = buffer.split(" ");
        if(request.length == 3) {
          client.consultaAluno({ cod_disciplina: request[1], ano: parseInt(request[2]), semestre: parseInt(request[3]) }, function(err, response) {
            console.log(response);
          });
        } else {
          console.log("deu errado aqui");
        }
      }
    }
  }
}

main();