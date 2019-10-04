# Description: This is the Python Server to Data Foreigner Representation. It's the control of the SQLite3 Database.
# Author: Gabriel David Sacca, Vitor Bueno de Camargo
# Created at: September, 25th. 2019
# Updated at: September, 30th. 2019

from concurrent import futures
import grpc
import sys
import matricula_pb2_grpc
import matricula_pb2
import sqlite3


conn = sqlite3.connect('Notas.db')
cursor = conn.cursor()

# lendo os dados
cursor.execute("""
create table if not exists Curso (
    codigo INTEGER PRIMARY KEY AUTOINCREMENT,
    nome varchar(100)
);
""")

cursor.execute("""
create table if not exists Aluno (
    RA INTEGER PRIMARY KEY,
    nome varchar(100),
    periodo INTEGER,
    cod_curso INTEGER,
    foreign key(cod_curso) references Curso(codigo)
);
""")

cursor.execute("""
create table if not exists Disciplina (
    codigo varchar(100) PRIMARY KEY,
    nome varchar(100),
    professor varchar(100),
    cod_curso INTEGER,
    foreign key(cod_curso) references Curso(codigo)
);
""")

cursor.execute("""
create table if not exists Matricula (
    RA INTEGER,
    cod_disciplina varchar(100),
    ano INTEGER,
    semestre INTEGER,
    nota float,
    faltas INTEGER,
    foreign key(RA) references Aluno(RA),
    foreign key(cod_disciplina) references Disciplina(codigo),
    PRIMARY KEY(RA, cod_disciplina, ano, semestre)

);
""")

class matriculaServicer(matricula_pb2_grpc.MatriculaServiceServicer):
    def cadastraNota(self, request, context):
        # lista = [(int(request[2]), request[3], int(request[4]), int(request[5]), int(request[6]), int(request[7].split("\"")[0]))]
        lista = [(request.RA, request.cod_disciplina, request.ano, request.semestre, request.nota, request.faltas)]
        cursor.executemany("""
        INSERT INTO Matricula (RA, cod_disciplina, ano, semestre, nota, faltas)
        VALUES (?,?,?,?,?,?)
        """, lista)
        conn.commit()
        print("inserido com sucesso")
        return matricula_pb2.Mess(mess = 'inserido com sucesso')

    def atualizaNota(self, request, context):
        lista = (request.Nota, request.RA, request.cod_disciplina, request.ano, request.semestre)
        cursor.execute("""
        UPDATE Matricula
        SET nota = ?
        WHERE RA = ? and cod_disciplina = ? and ano = ? and semestre = ?
        """, lista)
        conn.commit()

        return matricula_pb2.Mess(mess = 'inserido com sucesso')

    def removeNota(self, request, context):
        cursor.execute("""
        DELETE FROM Matricula
        WHERE RA = ? and cod_disciplina = ? and ano = ? and semestre = ?
        """, (request.RA, request.cod_disciplina, request.ano, request.semestre))

        return matricula_pb2.Mess(mess = 'inserido com sucesso')

    def consultaNota(self, request, context):
        lista = (request.RA,)

        cursor.execute("""
        SELECT m.cod_disciplina, d.nome, m.nota, m.faltas FROM Disciplina d, Matricula m 
        WHERE m.RA = ? and m.cod_disciplina = d.codigo
        """, lista)

        records = cursor.fetchall()
        
        valor = ''
        for row in records:
            for value in row:
                valor = valor + str(value) + ', '
            valor = valor + '\n'


        return matricula_pb2.Mess(mess = valor)

    def consultaFalta(self, request, context):
        lista = (request.cod_disciplina, request.ano, request.semestre)

        cursor.execute("""
        SELECT m.RA, a.nome, m.nota, m.faltas FROM Aluno a, Matricula m 
        WHERE m.cod_disciplina = ? and m.ano = ? and m.semestre = ? and a.RA = m.RA
        """, lista)

        records = cursor.fetchall()
        
        valor = ''
        for row in records:
            for value in row:
                valor = valor + str(value) + ', '
            valor = valor + '\n'

        return matricula_pb2.Mess(mess = valor)

    def consultaAluno(self, request, context):
        lista = (request.cod_disciplina, request.ano, request.semestre)

        cursor.execute("""
        SELECT m.RA, a.nome, a.periodo FROM Aluno a, Matricula m 
        WHERE m.cod_disciplina = ? and m.ano = ? and m.semestre = ? and a.RA = m.RA
        """, lista)

        records = cursor.fetchall()
        
        valor = ''
        for row in records:
            for value in row:
                valor = valor + str(value) + ', '
            valor = valor + '\n'

        return matricula_pb2.Mess(mess = valor)

def serve():
    server = grpc.server(futures.ThreadPoolExecutor(max_workers=10))
    matricula_pb2_grpc.add_MatriculaServiceServicer_to_server(
        matriculaServicer(), server)
    server.add_insecure_port('127.0.0.1:50051')
    server.start()
    server.wait_for_termination()

if __name__ == '__main__':
    serve()
