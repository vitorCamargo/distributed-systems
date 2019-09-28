create database if not exists Notas;
use Notas;


create table if not exists Curso (
    codigo int primary key auto_increment,
    nome varchar(100)
);

create table if not exists Aluno (
    RA int primary key,
    nome varchar(100),
    periodo int,
    cod_curso int,
    foreign key(cod_curso) references Curso(codigo)
);

create table if not exists Disciplina (
    codigo varchar(100) primary key,
    nome varchar(100),
    professor varchar(100),
    cod_curso int,
    foreign key(cod_curso) references Curso(codigo)
);

create table if not exists Matricula (
    RA int,
    cod_disciplina varchar(100),
    ano int,
    semestre int,
    nota float,
    faltas int,
    foreign key(RA) references Aluno(RA),
    foreign key(cod_disciplina) references Disciplina(codigo),
    primary key(RA, cod_disciplina, ano, semestre)

);