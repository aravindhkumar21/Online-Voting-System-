# Secure Online Voting System

A web-based Online Voting System developed using Java, JSP, Servlets, JDBC, SQL Server, HTML, CSS, and Apache Tomcat.

## Project Overview

The Secure Online Voting System provides a digital platform for conducting elections efficiently and securely. The system allows voters to cast votes online while administrators can manage parties, voters, and election results.

## Features

### Admin Module
- Admin Login
- Add New Admin
- Add Political Parties
- View Registered Voters
- View Election Results
- Manage Election Data

### Voter Module
- Voter Registration
- Voter Login
- Cast Vote
- One Vote Per Voter Restriction
- View Voting Status

## Technologies Used

### Frontend
- HTML
- CSS
- JSP

### Backend
- Java
- Servlets
- JDBC

### Database
- Microsoft SQL Server

### Server
- Apache Tomcat 9

### Build Tool
- Maven

## Database Tables

### admin
Stores administrator information.

### login
Stores voter login credentials.

### voter
Stores voter card number and selected party.

### partytable
Stores political party information.

### contact
Stores contact information.

## Project Structure

```
src/
 ├── com.Controller
 ├── com.Dao
 ├── com.Model

WebContent/
 ├── jsp pages
 ├── css
 ├── images
```

## Setup Instructions

### 1. Clone Repository

### 2. Configure SQL Server

Create database:


Import the provided SQL script.

### 3. Configure Database Connection

Update connection details inside:



Example:



### 4. Build Project



### 5. Deploy

Deploy generated WAR file to:

Start Tomcat Server.

### 6. Run Application



## Security Features

- One Vote Per Voter
- Session Based Authentication
- Role Based Access
- Database Validation

## Future Enhancements

- Blockchain Integration
- OTP Verification
- Email Notifications
- Election Scheduling
- Result Analytics Dashboard

## Author

Aravindh Kumar P

## License

This project is developed for educational and academic purposes.
