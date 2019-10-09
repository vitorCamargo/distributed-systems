import Pyro4
from Notas import Notas

daemon = Pyro4.Daemon()                  # executa o Daemon do Pyro4
ns = Pyro4.locateNS()                    # localiza o servidor de nomes
uri = daemon.register(Notas)       # registra Calculadora como um objeto Pyro
ns.register("NotasService", uri)   # registra o ojbeto com  um servidor de nomes

print("Objeto registrado.")
daemon.requestLoop()                     # servidor aguardando solicitações
