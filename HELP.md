# Getting Started

This is a simple back-end JWT-type authentication server for testing front-end web applications.
Server runs on http://localhost:8000

A simple in-memory database H2 was used as an authenticated users' database resource. This means that all user entries created by clients will be removed after the server stops.
Database resource can be accessed at http://localhost:8000/h2-console with credentials:
* user: sa
* password: password

### Known bugs:
* Registers duplicate users
* No API error handling