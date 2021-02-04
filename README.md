
# Exchange-Rate-Service

## Spring Microservice Test Assignment

The goal of this test assignment is to demonstrate an "Exchange Rate Service". The service
shall expose an API, which can be consumed by a frontend application.
Overview of your tasks
1. Create a Spring project using https://start.spring.io/.
a. Choose only the Spring modules needed for the task and do not add
unnecessary dependencies.
b. Choose Java or Kotlin, Gradle or Maven, according to your preferences.
The purpose of Spring here is to make it easy for you to create an HTTP API. If you
are not familiar with Spring, you can use any other framework or take some time to
learn the basics.
2. Ensure that the project builds as a standalone application.
3. Implement an API for the functionality described below.
4. (Optional) Show how to build, test, and run with docker.
User Stories
As a user, who accesses this service through a user interface, ...
1. I want to retrieve the ECB reference rate for a currency pair, e.g. USD/EUR or
HUF/EUR.
2. I want to retrieve an exchange rate for other pairs, e.g. HUF/USD.
3. I want to retrieve a list of supported currencies and see how many times they were
requested.
4. I want to convert an amount in a given currency to another, e.g. 15 EUR = ??? GBP
5. I want to retrieve a link to a public website showing an interactive chart for a given
currency pair.

The user interface is not part of this assignment.

### Implementation hints

* The European Central Bank publishes reference exchange rates on a daily basis. You can find them here: https://www.ecb.europa.eu/stats/policy_and_exchange_rates/euro_reference_exchange_rates/html/index.en.html
* The ECB uses EUR as the base currency. You can calculate non-EUR rates from the published data set.
* You won't need a database or other external storage for the task. You can keep the data in memory.
* We suggest you spend 3 to 4 hours on the task. It's OK to submit an incomplete solution.
* Decide how to spend your time working towards a solution. When time is running out, wrap up and explain possible next steps.



This project is an "Exchange Rate Service" service that exposes an API which can be consumed by a frontend application.

## Docker Instructions

To compile and package using Docker

```bash
docker build . -t demo
```

## To run the docker image

```bash
docker run -p 8080:8080 demo
```

## Postman Collection

There is a provided Postman collection to help you explore the API a bit.

## Summary

This project was developed using Spring Boot and Apache Commons IO for the use of its FileUtils. 
