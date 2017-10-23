Simple desktop Calendar application designed a while ago to learn Java Swing from this tutorial: www.dreamincode.net/forums/topic/25042-creating-a-calendar-viewer-application/ Recently updated to use JDBC. 

Before running: Import database/calendar_db.sql file into MySQL on your machine. Then, create a db.properties file in src/main/resources/ containing the following: 
 - db.url=[Database URL] (jdbc:mysql://localhost/calendar_db if running on localhost)
 - db.user=[Username]
 - db.passwd=[Password]
