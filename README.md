# GitHub Repository Viewer API
## Description

GitHub Repository Viewer API is a RESTful service designed to retrieve information about GitHub repositories for a specific user. This service interacts with the public GitHub API to fetch repository data, including repository names, owners, and a list of branches for each repository.

## Table of Contents

- [Description](#description)
- [Technologies](#technologies)
- [Getting Started](#getting-started)
    - [Prerequisites](#prerequisites)
    - [Installation](#installation-and-running)
- [Usage](#usage)
    - [Get User Repositories](#get-user-repositories)
    - [API Responses](#api-responses)
- [Versioning](#versioning)
- [Authors](#authors)
- [Contact](#contact)

## Technologies

- **Spring Boot** — to simplify configuration and deployment of the application.
- **Spring Web MVC** — to handle HTTP requests.
- **Jackson** — for parsing JSON data received from the GitHub API.
- **Lombok** - Reduces boilerplate code with annotations.
- **JUnit and Mockito** — for writing and running tests.

## Getting Started

### Prerequisites

To run this project, you need to have:

- Java JDK 11 or higher
- Apache Maven

### Installation and Running

Clone the repository:
```
$ git clone https://git-repository-viewer-api.git
$ cd git-repository-viewer-api
```
Build the project:
```
$ mvn clean install
```
Run the application:
```
$ mvn spring-boot:run
```
Once started, the application will be accessible at http://localhost:8080/.

## Usage
To use the API, follow these steps:

### Get User Repositories
* Request:
```
GET /api/github/users/{username}/repos
```
* Parameters:

    * `username` - GitHub username.
  
* Headers:

  * `Accept: application/json` - This header is required to ensure the server returns a JSON response.


* Example Request:
```
curl -X GET "http://localhost:8080/api/github/users/octocat/repos" -H  "Accept: application/json"
```
### API Responses

* 200 OK — the request was successfully processed.
* 404 Not Found — the specified user was not found.
* 403 Forbidden — request limit has been exceeded.
* 406 Not Acceptable — the Accept header does not match the expected value.

## Versioning

The current version of the API is v1. Support for future versions is planned to expand functionality or modify existing methods as needed.

## Authors

* Bohdan Mamchura

## Contact

If you have any questions or suggestions, please contact me at `b.work.pl@gmail.com`.
