# rubiera - simple weather client
Dr. Rubiera is the most famous meteorologist of Cuba :-)

# How to run rubiera?
Necesary tools:
```
Java
Maven
Docker
Docker-composer
```
From project folder run ```./build.sh``` script to execute the mvn package process and build the docker image.
Then run ```docker-compose up```.
The app should be available at http://localhost:8080

# About rubiera
This service make use of a couple of APIs to query location(https://api.opencagedata.com) and weather(http://api.openweathermap.org).

#### Both APIs has usage restrictions:
##### https://api.opencagedata.com
Is locked to 2500 request/day and 1 request/second to address this every location query is cached and after the limit is reach the service gets locked till the threshold is reached and the service can be queried again. The requests are queued and processed 1 second at the time.

##### http://api.openweathermap.org
They recommend not to do more than a request for a weather update within 10 minutes for the same location to address this the reverse geocoding service get the city name then is searched in a cities file which gets loaded on memory at startup to get the city ID, openweathermap recommends to search by ID to get more accurate results, so the result is cached and won't get updated after 10 minutes for the same city, and all request from the same city would be returned from the cached weather report for that city.

# Improvements
* Add swagger to publish API docs.
* Add comments to code.
* Add unit tests.
* Add complete postman tests.
* Add classes diagram and code flow diagram. 

