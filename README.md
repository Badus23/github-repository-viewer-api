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

- Java JDK 21 or higher
- Apache Maven

### Installation and Running

Clone the repository:
```
$ git clone https://github.com/Badus23/github-repository-viewer-api.git
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

* Example JSON Response:

<div style="overflow: auto; height: 400px;">

```json
[
  {
    "name": "git-consortium",
    "ownerLogin": "octocat",
    "branches": [
      {
        "name": "master",
        "lastCommitSha": "b33a9c7c02ad93f621fa38f0e9fc9e867e12fa0e"
      }
    ]
  },
  {
    "name": "hello-worId",
    "ownerLogin": "octocat",
    "branches": [
      {
        "name": "master",
        "lastCommitSha": "7e068727fdb347b685b658d2981f8c85f7bf0585"
      }
    ]
  },
  {
    "name": "Hello-World",
    "ownerLogin": "octocat",
    "branches": [
      {
        "name": "master",
        "lastCommitSha": "7fd1a60b01f91b314f59955a4e4d4e80d8edf11d"
      },
      {
        "name": "octocat-patch-1",
        "lastCommitSha": "b1b3f9723831141a31a1a7252a213e216ea76e56"
      },
      {
        "name": "test",
        "lastCommitSha": "b3cbd5bbd7e81436d2eee04537ea2b4c0cad4cdf"
      }
    ]
  },
  {
    "name": "octocat.github.io",
    "ownerLogin": "octocat",
    "branches": [
      {
        "name": "gh-pages",
        "lastCommitSha": "c0e4a095428f36b81f0bd4239d353f71918cbef3"
      },
      {
        "name": "master",
        "lastCommitSha": "3a9796cf19902af0f7e677391b340f1ae4128433"
      }
    ]
  },
  {
    "name": "Spoon-Knife",
    "ownerLogin": "octocat",
    "branches": [
      {
        "name": "change-the-title",
        "lastCommitSha": "f439fc5710cd87a4025247e8f75901cdadf5333d"
      },
      {
        "name": "main",
        "lastCommitSha": "d0dd1f61b33d64e29d8bc1372a94ef6a2fee76a9"
      },
      {
        "name": "test-branch",
        "lastCommitSha": "58060701b538587e8b4ab127253e6ed6fbdc53d1"
      }
    ]
  },
  {
    "name": "test-repo1",
    "ownerLogin": "octocat",
    "branches": [
      {
        "name": "gh-pages",
        "lastCommitSha": "57523742631876181d95bc268e09fb3fd1a4d85e"
      }
    ]
  }
]
```

</div>

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
