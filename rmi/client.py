import Pyro4

def main():
    Pyro4.locateNS("127.0.0.1", 9090)
    notas = Pyro4.Proxy("PYRONAME:NotasService")
    
    print("inserir", notas.cadastraNota(1921959, 'MB', 2019, 3, 10, 0))

if __name__ == "__main__":
    main()