# ResidentialHub - Hostel Management System

A Spring Boot application for managing hostels, rooms, beds, bookings, and users with email notifications.

## Tech Stack

- **Backend:** Spring Boot 3.5.8, Java 17, Spring Data JPA, Hibernate
- **Database:** PostgreSQL 16
- **Email:** Gmail SMTP (Spring Mail)
- **Frontend:** Static HTML + vanilla JavaScript
- **Build:** Maven 3.9.11 (wrapper included)

## Prerequisites

Only **Docker** is required. Everything else runs inside containers or via the included Maven wrapper.

- [Docker Desktop](https://www.docker.com/products/docker-desktop/) installed and running
- Java 17 ([Eclipse Temurin](https://adoptium.net/) recommended) - needed to run the app via Maven wrapper
- Git (to clone the repo)

> **Windows users:** Use Git Bash, WSL, or PowerShell for the commands below.

## Quick Start

### 1. Clone the repository

```bash
git clone <repo-url>
cd ResidentialHub
```

### 2. Start PostgreSQL with Docker

```bash
docker run -d \
  --name residentialhub-pg \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=password \
  -p 5432:5432 \
  postgres:16
```

Wait a few seconds for Postgres to initialize, then create the database:

```bash
docker exec residentialhub-pg psql -U postgres -c 'CREATE DATABASE "Hostel_M";'
```

> The database name `Hostel_M` is **case-sensitive** -- the double quotes preserve the capital letters.

### 3. Configure email (optional)

Edit `src/main/resources/application.properties` and set your Gmail credentials:

```properties
spring.mail.username=your-email@gmail.com
spring.mail.password=your-app-password
```

To generate a Gmail App Password:
1. Go to [Google Account Security](https://myaccount.google.com/security)
2. Enable 2-Step Verification
3. Go to App Passwords, create one for "Mail"
4. Paste the 16-character password (with spaces) into the config

> If you skip this step, the app still works -- bookings succeed, but confirmation emails will fail silently.

### 4. Start the application

**On Linux / macOS:**

```bash
./mvnw spring-boot:run
```

**On Windows (Git Bash):**

```bash
./mvnw spring-boot:run -Dspring-boot.run.jvmArguments="-Duser.timezone=Asia/Kolkata"
```

> The timezone flag fixes a known issue where Windows sends the legacy `Asia/Calcutta` timezone which PostgreSQL 16 rejects.

**If port 8080 is already in use**, run on a different port:

```bash
./mvnw spring-boot:run \
  -Dspring-boot.run.jvmArguments="-Duser.timezone=Asia/Kolkata" \
  -Dspring-boot.run.arguments="--server.port=8081"
```

### 5. Verify it's running

Open your browser and go to:

```
http://localhost:8080
```

(or `http://localhost:8081` if you changed the port)

You should see the "Residential Hub - Find Your Perfect Stay" landing page.

## Usage

### Pages

| URL | Description |
|-----|-------------|
| `/` | Landing page -- browse hostels, search by city/area, book beds |
| `/login.html` | Login / Signup page |
| `/dashboard.html` | User dashboard -- view profile, edit details, see bookings |
| `/organisation.html` | Admin console -- manage organisations, hostels, buildings, floors, rooms, beds |

### User Flow

1. **Create an account** -- Click "Sign In" on the landing page, then "Create an account"
2. **Browse hostels** -- Hostel cards show vacancy counts; use the search bar to filter by city/area
3. **Book a bed** -- Click "Book This Hostel", choose sharing type, select a bed, confirm
4. **View bookings** -- After booking you're redirected to the dashboard where your bookings appear
5. **Login** -- Use your registered email + mobile number (mobile number is your password)

### API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/hostels` | List all hostels |
| GET | `/address?city=X&area=Y` | Search hostels by location |
| GET | `/bedss?status=Available&sharing=2&hostelId=1` | Get vacant beds |
| POST | `/signup` | Register a new user (returns user with ID) |
| POST | `/login` | Login with email + password (mobile number) |
| POST | `/booking` | Create a booking `{bedId, userId}` |
| GET | `/bookings/user/{userId}` | Get bookings for a user |
| GET | `/users` | List all users |
| GET | `/user/{id}` | Get user by ID |
| PUT | `/update/{id}` | Update user profile |

See the full list of CRUD endpoints for organisations, hostels, buildings, floors, rooms, beds, and payments in the controller classes.

## Project Structure

```
ResidentialHub/
  src/main/java/com/example/demo/
    controller/       # REST controllers + EmailSender service
    dto/              # Data Transfer Objects
    entity/           # JPA entities (Users, Bookings, Beds, Rooms, etc.)
    exception/        # Custom exceptions
    globalexceptionhandler/  # @ControllerAdvice handlers
    interfaces/       # Service interfaces
    repository/       # Spring Data JPA repositories
    service/          # Service implementations + event classes
  src/main/resources/
    static/           # HTML pages (index, login, dashboard, admin pages)
    application.properties  # App configuration
```

## Stopping the Application

**Stop the Spring Boot app:** Press `Ctrl+C` in the terminal

**Stop and remove the Postgres container:**

```bash
docker stop residentialhub-pg
docker rm residentialhub-pg
```

**To restart Postgres later** (data persists in the container):

```bash
docker start residentialhub-pg
```

## Troubleshooting

| Problem | Solution |
|---------|----------|
| `FATAL: invalid value for parameter "TimeZone": "Asia/Calcutta"` | Add `-Dspring-boot.run.jvmArguments="-Duser.timezone=Asia/Kolkata"` to the run command |
| `Port 8080 was already in use` | Add `-Dspring-boot.run.arguments="--server.port=8081"` or stop the process using port 8080 |
| `FATAL: database "Hostel_M" does not exist` | Run: `docker exec residentialhub-pg psql -U postgres -c 'CREATE DATABASE "Hostel_M";'` |
| Email not sending | Check Gmail App Password config in `application.properties`; ensure 2FA is enabled on the Gmail account |
| `Connection refused` to PostgreSQL | Make sure the Docker container is running: `docker ps` should show `residentialhub-pg` |
