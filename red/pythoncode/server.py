import socket
import sys
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

# # criando uma lista de dados
# listaCurso = [
#     (None, "Ciência da Computação"),
#     (None, "Técnico de Informática")
#     ]

# # inserindo dados na tabela
# cursor.executemany("""
# INSERT INTO Curso (codigo, nome)
# VALUES (?,?)
# """, listaCurso)

# listaDisciplina = [
#     ("GA", "Geometria Analítica", "Elaine", 1),
#     ("CN", "Cálculo Numérico", "Wellington", 2),
#     ("MB", "Matemática Básica", "Thais", 1),
#     ("QM", "Química", "João Pedro", 2)
#     ]

# cursor.executemany("""
# INSERT INTO Disciplina (codigo, nome, professor, cod_curso)
# VALUES (?,?,?,?)
# """, listaDisciplina)

# listaAluno = [
#     (1921959, "Vitor Bueno", 6, 0),
#     (1921860, "Gabriel Sacca", 6, 0),
#     (1567890, "Thais Zorawski", 4, 1)
#     ]

# cursor.executemany("""
# INSERT INTO Aluno (RA, nome, periodo, cod_curso)
# VALUES (?,?,?,?)
# """, listaAluno)

# cursor.execute("""
# SELECT * FROM Curso;
# """)

# for linha in cursor.fetchall():
#     print(linha)


# conn.close()
def cadastraNota(request):
    lista = [(int(request[2]), request[3], int(request[4]), int(request[5]), int(request[6]), int(request[7].split("\"")[0]))]

    cursor.executemany("""
    INSERT INTO Matricula (RA, cod_disciplina, ano, semestre, nota, faltas)
    VALUES (?,?,?,?,?,?)
    """, lista)

    cursor.execute("""
    SELECT * FROM Matricula;
    """)

    for linha in cursor.fetchall():
        print(linha)

def atualizaNota(request):
    print('Olá')

def removeNota(request):
    print('Olá')

def consultaNota(request):
    print('Olá')

def consultaFalta(request):
    print('Olá')

def consultaAluno(request):
    print('Olá')



server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
server_address = ('localhost', 7001)
print('starting up on {} port {}'.format(*server_address))
server_socket.bind(server_address)

server_socket.listen(1)

while True:
    # Wait for a connection
    print('waiting for a connection')
    connection, client_address = server_socket.accept()
    try:
        print('connection from', client_address)

        # Receive the data in small chunks and retransmit it
        while(True):
            data = connection.recv(100)
            message = matricula_pb2.Mess()
            message.mess = data;
            request = message.mess.split(" ");
            print(message.mess.split(" "));
            if(request[1] == "\"POST"):
                cadastraNota(request)
            elif(request[1] == "\"PUT"):
                consultaFalta(request)
            elif(request[1] == "\"REMOVE"):
                consultaAluno(request)
            elif (request[1] == "\"GETMYNOTA"):
                consultaNota(request)
            elif (request[1] == "\"GETNOTABYSEMESTRE"):
                atualizaNota(request)
            elif (request[1] == "\"GETALUNOSBYANO"):
                removeNota(request)

    finally:
        # Clean up the connection
        connection.close()


# server_socket.connect(("localhost", 7000))

# #msg = "bom dia servidor."
# person = addressbook_pb2.Person()
# person.id = 234
# person.name = "Rodrigo Campiolo"
# person.email = "rcampiolo@ibest.com.br"

# # marshalling
# msg = person.SerializeToString()
# size = len(msg)

# server_socket.send((str(size) + "\n").encode())
# server_socket.send(msg)

# server_socket.close()
