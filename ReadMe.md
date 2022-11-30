## Readme

### Deployment 

 #### Ant
Benötigt ANT 1.7+

````shell
git clone https://github.com/JavaWebserver-maxganguly-tgm.git
cd JavaWebserver-mganguly-tgm
ant -f build.xml
java -jar Server.jar
````

##### Alternativ
````shell
git clone https://github.com/JavaWebserver-maxganguly-tgm.git
cd JavaWebserver-mganguly-tgm
javac -sourcepath ./src/ -d ./out/ ./src/main/Main.java
cd out
jar --create --file ../Server.jar --main-class=main.Main main/Main.class server/*.class 
java -jar Server.jar
````
### Veränderungen

Die UI von 404.html ; index.html kann ohne Bedenken geändert werden

login.html und register.html verwenden die http POST parameter username und password für die dementsprechenden Variablen.

