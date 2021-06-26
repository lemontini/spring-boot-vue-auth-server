# Getting Started

This is a simple back-end JWT-type authentication server for testing front-end web applications.
Server runs on http://localhost:8000

A simple in-memory database H2 was used as an authenticated users' database resource. This means that all user entries created by clients will be removed after the server stops.
Database resource can be accessed at http://localhost:8000/h2-console with credentials:
* user: sa
* password: password

### Supported:
* new user registration by making POST request to server an object with user credentials (included in body as json object):
```
{
    username: <USER_NAME>
    email: <USER_EMAIL>
    password: <PASSWORD>
}
```
* user login with credentials registered in the previous step: by making POST request to server an object with user credentials (included in body as json object):
```
{
  email: <USER_EMAIL>
  password: <PASSWORD>
}
```
* accessing main endpoint ("/"): by making a GET request with additional parameter of token, received form the login step - http://localhost:8000/?token=<TOKEN_RECEIVED_AT_LOGIN>

### Known bugs:
* none at this time
