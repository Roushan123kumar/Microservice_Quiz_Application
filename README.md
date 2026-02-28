# 🧠 Microservices-Based Quiz Application

A backend Quiz Application built using Microservices architecture with Spring Boot. The system allows quizzes to be created dynamically from a centralized question bank and supports service discovery, inter-service communication, and scalable deployment.

# 📌 Project Overview

This project demonstrates how multiple backend services can work together in a loosely coupled and scalable system.
The application is divided into independent services that communicate using REST APIs and Feign Clients, with Eureka Server handling service discovery.

# 🏗️ Architecture

The system consists of the following services:

#🔹 Question Service

Manages quiz questions and question bank

Stores:

Question title

Options

Correct answer

Category

Difficulty level

Provides APIs to:

Fetch random questions by category

Return questions based on question IDs

Calculate quiz scores

# 🔹 Quiz Service

Creates quizzes dynamically based on:

Category

Number of questions

Stores quiz metadata and question IDs

Communicates with Question Service using Feign Client

Provides APIs to:

Create quiz

Fetch quiz questions

Submit quiz and calculate score

# 🔹 Eureka Server

Acts as a Service Registry

Enables:

Service discovery

Load balancing between multiple instances

Allows Quiz Service to discover Question Service without hardcoded URLs

# 🛠️ Tech Stack

Java 17

Spring Boot

Spring Data JPA / Hibernate

REST APIs

PostgreSQL

MongoDB (knowledge & usage in backend projects)

Eureka Server (Netflix OSS)

Feign Client

Maven

Postman (API testing)

# 🗄️ Database Design
Quiz Service Database (PostgreSQL)

quiz table

id

title

quiz_question_ids table (created using @ElementCollection)

Stores list of question IDs for each quiz

Question Service Database

Stores questions with options, correct answer, category, and difficulty

Correct answers are never exposed to the client

# 🔄 Application Flow

Create Quiz

Client sends request to Quiz Service with category & number of questions

Quiz Service calls Question Service to fetch random question IDs

Quiz is stored with question IDs

Get Quiz Questions

Client requests quiz by ID

Quiz Service sends question IDs to Question Service

Question Service returns question details (without correct answers)

Submit Quiz

Client submits answers

Quiz Service forwards responses to Question Service

Question Service calculates score and returns result

# 🚀 API Endpoints
Quiz Service

POST /quiz/create

GET /quiz/get/{id}

POST /quiz/submit/{id}

Question Service

GET /question/generate

POST /question/getQuestions

POST /question/getScore

# 🧪 Testing

APIs tested using Postman

Multiple instances of Question Service run on different ports

Load balancing verified via Eureka Server

# 🎯 Key Learnings

Hands-on experience with Microservices architecture

Inter-service communication using Feign Client

Service discovery using Eureka Server

Designing relational databases for distributed systems

Debugging real-world service-to-service issues

Building secure APIs without exposing sensitive data

# 📷 Screenshots Included

Quiz creation API (Postman)

Fetch quiz questions response

Quiz submission & score calculation

Eureka dashboard showing registered services
