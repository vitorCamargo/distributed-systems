# -*- coding: utf-8 -*-
# Generated by the protocol buffer compiler.  DO NOT EDIT!
# source: matricula.proto

import sys
_b=sys.version_info[0]<3 and (lambda x:x) or (lambda x:x.encode('latin1'))
from google.protobuf import descriptor as _descriptor
from google.protobuf import message as _message
from google.protobuf import reflection as _reflection
from google.protobuf import symbol_database as _symbol_database
# @@protoc_insertion_point(imports)

_sym_db = _symbol_database.Default()




DESCRIPTOR = _descriptor.FileDescriptor(
  name='matricula.proto',
  package='matricula',
  syntax='proto3',
  serialized_options=_b('\n\032io.grpc.examples.matriculaB\025MatriculaServiceProtoP\001\242\002\003RTG'),
  serialized_pb=_b('\n\x0fmatricula.proto\x12\tmatricula\"\x14\n\x04Mess\x12\x0c\n\x04mess\x18\x01 \x01(\t\"\x17\n\x07MessInt\x12\x0c\n\x04mess\x18\x01 \x01(\x05\"%\n\x05\x43urso\x12\x0e\n\x06\x63odigo\x18\x01 \x01(\x05\x12\x0c\n\x04nome\x18\x02 \x01(\t\"E\n\x05\x41luno\x12\n\n\x02RA\x18\x01 \x01(\x05\x12\x0c\n\x04nome\x18\x02 \x01(\t\x12\x0f\n\x07periodo\x18\x03 \x01(\x05\x12\x11\n\tcod_curso\x18\x04 \x01(\x05\"P\n\nDisciplina\x12\x0e\n\x06\x63odigo\x18\x01 \x01(\t\x12\x0c\n\x04nome\x18\x02 \x01(\t\x12\x11\n\tprofessor\x18\x03 \x01(\t\x12\x11\n\tcod_curso\x18\x04 \x01(\x05\"l\n\tMatricula\x12\n\n\x02RA\x18\x01 \x01(\x05\x12\x16\n\x0e\x63od_disciplina\x18\x02 \x01(\t\x12\x0b\n\x03\x61no\x18\x03 \x01(\x05\x12\x10\n\x08semestre\x18\x04 \x01(\x05\x12\x0c\n\x04nota\x18\x05 \x01(\x02\x12\x0e\n\x06\x66\x61ltas\x18\x06 \x01(\x05\x32\xe8\x02\n\x10MatriculaService\x12\x37\n\x0c\x63\x61\x64\x61straNota\x12\x14.matricula.Matricula\x1a\x0f.matricula.Mess\"\x00\x12\x37\n\x0c\x61tualizaNota\x12\x14.matricula.Matricula\x1a\x0f.matricula.Mess\"\x00\x12\x35\n\nremoveNota\x12\x14.matricula.Matricula\x1a\x0f.matricula.Mess\"\x00\x12\x37\n\x0c\x63onsultaNota\x12\x14.matricula.Matricula\x1a\x0f.matricula.Mess\"\x00\x12\x38\n\rconsultaFalta\x12\x14.matricula.Matricula\x1a\x0f.matricula.Mess\"\x00\x12\x38\n\rconsultaAluno\x12\x14.matricula.Matricula\x1a\x0f.matricula.Mess\"\x00\x42;\n\x1aio.grpc.examples.matriculaB\x15MatriculaServiceProtoP\x01\xa2\x02\x03RTGb\x06proto3')
)




_MESS = _descriptor.Descriptor(
  name='Mess',
  full_name='matricula.Mess',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  fields=[
    _descriptor.FieldDescriptor(
      name='mess', full_name='matricula.Mess.mess', index=0,
      number=1, type=9, cpp_type=9, label=1,
      has_default_value=False, default_value=_b("").decode('utf-8'),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR),
  ],
  extensions=[
  ],
  nested_types=[],
  enum_types=[
  ],
  serialized_options=None,
  is_extendable=False,
  syntax='proto3',
  extension_ranges=[],
  oneofs=[
  ],
  serialized_start=30,
  serialized_end=50,
)


_MESSINT = _descriptor.Descriptor(
  name='MessInt',
  full_name='matricula.MessInt',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  fields=[
    _descriptor.FieldDescriptor(
      name='mess', full_name='matricula.MessInt.mess', index=0,
      number=1, type=5, cpp_type=1, label=1,
      has_default_value=False, default_value=0,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR),
  ],
  extensions=[
  ],
  nested_types=[],
  enum_types=[
  ],
  serialized_options=None,
  is_extendable=False,
  syntax='proto3',
  extension_ranges=[],
  oneofs=[
  ],
  serialized_start=52,
  serialized_end=75,
)


_CURSO = _descriptor.Descriptor(
  name='Curso',
  full_name='matricula.Curso',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  fields=[
    _descriptor.FieldDescriptor(
      name='codigo', full_name='matricula.Curso.codigo', index=0,
      number=1, type=5, cpp_type=1, label=1,
      has_default_value=False, default_value=0,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR),
    _descriptor.FieldDescriptor(
      name='nome', full_name='matricula.Curso.nome', index=1,
      number=2, type=9, cpp_type=9, label=1,
      has_default_value=False, default_value=_b("").decode('utf-8'),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR),
  ],
  extensions=[
  ],
  nested_types=[],
  enum_types=[
  ],
  serialized_options=None,
  is_extendable=False,
  syntax='proto3',
  extension_ranges=[],
  oneofs=[
  ],
  serialized_start=77,
  serialized_end=114,
)


_ALUNO = _descriptor.Descriptor(
  name='Aluno',
  full_name='matricula.Aluno',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  fields=[
    _descriptor.FieldDescriptor(
      name='RA', full_name='matricula.Aluno.RA', index=0,
      number=1, type=5, cpp_type=1, label=1,
      has_default_value=False, default_value=0,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR),
    _descriptor.FieldDescriptor(
      name='nome', full_name='matricula.Aluno.nome', index=1,
      number=2, type=9, cpp_type=9, label=1,
      has_default_value=False, default_value=_b("").decode('utf-8'),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR),
    _descriptor.FieldDescriptor(
      name='periodo', full_name='matricula.Aluno.periodo', index=2,
      number=3, type=5, cpp_type=1, label=1,
      has_default_value=False, default_value=0,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR),
    _descriptor.FieldDescriptor(
      name='cod_curso', full_name='matricula.Aluno.cod_curso', index=3,
      number=4, type=5, cpp_type=1, label=1,
      has_default_value=False, default_value=0,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR),
  ],
  extensions=[
  ],
  nested_types=[],
  enum_types=[
  ],
  serialized_options=None,
  is_extendable=False,
  syntax='proto3',
  extension_ranges=[],
  oneofs=[
  ],
  serialized_start=116,
  serialized_end=185,
)


_DISCIPLINA = _descriptor.Descriptor(
  name='Disciplina',
  full_name='matricula.Disciplina',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  fields=[
    _descriptor.FieldDescriptor(
      name='codigo', full_name='matricula.Disciplina.codigo', index=0,
      number=1, type=9, cpp_type=9, label=1,
      has_default_value=False, default_value=_b("").decode('utf-8'),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR),
    _descriptor.FieldDescriptor(
      name='nome', full_name='matricula.Disciplina.nome', index=1,
      number=2, type=9, cpp_type=9, label=1,
      has_default_value=False, default_value=_b("").decode('utf-8'),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR),
    _descriptor.FieldDescriptor(
      name='professor', full_name='matricula.Disciplina.professor', index=2,
      number=3, type=9, cpp_type=9, label=1,
      has_default_value=False, default_value=_b("").decode('utf-8'),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR),
    _descriptor.FieldDescriptor(
      name='cod_curso', full_name='matricula.Disciplina.cod_curso', index=3,
      number=4, type=5, cpp_type=1, label=1,
      has_default_value=False, default_value=0,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR),
  ],
  extensions=[
  ],
  nested_types=[],
  enum_types=[
  ],
  serialized_options=None,
  is_extendable=False,
  syntax='proto3',
  extension_ranges=[],
  oneofs=[
  ],
  serialized_start=187,
  serialized_end=267,
)


_MATRICULA = _descriptor.Descriptor(
  name='Matricula',
  full_name='matricula.Matricula',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  fields=[
    _descriptor.FieldDescriptor(
      name='RA', full_name='matricula.Matricula.RA', index=0,
      number=1, type=5, cpp_type=1, label=1,
      has_default_value=False, default_value=0,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR),
    _descriptor.FieldDescriptor(
      name='cod_disciplina', full_name='matricula.Matricula.cod_disciplina', index=1,
      number=2, type=9, cpp_type=9, label=1,
      has_default_value=False, default_value=_b("").decode('utf-8'),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR),
    _descriptor.FieldDescriptor(
      name='ano', full_name='matricula.Matricula.ano', index=2,
      number=3, type=5, cpp_type=1, label=1,
      has_default_value=False, default_value=0,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR),
    _descriptor.FieldDescriptor(
      name='semestre', full_name='matricula.Matricula.semestre', index=3,
      number=4, type=5, cpp_type=1, label=1,
      has_default_value=False, default_value=0,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR),
    _descriptor.FieldDescriptor(
      name='nota', full_name='matricula.Matricula.nota', index=4,
      number=5, type=2, cpp_type=6, label=1,
      has_default_value=False, default_value=float(0),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR),
    _descriptor.FieldDescriptor(
      name='faltas', full_name='matricula.Matricula.faltas', index=5,
      number=6, type=5, cpp_type=1, label=1,
      has_default_value=False, default_value=0,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR),
  ],
  extensions=[
  ],
  nested_types=[],
  enum_types=[
  ],
  serialized_options=None,
  is_extendable=False,
  syntax='proto3',
  extension_ranges=[],
  oneofs=[
  ],
  serialized_start=269,
  serialized_end=377,
)

DESCRIPTOR.message_types_by_name['Mess'] = _MESS
DESCRIPTOR.message_types_by_name['MessInt'] = _MESSINT
DESCRIPTOR.message_types_by_name['Curso'] = _CURSO
DESCRIPTOR.message_types_by_name['Aluno'] = _ALUNO
DESCRIPTOR.message_types_by_name['Disciplina'] = _DISCIPLINA
DESCRIPTOR.message_types_by_name['Matricula'] = _MATRICULA
_sym_db.RegisterFileDescriptor(DESCRIPTOR)

Mess = _reflection.GeneratedProtocolMessageType('Mess', (_message.Message,), {
  'DESCRIPTOR' : _MESS,
  '__module__' : 'matricula_pb2'
  # @@protoc_insertion_point(class_scope:matricula.Mess)
  })
_sym_db.RegisterMessage(Mess)

MessInt = _reflection.GeneratedProtocolMessageType('MessInt', (_message.Message,), {
  'DESCRIPTOR' : _MESSINT,
  '__module__' : 'matricula_pb2'
  # @@protoc_insertion_point(class_scope:matricula.MessInt)
  })
_sym_db.RegisterMessage(MessInt)

Curso = _reflection.GeneratedProtocolMessageType('Curso', (_message.Message,), {
  'DESCRIPTOR' : _CURSO,
  '__module__' : 'matricula_pb2'
  # @@protoc_insertion_point(class_scope:matricula.Curso)
  })
_sym_db.RegisterMessage(Curso)

Aluno = _reflection.GeneratedProtocolMessageType('Aluno', (_message.Message,), {
  'DESCRIPTOR' : _ALUNO,
  '__module__' : 'matricula_pb2'
  # @@protoc_insertion_point(class_scope:matricula.Aluno)
  })
_sym_db.RegisterMessage(Aluno)

Disciplina = _reflection.GeneratedProtocolMessageType('Disciplina', (_message.Message,), {
  'DESCRIPTOR' : _DISCIPLINA,
  '__module__' : 'matricula_pb2'
  # @@protoc_insertion_point(class_scope:matricula.Disciplina)
  })
_sym_db.RegisterMessage(Disciplina)

Matricula = _reflection.GeneratedProtocolMessageType('Matricula', (_message.Message,), {
  'DESCRIPTOR' : _MATRICULA,
  '__module__' : 'matricula_pb2'
  # @@protoc_insertion_point(class_scope:matricula.Matricula)
  })
_sym_db.RegisterMessage(Matricula)


DESCRIPTOR._options = None

_MATRICULASERVICE = _descriptor.ServiceDescriptor(
  name='MatriculaService',
  full_name='matricula.MatriculaService',
  file=DESCRIPTOR,
  index=0,
  serialized_options=None,
  serialized_start=380,
  serialized_end=740,
  methods=[
  _descriptor.MethodDescriptor(
    name='cadastraNota',
    full_name='matricula.MatriculaService.cadastraNota',
    index=0,
    containing_service=None,
    input_type=_MATRICULA,
    output_type=_MESS,
    serialized_options=None,
  ),
  _descriptor.MethodDescriptor(
    name='atualizaNota',
    full_name='matricula.MatriculaService.atualizaNota',
    index=1,
    containing_service=None,
    input_type=_MATRICULA,
    output_type=_MESS,
    serialized_options=None,
  ),
  _descriptor.MethodDescriptor(
    name='removeNota',
    full_name='matricula.MatriculaService.removeNota',
    index=2,
    containing_service=None,
    input_type=_MATRICULA,
    output_type=_MESS,
    serialized_options=None,
  ),
  _descriptor.MethodDescriptor(
    name='consultaNota',
    full_name='matricula.MatriculaService.consultaNota',
    index=3,
    containing_service=None,
    input_type=_MATRICULA,
    output_type=_MESS,
    serialized_options=None,
  ),
  _descriptor.MethodDescriptor(
    name='consultaFalta',
    full_name='matricula.MatriculaService.consultaFalta',
    index=4,
    containing_service=None,
    input_type=_MATRICULA,
    output_type=_MESS,
    serialized_options=None,
  ),
  _descriptor.MethodDescriptor(
    name='consultaAluno',
    full_name='matricula.MatriculaService.consultaAluno',
    index=5,
    containing_service=None,
    input_type=_MATRICULA,
    output_type=_MESS,
    serialized_options=None,
  ),
])
_sym_db.RegisterServiceDescriptor(_MATRICULASERVICE)

DESCRIPTOR.services_by_name['MatriculaService'] = _MATRICULASERVICE

# @@protoc_insertion_point(module_scope)