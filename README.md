# me

To run the app

## mongodb
docker pull mongo 

docker run --name mongo -p 127.0.0.1:27017:27017 -d mongo:latest

git clone this repo and import into intellij and run the app as spring boot app

test cases unit test cases run TestSuite.java as TestSuite in intellij

for function goto http://localhost:8080/swagger-ui.html#/

## JMeter Test for Integration test

download jmeter from https://jmeter.apache.org/
import Transactions.jmx file onto JMeter
and run the test ::IMPORTANT: Keep the myfile.csv in same folder where you have Transactions.jmx file::
