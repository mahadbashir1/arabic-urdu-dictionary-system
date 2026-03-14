# Arabic–Urdu Dictionary System

A Java Swing-based desktop application for managing and querying an Arabic-Urdu dictionary. The system allows users to manage Arabic words, their roots, patterns, and elaborate metadata, providing a comprehensive tool for linguistic study and reference.

## Features

- **Root Management**: Add, update, and delete Arabic roots.
- **Pattern Management**: Manage Arabic word patterns.
- **Word Management**: Store Arabic words with their base forms, Urdu meanings, and parts of speech. Link words to their corresponding roots and patterns.
- **Metadata Management**: Detailed linguistic metadata for words, including Gender, Number, Case, Verb Form, Tense, and Transitivity.
- **Graphical User Interface**: A user-friendly desktop experience built with Java Swing and organized using a modern CardLayout-based navigation system.
- **Database Backend**: Reliable data storage using MySQL with JDBC. Auto-initialization of tables upon first run.

## Prerequisites

- **Java Development Kit (JDK)**: Version 8 or higher.
- **MySQL Server**: Running locally or remotely.
- **MySQL Connector/J**: JDBC driver for MySQL (included in the `lib` directory: `mysql-connector-j-9.4.0.jar`).
- An IDE like Eclipse, IntelliJ IDEA, or VS Code (optional, but recommended for development).

## Setup Instructions

### 1. Database Setup

1. Make sure your MySQL Server is running.
2. The application is configured to connect to MySQL on `localhost:3306` with the default username `root` and an empty password. 
3. If your MySQL credentials are different, open `src/com/aud/dal/DBConnection.java` and update the `USER` and `PASSWORD` constants:
   ```java
   private static final String USER = "your_username";
   private static final String PASSWORD = "your_password";
   ```
4. The database `arabic_urdu_dictionary` and its required tables (`Root`, `Pattern`, `Word`, `Metadata`) will be automatically created upon launching the application for the first time.

### 2. Running the Application

#### Option A: Using an IDE (Eclipse, IntelliJ, etc.)
1. Import the project into your IDE.
2. Ensure that the `lib/mysql-connector-j-9.4.0.jar` file is added to your project's Build Path/Libraries.
3. Locate the main class: `src/com/aud/main/Main.java`.
4. Run `Main.java` to start the application.

#### Option B: Using Command Line
1. Open a terminal and navigate to the root directory of the project.
2. Compile the Java files (ensure the JDBC driver is in the classpath):
   ```bash
   javac -d bin -cp "lib/mysql-connector-j-9.4.0.jar" src/com/aud/**/*.java
   ```
3. Run the application:
   ```bash
   java -cp "bin;lib/mysql-connector-j-9.4.0.jar" com.aud.main.Main
   ```
   *(Note: On Linux/macOS, use `:` instead of `;` in the classpath).*

## Architecture & Technologies
- **Frontend**: Java Swing.
- **Backend / BLL**: Business Logic Layer interfaces and implementations for separation of concerns.
- **DAL**: Data Access Layer employing JDBC for database connectivity and CRUD operations.
- **Database**: MySQL.
