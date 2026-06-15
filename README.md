# Spring Boot Sudoku Project

A full-stack Sudoku web application built with Java, Spring Boot, MongoDB Atlas, Thymeleaf, and Spring Security. The application provides an interactive Sudoku experience with secure user authentication, puzzle generation, score tracking, and leaderboards.

## Features

* User Registration and Login
* Secure Authentication with Spring Security
* Sudoku Puzzle Generation
* Multiple Difficulty Levels
* Interactive Sudoku Board
* Score Tracking System
* Leaderboard Ranking
* MongoDB Atlas Integration
* Responsive User Interface
* Session Management

## Tech Stack

### Backend

* Java
* Spring Boot
* Spring Security
* Spring Data MongoDB

### Database

* MongoDB Atlas

### Frontend

* Thymeleaf
* HTML5
* CSS3
* JavaScript

### Tools

* Maven
* Git
* GitHub

## Project Structure

```text
src/
├── main/
│   ├── java/
│   │   ├── controller/
│   │   ├── service/
│   │   ├── repository/
│   │   ├── model/
│   │   └── config/
│   └── resources/
│       ├── templates/
│       ├── static/
│       └── application.properties
```

## Getting Started

### Prerequisites

* Java 17 or later
* Maven
* MongoDB Atlas Account

### Installation

1. Clone the repository

```bash
git clone https://github.com/badoniadwait/Spring-Boot-Sudoku-Project.git
```

2. Navigate to the project directory

```bash
cd Spring-Boot-Sudoku-Project
```

3. Configure MongoDB Atlas connection in `application.properties`

```properties
spring.data.mongodb.uri=YOUR_MONGODB_CONNECTION_STRING
```

4. Run the application

```bash
mvn spring-boot:run
```

5. Open your browser and visit

```text
http://localhost:8080
```
