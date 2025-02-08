Log Parser App

We are supporting following APIs
1.  Get distinct IP addresses that hit the server on a given day
2.  Get the hourly traffic on a given day
3.  Fetch the List of the IP addresses that contribute to 85% of the traffic on a given day.
4.  Fetch the List of the hours contributing to the 70% of overall traffic on a given day.

How to run the Application:-
1. Please make sure you have Java 17 installed.
2. Run `java -jar .\target\logParser-0.0.1-SNAPSHOT.jar`

How to test the APIs(Built using Spring Rest):-
Go the below provided swagger url for all the above-mentioned api's
1. http://localhost:8080/swagger-ui/index.html#

Note:- Date format supported DD/Mon/YYYY.  e.g :- 30/Jan/2024.

How to test Dashboard(Built using Spring MVC):-
1. Url of homePage:- http://localhost:8080/
2. Url to View Unique IP Dashboard:- http://localhost:8080/log-parser/dashboard/unique-ip?date=30/Jan/2024
3. Url to View Hourly Traffic Dashboard:- http://localhost:8080/log-parser/dashboard/hourly-traffic?date=30/Jan/2024
4. Url to View TOP CONTRIBUTING IP Dashboard :- http://localhost:8080/log-parser/dashboard/top-contributing-ips?date=30/Jan/2024
5. Url to View TOP Traffic HOUR Dashboard :- http://localhost:8080/log-parser/dashboard/top-traffic-hours?date=30/Jan/2024


