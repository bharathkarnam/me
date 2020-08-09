# me

To run the app

### mongodb
docker pull mongo 

docker run --name mongo -p 127.0.0.1:27017:27017 -d mongo:latest

### git clone and IDE
git clone this repo and import into intellij(https://www.jetbrains.com/idea/) and run the app as spring boot app

test cases unit test cases run TestSuite.java as TestSuite in intellij

for swagger goto http://localhost:8080/swagger-ui.html#/
![](https://github.com/bharathkarnam/me/blob/master/img/api3.JPG)

### request from swagger
for uploading CSV use :POST /api/GetTransaction
#### choose file and hit Try it out!
![](https://github.com/bharathkarnam/me/blob/master/img/api1.JPG)

for geting the account related balance for a specific period of transaction use: POST /api/PostCSV
#### give accountid with dates, hit Try it out!
![](https://github.com/bharathkarnam/me/blob/master/img/api4.JPG)

## JMeter Test for Integration test

download jmeter from https://jmeter.apache.org/
import Transactions.jmx file onto JMeter
and run the test ::IMPORTANT: Keep the myfile.csv in same folder where you have Transactions.jmx file::
![](https://github.com/bharathkarnam/me/blob/master/img/api5.JPG)
