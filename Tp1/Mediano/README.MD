-Descomprimir en un directorio.

- Abrir una terminal en dicho directorio

- Generar las clases de java:

$ javac Servidor.java

$ javac Cliente.java

$ javac ServidorHoroscopo.java

$ javac ServidorPronosticoClima.java

- Ejecutar los servidores del clima y del horoscopo en diferentes terminales en el mismo directorio (indistinto cual se ejecuta primero):


$ java ServidorHoroscopo

$ java ServidorPronosticoClima



- Abrir otra terminal en el mismo directorio y ejecutar el servidor central

$ java ServidorCentral

- Abril otra terminal en el mismo directorio y ejecutar el cliente

$ java Cliente

- Ingresar "c" para conectarse con el servidor del clima o "h" para el del horoscopo y presionar enter

-Cerrar todo una vez terminado:

ctrl-c  en cada terminal.
