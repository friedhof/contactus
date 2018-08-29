*Contactus*
===========

**Provide random user data as a demo service for RESTful clients.**

*Copyright (C) 2016-2018 Marcus Fihlon*

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.

# What is *Random User*?

When I do workshops and trainings on how to develop RESTful clients using HTML and other technologies, I need a RESTful service which provides data for the demo application. This is what *Random User* does. After starting the server, it provides 12 records containing user data which can be requested using `GET` requests, added using `POST` requests, modified using `PUT` requests and deleted using `DELETE` requests. The API documentation of the running server can be accessed with every browser.

# Running *Random User*

To run *Random User* you need to have a working [Docker](https://www.docker.com/) installation. The *Random User* docker image is available on [DockerHub](https://hub.docker.com/r/dockergarten/randomuser/). To run *Random User*, you have to specify a port mapping to map the ports of the application server inside the container (8080) to a port on your machine (e.g. 1001). The complete docker call looks like this:

`docker run -it -p [local port]:8080 --name randomuser dockergarten/randomuser`

Example:

`docker run -it -p 1001:8080 --name randomuser dockergarten/randomuser`

The parameter `-it` creates an interactive pseudo-TTY which shows the output of the application server. Stop the server by pressing `CTRL-C`.

# Access the API documentation

To access the API documentation point your web browser to the directory `/apidocs/` of the running server. Be sure to end the path with the slash or the CSS styles of the API documentation will be messed up. The URL looks like this:

`http://localhost:[local port]/apidocs/`

Example:

`http://localhost:1001/apidocs/`

# Release Notes

## Version 1.3

- Fixing CORS problem (location header was not exposed)

## Version 1.2

- Adding support for CORS (cross-origin resource sharing)

## Version 1.1

- Updating application server
- Limiting RAM usage

## Version 1.0

- First version, providing 12 random users persisted only in memory.

# Technology

The server is based on Java EE technology and provides high-performance, RESTful web services. Everything is tied together using a [Maven](https://maven.apache.org/) build.

On the first call to the API the server will connect to the service of [Random User Generator](https://randomuser.me/) and load 12 records of randomly generated user data. This data is stored in memory and served on `GET` requests. The data will *not* be persisted, on subsequent starts the random user data will be requested again which will result in different records provided by the server.

# How to contribute to *Random User*

## Keep your fork in sync

If you fork this repository, GitHub will not keep your fork in sync with this repository. You have to do it on your own.

1. If not already done, add this repository as an upstream to your repository:<br/>`git remote add upstream https://github.com/dockergarten/randomuser.git`
2. Verify that this repository was added successfully:<br/>`git remote -v`
3. Fetch branches and commits from this repository to your local repository:<br/>`git fetch upstream`
4. If you are not on your local master branch, check it out:<br/>`git checkout master`
5. Merge the changes from this repositories master branch into your repository):<br/>`git merge upstream/master`
7. Push your updated repository to your GitHub fork:<br/>`git push origin develop`

## Frequently Asked Questions

1. When I try to push, I get a `non-fast-forward updates were rejected` error.<br/>*Your local copy of a repository is out of sync with, or behind the upstream repository, you are pushing to. You must retrieve the upstream changes, before you are able to push your local changes.*
2. On the first request after server startup I get an `Internal Server Error`.<br/>*There is an open issue with the Glassfish application server which runs in the docker container. Hopefully it will be fixed soon. After the first request failed all further requests will work fine.*
