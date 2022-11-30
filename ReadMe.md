## Readme

### Deployment 

````shell
git clone https://github.com/maxganguly/JavaWebserver.git
cd JavaWebserver
javac -sourcepath ./src/ -d ./out/ ./src/main/Main.java
cd out
jar --create --file ../Server.jar --main-class=main.Main main/Main.class server/*.class 
cd..
java -jar Server.jar
````
### Veränderungen

Die UI von 404.html ; index.html kann ohne Bedenken geändert werden

login.html und register.html verwenden die http POST parameter username und password für die dementsprechenden Variablen.

