dir /s /B *.java > sources.txt
javac -cp "src;lib/jfxrt.jar;lib/ojdbc6.jar;lib/sqlite-jdbc-3.8.11.2.jar;lib/jsqlparser-1.1.jar"  @sources.txt