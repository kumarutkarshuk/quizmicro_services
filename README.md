# MicroQuiz Services

## Overview
This project is a microservices-based Quiz App developed using Spring Boot. It consists of two main services: the Quiz Service and the Question Service. The application manages questions, quizzes, and score calculations.

## Features
- **Quiz Service**: Manages quizzes, including creation, retrieval, and score calculation.
- **Question Service**: Handles questions associated with quizzes, including addition and retrieval.
- **Service Registry and Discovery**: Implemented using Eureka for management and discovery of services.
- **Inter-Service Communication**: Utilized OpenFeign for communication between services and load balancing.
- **Reactive API Gateway**: Centralized routing for handling requests.
