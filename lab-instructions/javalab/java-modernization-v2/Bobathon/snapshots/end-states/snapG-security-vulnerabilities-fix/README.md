# Simple Pharmacy Management System - Security Vulnerabilities FIXED

A pharmacy management system with a Java REST API backend running on Liberty. This snapshot contains the **fixed version** with security vulnerabilities remediated.

## 🎯 Overview

This is the **solution snapshot** showing how the security vulnerabilities from snapE have been properly fixed. This demonstrates best practices for:
- Secure CORS configuration
- Proper credential management
- Security-first development approach

## 🔒 Security Fixes Implemented

### 1. CORS Configuration (CorsFilter.java)
**Vulnerability**: Wildcard `*` in Access-Control-Allow-Origin allowed any origin to access the API
**Fix**: 
- Restricted CORS to specific allowed origins
- Added origin validation
- Enabled credentials support for trusted origins
- Removed wildcard access

### 2. Hardcoded Credentials (server.xml)
**Vulnerability**: Plain-text passwords (admin123, user123) in configuration file
**Fix**:
- Implemented environment variable support for credentials
- Used XOR-encoded passwords instead of plain text
- Added documentation for production LDAP integration
- Provided secure default values for development

## 🚀 Quick Start

### Prerequisites

- Java 21 or higher
- Maven 3.6 or higher

### Build and Run

```bash
# Build and run
mvn clean liberty:run

# Stop the server (in another terminal)
mvn liberty:stop
```

**Access the application:**
- **REST API**: http://localhost:9081/simple-pharmacy.war/api/

### Environment Variables (Optional)

For production, set these environment variables:
```bash
export ADMIN_USER=your_admin_username
export ADMIN_PASSWORD=your_encoded_password
export APP_USER=your_app_username
export APP_PASSWORD=your_encoded_password
```

To encode passwords:
```bash
${wlp.install.dir}/bin/securityUtility encode --encoding=xor yourPassword
```

## 📁 Project Structure

```
simple-pharmacy/
├── src/main/java/com/pharmacy/
│   ├── api/                    # REST API endpoints (JAX-RS)
│   │   ├── DashboardResource.java
│   │   ├── MedicineResource.java
│   │   ├── OrderResource.java
│   │   ├── PrescriptionResource.java
│   │   ├── PharmacyApplication.java
│   │   └── CorsFilter.java    # ✅ FIXED: Secure CORS configuration
│   ├── model/                  # Domain models
│   └── repository/             # Data repositories
├── src/main/liberty/config/
│   └── server.xml              # ✅ FIXED: Secure credential management
├── src/main/webapp/
│   └── WEB-INF/web.xml
└── pom.xml                     # Maven build configuration
```

## 🌐 Application Features

### Dashboard
- View pending prescriptions and orders
- See total counts and statistics

### Prescriptions
- Create new prescriptions
- Validate prescriptions
- View prescription history

### Orders
- Create orders from validated prescriptions
- Process payments
- Track order status

### Medicines
- View medicine inventory
- Search medicines by name
- Check stock levels

## 🔌 REST API Endpoints

Base URL: `http://localhost:9081/simple-pharmacy.war/api`

### Dashboard
- `GET /dashboard` - Get dashboard statistics

### Medicines
- `GET /medicines` - Get all medicines
- `GET /medicines/{id}` - Get medicine by ID
- `GET /medicines/search?name={query}` - Search medicines

### Prescriptions
- `GET /prescriptions` - Get all prescriptions
- `GET /prescriptions/{id}` - Get prescription by ID
- `POST /prescriptions` - Create new prescription
- `PUT /prescriptions/{id}/validate` - Validate prescription

### Orders
- `GET /orders` - Get all orders
- `GET /orders/{id}` - Get order by ID
- `POST /orders/from-prescription` - Create order from prescription
- `PUT /orders/{id}/payment` - Process payment
- `PUT /orders/{id}/collect` - Mark order as collected

## 🛠️ Maven Commands

### Build
```bash
# Full build
mvn clean package

# Build without tests
mvn clean package -DskipTests
```

### Run
```bash
# Start Liberty server
mvn liberty:run

# Stop Liberty server (in another terminal)
mvn liberty:stop
```

## 📝 Configuration

### Backend Configuration
- **Server Config**: `src/main/liberty/config/server.xml`
- **HTTP Port**: 9081
- **HTTPS Port**: 9443

## 🔄 Technology Stack

- **Java**: 21
- **Jakarta EE**: 10
- **JAX-RS**: 3.1
- **JSON-B**: 3.0
- **Servlet**: 6.0
- **Open Liberty**: Latest

## 🔐 Security Best Practices Demonstrated

1. **CORS Configuration**
   - Never use wildcard `*` for Access-Control-Allow-Origin in production
   - Validate and whitelist specific origins
   - Use credentials only with trusted origins

2. **Credential Management**
   - Never commit plain-text passwords to version control
   - Use environment variables for sensitive configuration
   - Encode passwords at minimum (XOR encoding)
   - Consider external authentication (LDAP, OAuth) for production

3. **Additional Recommendations**
   - Implement HTTPS in production
   - Add rate limiting to prevent abuse
   - Implement proper authentication and authorization
   - Regular security audits and dependency updates
   - Use secrets management tools (HashiCorp Vault, AWS Secrets Manager)

## 🐛 Troubleshooting

### Port 9081 Already in Use
```bash
mvn liberty:stop
```

### Liberty Server Won't Start
Check logs in: `target/liberty/wlp/usr/servers/defaultServer/logs/`

### Environment Variables Not Working
Ensure variables are exported in the same shell session where you run Maven

## 📚 Additional Documentation

- [Quick Reference](QUICK-REFERENCE.md) - Command reference
- [Automation Scripts](README-AUTOMATION.md) - Script documentation

## 📖 Learning Resources

- [OWASP Top 10](https://owasp.org/www-project-top-ten/)
- [Open Liberty Security](https://openliberty.io/docs/latest/security.html)
- [Jakarta EE Security](https://jakarta.ee/specifications/security/)