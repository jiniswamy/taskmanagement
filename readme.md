# TODO Application
This application is designed by me to showcase Spring boot technologies.  This is a work in progress.  I hope to highlight many spring modules with this project including Spring Web, JPA, Caching, Security and Validation.  My goal is to develop this as a cloud native application.  The application is a Task Management app powered by REST services.  Currently it has the following features: - 

- Spring REST Controllers.  They return 200 for GET calls, 201 for POST, 202 (ACCEPTED) for PUT and 204 (NO CONTENT) for delete calls
- Exception Controller Advice to handle errors. A HTTP 404 code is returned if the record could not be found
- Spring Authentication.  Currently it has an in memory authentication. Future TODO is to do JDBC authentication.
- Spring JPA Repo is used to interact with the database. Future TODO is to extend PagingAndSortingRepository.
- Spring Actuator. We can ping the /health and the /info endpoints, /metrics and /logger endpoints.  These are very useful for metrics and   managing the health of the application.
- Spring Caching which shows the cached stats data
- Spring Scheduled which has the capability to run every minute.  Future TODO is to send SMS messages via Twilio Integration
- Liquibase is used for database table creation.  Developers have full control over their database, say goodbye to DDL sql scripts.
- Authenticate user and return  401 code if the user is not authenticated
- Unit Tests have been written using MockitoJunitRunner and Spring Test Controller
- Postman Calls with Tests have been built.  These tests have been executed using POSTMAN Runner. A screenshot is given below

Follows many of the 12 Factor App (https://12factor.net/) guiding principles for Cloud Native Development.

   1.  Codebase in github and heroku.  All deploys come out of this codebase.
   2.  All dependencies are isolated into property files.  Example is the JDBC_DATABASE_URL to set the application database url.
   3.  All configuration like passwords are set outside the repo.  It was tested using Heroku's CLI config:set command.
   4.  Backing services as resources: Liquibase is used to configure the database.
   5.  Build, Release and Run are all separated. Tested with Heroku.
   6.  The app  is a stateless process.
   7.  Port Binding is implemented.  Please look at the Procfile for how this was achieved for Heroku.
   9.  Disposability: Part of Spring Boot, gracefully shuts down
   10.  Dev/Prod Parity: The artifact is the same between dev and prod.  The same jar file can be moved between environments.
   11.  Logs as event streams. The output logs are written to the console and not to a file. 
	     Tested this by using Heroku CLI logs command.
   12.  Admin Processes: N/A. No admin processes need to be run.

![Alt text](Todo_Test_run.PNG?raw=true "Postman REST calls with tests")
