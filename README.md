
Requirement
 1. Jdk 1.8
 2. Mysql

 How it works
  1. Create Db name logDB
  2. run  schema.sql script
  3. Java -jar parser.jar
  4. Type command in the Shell console
 
  e.g

   java -jar parser.jar

   shell:> com.ef.Parser com.ef.Parser --accesslog=/var/lib/tomcat8/backup/ --startDate=2017-01-01.13:00:00 --duration=hourly --threshold=100

  Note:  please provide  Folder path , then the system will iterate on each file  to store it into Db