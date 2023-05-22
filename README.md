## interview: adserver 

This project uses Micronaut as the basis of the implementation.  Micronaut is a lightweight 
framework for implementing microservices in java

- [User Guide](https://docs.micronaut.io/3.9.1/guide/index.html)
- [API Reference](https://docs.micronaut.io/3.9.1/api/index.html)
- [Configuration Reference](https://docs.micronaut.io/3.9.1/guide/configurationreference.html)
- [Micronaut Guides](https://guides.micronaut.io/index.html)
---
## Getting Started on MacOS
- Install Homebrew, if you don't already have it installed.
- Install Java 17 SDK - on Mac, `brew install java` should do the trick
- Install Micronaut - `brew install micronaut`

Clone the repo locally, and cd into the project directory.  
- To run unit tests: `./gradlew test`
- To run the application and submit ad requests: `./gradlew run`

## Troubleshooting
If you attempt to run it, and you get an error about JDK 17 being unavailable, 
this link has information to assist in fixing: https://medium.com/@manvendrapsingh/installing-many-jdk-versions-on-macos-dfc177bc8c2b  