# Getting Started

## Overview

Demo application - BE REST API for a blog platform, driven by Spring Boot & Java 11.

Functionality:
- Create post
- Add comment to post
- Get all posts
- Get all posts by tags

Persistence is currently an in-memory H2 database (so will not persist between application runs). But this can easily be switched out for something more permanent.

## Building / running in Docker container

1. Compile the application
   - `mvn clean package`
2. Docker build from top level dir
   - `docker build -t blog --build-arg APPNAME=blog --build-arg VERSION=0.0.1-SNAPSHOT .`
3. Run docker container
   - `docker run -p 8080:8080 blog`
4. Will be reachable at: http://localhost:8080/swagger-ui/index.html