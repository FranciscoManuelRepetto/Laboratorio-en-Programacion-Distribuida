-Descomprimir en un directorio.

- Abrir una terminal en dicho directorio

- Generar las clases de java:

$ javac ObjetoCentral.java

$ javac Cliente.java

$ javac ObjetoHoroscopo.java

$ javac ObjetoClima.java

- Ejecutar los objetos del clima y del horoscopo en diferentes terminales en el mismo directorio (indistinto cual se ejecuta primero):


$ java ObjetoHoroscopo 127.0.0.1 20002

$ java ObjetoClima 127.0.0.1 20001



- Abrir otra terminal en el mismo directorio y ejecutar el servidor central

$ java ObjetoCentral 127.0.0.1 20000 127.0.0.1 20001 127.0.0.1 20002

- Abril otra terminal en el mismo directorio y ejecutar el cliente

$ java Cliente 127.0.0.1 20000

- Ingresar una signo (en minisculas y con tildes) y una fecha (dd/mm/aaaa), con el formato 'signo;fecha'

-Cerrar todo una vez terminado:

ctrl-c  en cada terminal.
