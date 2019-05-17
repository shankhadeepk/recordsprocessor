Following RESTFul Endpoints created :

1.  To Get the on failed transactions
    The RESTFul API
     POST : http://<url>:<Port>/record/reports

    Request:
    {
    	"csvFileLocation":"Input CSV file location",
    	"xmlFileLocation":"Input XML file location"
    }

How to run the project

1. Build the project
   mvn clean install

2. Run the Project
   java -jar  ./target/recordsprocessor-1.0.0.jar

By default it will run at Port 8080