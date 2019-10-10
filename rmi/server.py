# Description: This is the Python Server to RMI Project.
# Author: Gabriel David Sacca, Vitor Bueno de Camargo
# Created at: October, 09th. 2019
# Updated at: October, 09th. 2019

import Pyro4
from Notas import Notas

daemon = Pyro4.Daemon()
ns = Pyro4.locateNS()
uri = daemon.register(Notas)
ns.register("NotasService", uri)

print("Objeto registrado.")
daemon.requestLoop()
