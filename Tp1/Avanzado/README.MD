-Descomprimir en un directorio.

- Abrir una terminal en dicho directorio

- Generar las clases de java:

$ javac ServidorCemtral.java

$ javac Cliente.java

$ javac ServidorHoroscopo.java

$ javac ServidorClima.java

- Ejecutar los servidores del clima y del horoscopo en diferentes terminales en el mismo directorio (indistinto cual se ejecuta primero):


$ java ServidorHoroscopo 20002

$ java ServidorClima 20001



- Abrir otra terminal en el mismo directorio y ejecutar el servidor central

$ java ServidorCentral 20000 20001 20002

- Abril otra terminal en el mismo directorio y ejecutar el cliente

$ java Cliente 20000

- Ingresar una signo (en minisculas y con tildes) y una fecha (dd/mm/aaaa), con el formato 'signo;fecha'

-Cerrar todo una vez terminado:

ctrl-c  en cada terminal.
