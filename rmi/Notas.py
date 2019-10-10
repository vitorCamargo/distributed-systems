# Description: This is the Python Class to RMI Project, it's the functions used in client.
# Author: Gabriel David Sacca, Vitor Bueno de Camargo
# Created at: October, 09th. 2019
# Updated at: October, 09th. 2019

import Pyro4
import sqlite3

@Pyro4.expose
class Notas:
    def cadastraNota(self, ra, cod_disciplina, ano, semestre, nota, faltas):
        conn = sqlite3.connect('Notas.db')
        cursor = conn.cursor()

        lista = [(int(ra), cod_disciplina, int(ano), int(semestre), float(nota), int(faltas))]
        cursor.executemany("""
        INSERT INTO Matricula (RA, cod_disciplina, ano, semestre, nota, faltas)
        VALUES (?,?,?,?,?,?)
        """, lista)
        conn.commit()
        print("inserido com sucesso")

        return "inserido com sucesso"

    def atualizaNota(self, nota, ra, cod_disciplina, ano, semestre):
        conn = sqlite3.connect('Notas.db')
        cursor = conn.cursor()

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
        conn = sqlite3.connect('Notas.db')
        cursor = conn.cursor()

        cursor.execute("""
        DELETE FROM Matricula
        WHERE RA = ? and cod_disciplina = ? and ano = ? and semestre = ?
        """, (ra, cod_disciplina, ano, semestre))
        conn.commit()
        print("removido com sucesso")

        return "removido com sucesso"

    def consultaNota(self, ra):
        conn = sqlite3.connect('Notas.db')
        cursor = conn.cursor()

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
        conn = sqlite3.connect('Notas.db')
        cursor = conn.cursor()

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
        conn = sqlite3.connect('Notas.db')
        cursor = conn.cursor()

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
