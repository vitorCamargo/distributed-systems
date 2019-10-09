import Pyro4
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

@Pyro4.expose
class Notas:
    def cadastraNota(self, ra, cod_disciplina, ano, semestre, nota, faltas):
        lista = [(ra, cod_disciplina, ano, semestre, nota, faltas)]
        cursor.executemany("""
        INSERT INTO Matricula (RA, cod_disciplina, ano, semestre, nota, faltas)
        VALUES (?,?,?,?,?,?)
        """, lista)
        conn.commit()
        print("inserido com sucesso")

        return "inserido com sucesso"

    def atualizaNota(self, nota, ra, cod_disciplina, ano, semestre):
        lista = (nota, ra, cod_disciplina, ano, semestre)
        cursor.execute("""
        UPDATE Matricula
        SET nota = ?
        WHERE RA = ? and cod_disciplina = ? and ano = ? and semestre = ?
        """, lista)
        conn.commit()
        print("atualizado com sucesso")

        return "atualizado com sucesso"

    def removeNota(self, ra, cod_disciplina, ano, semestre):
        cursor.execute("""
        DELETE FROM Matricula
        WHERE RA = ? and cod_disciplina = ? and ano = ? and semestre = ?
        """, (ra, cod_disciplina, ano, semestre))
        conn.commit()
        print("removido com sucesso")

        return "removido com sucesso"

    def consultaNota(self, ra):
        lista = (ra,)

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


        return valor

    def consultaFalta(self, cod_disciplina, ano, semestre):
        lista = (cod_disciplina, ano, semestre)

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

        return valor

    def consultaAluno(self, cod_disciplina, ano, semestre):
        lista = (cod_disciplina, ano, semestre)

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

        return valor
