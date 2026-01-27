# Bakery Management System

A Spring Boot-based web application for managing a bakery's operations including product inventory, customer orders, user management, and reviews.

## 📋 Table of Contents

- [Project Overview](#project-overview)
- [Architecture](#architecture)
- [Features](#features)
- [Prerequisites](#prerequisites)
- [Installation & Setup](#installation--setup)
- [Running the Application](#running-the-application)
- [Project Structure](#project-structure)
- [Configuration](#configuration)
- [Known Issues & Limitations](#known-issues--limitations)
- [Security Considerations](#security-considerations)
- [Future Improvements](#future-improvements)
- [Troubleshooting](#troubleshooting)

---

## 📱 Project Overview

The **Bakery Management System** is a web-based application designed to streamline bakery operations. It provides features for:
- Managing product inventory with images
- Processing customer orders
- Handling user authentication and profiles
- Collecting product reviews and ratings
- Vendor management

**Technology Stack:**
- **Backend**: Spring Boot 3.4.5, Java 17
- **Frontend**: JSP with JSTL
- **Data Persistence**: File-based storage (text files)
- **Build Tool**: Maven
- **Server**: Embedded Tomcat (Spring Boot)

---

## 🏗️ Architecture

### Three-Tier Architecture

```
┌─────────────────────────────────┐
│       Presentation Layer         │
│  (JSP Views & Controllers)       │
├─────────────────────────────────┤
│       Business Logic Layer       │
│  (Services with Validation)      │
├─────────────────────────────────┤
│       Data Access Layer          │
│  (DAO - File-based Operations)   │
├─────────────────────────────────┤
│       Persistence Layer          │
│  (Text Files in /data directory) │
└─────────────────────────────────┘
```

### Layer Responsibilities

| Layer | Components | Responsibility |
|-------|-----------|-----------------|
| **Controller** | AuthController, ProductController, OrderController, etc. | Handle HTTP requests, manage sessions, redirect flows |
| **Service** | UserService, ProductService, OrderService, etc. | Business logic, validation, orchestration |
| **DAO** | UserDAO, ProductDAO, OrderDAO, etc. | File I/O operations, data persistence |
| **Model** | User, Product, Order, Review, Vendor | Data entities with getters/setters |

---

## ✨ Features

### 👤 User Management
- User registration with validation
- Login/Logout functionality
- Role-based access control (Admin, User)
- User profile management
- Password validation (minimum 5 characters)

### 🍰 Product Management
- Add/Edit/Delete products
- Product categories (Bread, Cake, Pastry, etc.)
- Image upload with compression
- Stock management (initial, current, available quantities)
- Product descriptions and pricing

### 📦 Order Management
- Place orders with delivery details
- View order history
- Order status tracking (PENDING, COMPLETED, CANCELLED)
- Payment method selection (Cash, Card, Digital Wallet, UPI)
- Order confirmation with delivery information

### ⭐ Reviews & Ratings
- Add product reviews with ratings (1-5 stars)
- View all reviews for a product
- Customer-identified reviews

### 🏪 Vendor Management
- Add/Edit/Delete vendors
- Vendor information storage
- Vendor product association

### 📊 Admin Dashboard
- View all orders
- Manage products and vendors
- User management interface
- System overview

---

## 📋 Prerequisites

- **Java 17** or higher installed
- **Maven 3.6+** for building
- **Windows, macOS, or Linux**
- Minimum **2GB RAM** for development
- **1GB disk space** for application and data files

### Verify Installation

```bash
java -version
mvn -version
```

---

## 🚀 Installation & Setup

### 1. Clone/Download the Project

```bash
cd d:\Project\Bakery-Management
```

### 2. Build the Project

```bash
mvn clean install
```

This will:
- Download all dependencies
- Compile Java source files
- Package the application

### 3. Verify Build Success

```bash
mvn clean build
```

Expected output should show `BUILD SUCCESS`.

---

## ▶️ Running the Application

### Option 1: Using Maven (Recommended)

```bash
mvn spring-boot:run
```

### Option 2: Using JAR File

After building:

```bash
java -jar target/backery_management-0.0.1-SNAPSHOT.jar
```

### Option 3: Using IDE

- Open project in VS Code or IntelliJ IDEA
- Run `BackeryManagementApplication.java`
- Click "Run" button

### Access the Application

Once running, open your browser and navigate to:

```
http://localhost:8080
```

### Default Credentials

| Username | Password | Role |
|----------|----------|------|
| Admin | admin@12345 | Admin |
| User1 | User1@12345 | User |
| User2 | User2@12345 | User |

---

## 📁 Project Structure

```
Bakery-Management/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/backery/backery_management/
│   │   │       ├── BackeryManagementApplication.java      # Spring Boot entry point
│   │   │       ├── controller/                             # HTTP request handlers
│   │   │       │   ├── AuthController.java                # Login/Signup
│   │   │       │   ├── ProductController.java             # Product management
│   │   │       │   ├── OrderController.java               # Order management
│   │   │       │   ├── CustomerController.java            # Customer views
│   │   │       │   ├── UserController.java                # User management
│   │   │       │   ├── VendorController.java              # Vendor management
│   │   │       │   ├── HomeController.java                # Admin dashboard
│   │   │       │   └── LogoutController.java              # Session management
│   │   │       ├── service/                                # Business logic
│   │   │       │   ├── UserService.java                   # User operations & validation
│   │   │       │   ├── ProductService.java                # Product operations
│   │   │       │   ├── OrderService.java                  # Order processing
│   │   │       │   ├── OrderFileService.java              # Order file operations
│   │   │       │   └── VendorService.java                 # Vendor operations
│   │   │       ├── dao/                                    # Data access objects
│   │   │       │   ├── UserDAO.java                       # User file I/O
│   │   │       │   ├── ProductDAO.java                    # Product file I/O
│   │   │       │   ├── OrderDAO.java                      # Order file I/O
│   │   │       │   ├── ReviewDAO.java                     # Review file I/O
│   │   │       │   └── VendorDAO.java                     # Vendor file I/O
│   │   │       └── model/                                  # Data entities
│   │   │           ├── User.java
│   │   │           ├── Product.java
│   │   │           ├── Order.java
│   │   │           ├── Review.java
│   │   │           └── Vendor.java
│   │   ├── resources/
│   │   │   └── application.properties                      # Configuration
│   │   └── webapp/
│   │       ├── resources/
│   │       │   └── images/products/                        # Product images
│   │       └── WEB-INF/
│   │           └── views/                                  # JSP templates
│   │               ├── login.jsp
│   │               ├── signup.jsp
│   │               ├── index.jsp
│   │               ├── products.jsp
│   │               ├── orders.jsp
│   │               ├── single-product.jsp
│   │               ├── payment-options.jsp
│   │               ├── payment-success.jsp
│   │               └── ... (other views)
│   └── test/
│       └── java/
│           └── BackeryManagementApplicationTests.java      # Unit tests
├── data/                                                    # Data persistence
│   ├── users.txt                                           # User data
│   ├── products.txt                                        # Product inventory
│   ├── orders.txt                                          # All orders
│   ├── reviews.txt                                         # Product reviews
│   ├── vendors.txt                                         # Vendor data
│   └── orders/
│       ├── all_orders.txt                                  # Aggregated orders
│       └── user_*_orders.txt                               # User-specific orders
├── uploads/
│   └── products/                                           # Uploaded product images
├── pom.xml                                                  # Maven configuration
├── mvnw & mvnw.cmd                                         # Maven wrapper
└── README.md                                               # This file
```

---

## ⚙️ Configuration

### Application Properties

Located in: `src/main/resources/application.properties`

```properties
# Application name
spring.application.name=BackeryManagement

# JSP view configuration
spring.mvc.view.prefix=/WEB-INF/views/
spring.mvc.view.suffix=.jsp

# File upload configuration
spring.servlet.multipart.max-file-size=1024MB
spring.servlet.multipart.max-request-size=1024MB
```

### Session Configuration

- **Session tracking**: URL-based (via HttpSession)
- **Session attributes**: username, role, customerName
- **Session timeout**: Default Spring Boot timeout

### File Storage Paths

| Data Type | Location |
|-----------|----------|
| User data | `data/users.txt` |
| Products | `data/products.txt` |
| Orders | `data/orders/all_orders.txt` |
| Reviews | `data/reviews.txt` |
| Vendors | `data/vendors.txt` |
| Product Images | `uploads/products/` |

### Data File Formats

#### Users (CSV)
```
id,username,password,email,role
1,Admin,admin@12345,admin@gmail.com,Admin
2,User1,User1@12345,User1@gmail.com,User
```

#### Products (Delimiter: `|||`)
```
id|||name|||description|||category|||price|||quantityAvailable|||initialStock|||currentStock|||image
```

#### Orders (Multi-line format)
```
Order ID:1
User ID:2
Product:Cake
Quantity:5
Status:PENDING
Payment Method:Card
---
```

---

## ⚠️ Known Issues & Limitations

### Critical Issues

| Issue | Impact | Severity |
|-------|--------|----------|
| **No password hashing** | Passwords stored in plaintext in `data/users.txt` | 🔴 CRITICAL |
| **File-based persistence** | Data corruption in concurrent access | 🔴 CRITICAL |
| **No CSRF protection** | Forms vulnerable to CSRF attacks | 🔴 CRITICAL |
| **No input sanitization** | Potential injection vulnerabilities | 🔴 CRITICAL |

### Functional Limitations

1. **Concurrent Access**
   - Multiple simultaneous users may cause data loss
   - No file locking mechanism
   - Manual ID generation can cause collisions

2. **Scalability**
   - Entire dataset loaded in memory on startup
   - Linear O(n) search for any lookups
   - File I/O on every operation

3. **Image Handling**
   - Image compression happens on every upload (slow)
   - No image optimization or caching
   - File path traversal not validated

4. **Data Integrity**
   - No atomic transactions
   - Incomplete writes not detected
   - No backup/recovery mechanism

5. **Order Processing**
   - Payment processing not implemented (selection only)
   - No order confirmation emails
   - No inventory reserved during checkout

### Validation Issues

- **Username**: Only alphanumeric, 3-20 chars (no special chars allowed)
- **Password**: Only length validation (minimum 5 chars)
- **Email**: Basic regex, doesn't validate all valid formats
- **Product Stock**: No warning for low inventory

---

## 🔒 Security Considerations

### ⚠️ Current Security Gaps

1. **Authentication**
   - ❌ Passwords not hashed (use BCrypt/Argon2)
   - ❌ No session timeout configuration
   - ❌ No brute force protection

2. **Authorization**
   - ⚠️ Role checking exists but inconsistent
   - ⚠️ No method-level security annotations

3. **Data Protection**
   - ❌ No HTTPS enforcement
   - ❌ Sensitive data in logs
   - ❌ No SQL injection prevention (if DB added)

4. **Input Validation**
   - ❌ No XSS protection in JSP
   - ❌ No input sanitization
   - ⚠️ Partial validation in Service layer

### 🛡️ Recommendations

**Immediate Actions:**

```java
// Use Spring Security for password hashing
// Add BCrypt dependency:
// <dependency>
//     <groupId>org.springframework.boot</groupId>
//     <artifactId>spring-boot-starter-security</artifactId>
// </dependency>

// Example:
BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
String hashedPassword = encoder.encode(plainPassword);
```

**For Production:**

1. Implement Spring Security
2. Use HTTPS/TLS
3. Add CSRF tokens to forms
4. Implement proper exception handling
5. Add security headers (CSP, X-Frame-Options, etc.)
6. Use parameterized queries if switching to DB
7. Implement rate limiting
8. Add audit logging

---

## 🚀 Future Improvements

### High Priority

- [ ] **Migrate to Database** (MySQL/PostgreSQL)
  - Replace file-based persistence
  - Add proper transaction support
  - Enable concurrent access

- [ ] **Implement Spring Security**
  - Password hashing (BCrypt)
  - CSRF protection
  - Session management
  - Role-based access control (RBAC)

- [ ] **Add Comprehensive Validation**
  - Jakarta Validation annotations
  - Custom validators
  - Input sanitization

### Medium Priority

- [ ] **Payment Gateway Integration** (Stripe, PayPal)
- [ ] **Email Notifications** (Order confirmation, status updates)
- [ ] **Image Optimization** (Caching, CDN)
- [ ] **Logging Framework** (SLF4J + Logback)
- [ ] **API Documentation** (Swagger/OpenAPI)
- [ ] **Unit Testing** (JUnit 5, Mockito)

### Nice to Have

- [ ] **Advanced Search & Filtering**
- [ ] **Dashboard Analytics** (Sales, inventory reports)
- [ ] **Vendor Dashboard** (Separate portal)
- [ ] **Mobile-friendly UI** (Responsive design)
- [ ] **Multi-language Support** (i18n)
- [ ] **REST API** (For mobile apps)
- [ ] **Docker Containerization**

---

## 🔧 Troubleshooting

### Application won't start

**Error**: `Cannot find symbol: class BackeryManagementApplication`

**Solution**:
```bash
mvn clean compile
mvn spring-boot:run
```

### Port 8080 already in use

**Solution**:
```bash
# Change port in application.properties
server.port=8081
```

### JSP views not found (404)

**Verify**:
- JSP files exist in `src/main/resources/WEB-INF/views/`
- View names match file names (e.g., "login" → "login.jsp")
- Check `application.properties` prefix/suffix settings

### Data files not found

**Solution**:
```bash
# Create data directory structure
mkdir data
mkdir data/orders
mkdir uploads
mkdir uploads/products
```

### Image upload fails

**Check**:
- `uploads/products/` directory exists
- Sufficient disk space
- File size under 1GB limit
- JPEG/PNG format

### Session lost on refresh

**Cause**: JSESSIONID cookie may not persist

**Solution**:
- Enable cookies in browser
- Check session timeout in `application.properties`

### Concurrent data corruption

**Current Workaround**: Single-user deployment only

**Long-term Solution**: Migrate to database with proper locking

---

## 📝 Development Notes

### Building from Source

```bash
# Full build with tests
mvn clean test package

# Skip tests for faster build
mvn clean package -DskipTests

# View dependency tree
mvn dependency:tree
```

### Common Maven Commands

```bash
mvn compile              # Compile only
mvn test                 # Run tests
mvn package             # Create JAR
mvn clean              # Remove target directory
mvn install            # Install to local repository
```

### IDE Configuration

**VS Code**:
- Install "Extension Pack for Java"
- Open project root
- Maven will auto-detect pom.xml

**IntelliJ IDEA**:
- Open → Select project root
- File → Project Structure → Verify Java 17 SDK

---

## 📞 Support & Questions

For issues or questions:
1. Check the Troubleshooting section above
2. Review the known issues list
3. Check application logs for error messages

---

## 📄 License

This project is provided as-is for educational purposes.

---

## 👤 Author Notes

**Current State**: This is a functional learning project demonstrating Spring Boot fundamentals.

**Production Readiness**: ⚠️ **NOT production-ready** due to:
- File-based persistence (no concurrent access support)
- Security vulnerabilities (plaintext passwords, no CSRF protection)
- Limited error handling
- No testing framework

**Recommended Before Production**:
1. Implement comprehensive security
2. Migrate to relational database
3. Add full test coverage
4. Implement logging and monitoring
5. Performance optimization and caching

---

**Last Updated**: January 27, 2026  
**Version**: 0.0.1-SNAPSHOT
