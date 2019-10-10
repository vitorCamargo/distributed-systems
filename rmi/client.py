# Description: This is the Python Client to RMI Project.
# Author: Gabriel David Sacca, Vitor Bueno de Camargo
# Created at: October, 09th. 2019
# Updated at: October, 09th. 2019

import Pyro4

def main():
    Pyro4.locateNS("127.0.0.1", 9090)
    notas = Pyro4.Proxy("PYRONAME:NotasService")
    

    while(True):
        # print("/> ");
        Buffer = input('/> ');
        
        if(Buffer == "EXIT"):
            break
        elif(Buffer == "HELP"):
            print("POST [RA] [cod_disciplina] [ano] [semestre] [nota] [falta]");
            print("PUT [nota] [RA] [cod_disciplina] [ano] [semestre]");
            print("REMOVE [RA] [cod_disciplina] [ano] [semestre]");
            print("GETMYNOTA [RA]");
            print("GETNOTABYSEMESTRE [cod_disciplina] [ano] [semestre]");
            print("GETALUNOSBYANO [cod_disciplina] [ano] [semestre]");
        else:
            if(Buffer.startswith("POST ")):
                request = Buffer.split(" ");
                if(len(request) == 7):
                    print(notas.cadastraNota(int(request[1]), request[2], int(request[3]), int(request[4]), float(request[5]), int(request[6])))
                else:
                    print("deu errado aqui")

            if(Buffer.startswith("PUT ")):
                request = Buffer.split(" ")
                if(len(request) == 6):
                    print(notas.atualizaNota(float(request[1]), int(request[2]), request[3], int(request[4]), int(request[5])))
                else:
                    print("deu errado aqui")

            if(Buffer.startswith("REMOVE ")):
                request = Buffer.split(" ")
                if(len(request) == 5):
                    print(notas.removeNota(int(request[1]), request[2], int(request[3]), int(request[4])))
                else:
                    print("deu errado aqui")

            if(Buffer.startswith("GETMYNOTA ")):
                request = Buffer.split(" ")
                if(len(request) == 2):
                     print(notas.consultaNota(int(request[1])))
                else:
                    print("deu errado aqui")

            if(Buffer.startswith("GETNOTABYSEMESTRE ")):
                request = Buffer.split(" ")
                if(len(request) == 4):
                    print(notas.consultaFalta(request[1], int(request[2]), int(request[3])))
                else:
                    print("deu errado aqui")

            if(Buffer.startswith("GETALUNOSBYANO ")):
                request = Buffer.split(" ");
                if(len(request) == 4):
                    print(notas.consultaAluno(request[1], int(request[2]), int(request[3])))
                else:
                    print("deu errado aqui")

if __name__ == "__main__":
    main()