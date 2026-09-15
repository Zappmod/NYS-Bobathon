# Bobathon — Java Modernization Labs

> 🚀 **New to This Repository?** Start with the [Getting Started guide](#getting-started) — complete setup in 10 minutes!

This workshop contains **6 hands-on labs** for learning AI-assisted application modernization using IBM Bob. You will transform a legacy pharmacy management system step-by-step through a complete modernization journey.

---

## What is IBM Bob?

IBM Bob is an AI-powered coding assistant that integrates seamlessly with VS Code. Bob helps developers:

- Write code faster with intelligent suggestions
- Identify and fix security vulnerabilities
- Improve code quality and maintainability
- Implement security best practices
- Refactor and optimize existing codebases

---

## The Modernization Journey

```
┌─────────────────────────┐
│   Starting Point        │
│ Traditional WebSphere   │
│   Java 8 + Struts       │
└───────────┬─────────────┘
            │
            ▼
┌─────────────────────────┐
│        Lab 1            │
│   Migrate to Liberty    │
│   ─────────────────     │
│   Result: Liberty       │
│   Java 8 + Struts       │
└───────────┬─────────────┘
            │
            ▼
┌─────────────────────────┐
│        Lab 2            │
│   Upgrade to Java 21    │
│   ─────────────────     │
│   Result: Liberty       │
│   Java 21 + Struts      │
└───────────┬─────────────┘
            │
            ▼
┌─────────────────────────┐
│        Lab 3            │
│   Modernize Frontend    │
│   ─────────────────     │
│   Result: Liberty       │
│   Java 21 + Angular     │
└───────────┬─────────────┘
            │
            ├──────────────────────────────────┐
            │                                  │
            ▼                                  ▼
┌─────────────────────────┐      ┌─────────────────────────┐
│        Lab 4            │      │      Lab Alt-4           │
│   Unit Test Generation  │      │  Test Driven Dev (TDD)   │
│   ─────────────────     │      │   ─────────────────      │
│   Result: Tested App    │      │   Result: Generated      │
│   with Coverage         │      │   PrescriptionResource   │
└───────────┬─────────────┘      └─────────────────────────┘
            │                         (Standalone Lab)
            ▼
┌─────────────────────────┐
│        Lab 5            │
│   Security Hardening    │
│   ─────────────────     │
│   Result: Secure App    │
│   with Remediation      │
└─────────────────────────┘
```

**Progressive path:**

| Step | Result |
|------|--------|
| Starting Point | Traditional WebSphere, Java 8, Struts |
| Lab 1 | Liberty Runtime, Java 8, Struts ✅ |
| Lab 2 | Liberty Runtime, Java 21, Struts ✅ |
| Lab 3 | Liberty Runtime, Java 21, Angular ✅ |
| Lab 4 | Fully tested application ✅ |
| Lab Alt-4 | TDD-generated PrescriptionResource ✅ (Standalone) |
| Lab 5 | Security-hardened application ✅ |

---

## Lab Overview

| Lab | Focus Area | Starting Snapshot | Tech Stack | Duration |
|-----|-----------|------------------|------------|----------|
| Lab 1 | Application Server Migration | `snapA-java-liberty-replatforming` | TWas → Liberty, Java 8, Struts | 45–60 min |
| Lab 2 | Java Version Upgrade | `snapB-java-upgrade` | Liberty, Java 8 → Java 21, Struts | 45–60 min |
| Lab 3 | Frontend Modernization | `snapC-ui-mod` | Liberty, Java 21, Struts → Angular | 60–90 min |
| Lab 4 | Unit Test Generation | `snapD-unit-test-gen` | Liberty, Java 21, Angular | 60–75 min |
| Lab Alt-4 | Test Driven Development (TDD) | `snapTDD` | OpenAPI → Java 21 REST API | 45–60 min |
| Lab 5 | Security Vulnerability Remediation | `snapE-security-vulnerabilities` | Liberty, Java 21, Angular | 60–75 min |

> **Figma Integration Labs:** Separated to a different repo — see [figma-bobathon](https://github.ibm.com/ce-pub-northeast/figma-bobathon).

---

## Lab 1: Java Liberty Replatforming

**Migrate from Traditional WebSphere to Liberty Runtime**

**Objective:** Use IBM Bob's Java Modernization mode to migrate a legacy application from Traditional WebSphere Application Server (TWas) to the modern, lightweight Liberty runtime.

**What You'll Learn:**
- Analyzing legacy WebSphere applications
- Using IBM Bob's Java Modernization workflow
- Executing automated Liberty replatforming transformations
- Validating migrated applications

**Starting Point:** `Bobathon/labs/lab1-java-liberty-replatforming/snapA-java-liberty-replatforming/`

| | |
|---|---|
| **Before** | Traditional WebSphere 9, Java 8, Struts |
| **After** | Liberty Runtime, Java 8, Struts |

---

## Lab 2: Java Upgrade (Java 8 to Java 21)

**Upgrade Your Application to Modern Java**

**Objective:** Use IBM Bob to upgrade a Java 8 application to Java 21, taking advantage of modern language features, performance improvements, and long-term support.

**What You'll Learn:**
- Analyzing Java 8 codebases for upgrade readiness
- Using Bob's Java Modernization workflow for version upgrades
- Handling namespace changes (Java EE → Jakarta EE)
- Updating dependencies and configurations for Java 21
- Validating Java 21 compatibility

**Starting Point:** `Bobathon/labs/lab2-java-upgrade/snapB-java-upgrade/`

| | |
|---|---|
| **Before** | Liberty Runtime, Java 8, Struts |
| **After** | Liberty Runtime, Java 21, Struts |

---

## Lab 3: UI Modernization (Struts to Angular)

**Transform Your Frontend with Modern Frameworks**

**Objective:** Migrate the application's frontend from legacy Struts to modern Angular, creating a responsive single-page application (SPA) with improved user experience.

**What You'll Learn:**
- Analyzing Struts applications for frontend migration
- Using Bob's Advanced mode for complex framework migrations
- Creating Angular standalone components
- Implementing RESTful API integration
- Building modern, responsive user interfaces

**Starting Point:** `Bobathon/labs/lab3-ui-modernization/snapC-ui-mod/`

| | |
|---|---|
| **Before** | Liberty Runtime, Java 21, Struts (JSP views) |
| **After** | Liberty Runtime, Java 21, Angular 19 (REST API + SPA) |

---

## Lab 4: Unit Test Generation

**Generate Comprehensive Unit Tests with IBM Bob**

**Objective:** Use IBM Bob to automatically generate comprehensive unit tests for your modernized application, improving code quality and maintainability.

**What You'll Learn:**
- Analyzing code coverage gaps
- Using Bob to generate unit tests for Java backend
- Creating repository and API resource tests
- Implementing test best practices (JUnit, Mockito)
- Achieving high code coverage (>80%)
- Validating test quality and effectiveness

**Starting Point:** `Bobathon/snapshots/start-states/snapD-unit-test-gen/`

| | |
|---|---|
| **Before** | Liberty Runtime, Java 21, Angular (no tests) |
| **After** | Liberty Runtime, Java 21, Angular + comprehensive unit tests |

---

## Lab Alt-4: Test Driven Development (TDD)

**API-First Development Using TDD Methodology**

**Objective:** Implement API-first development using Test Driven Development methodology, generating implementation from tests derived from an OpenAPI specification.

**What You'll Learn:**
- Generating unit tests from OpenAPI specifications
- Following the Red-Green-Refactor TDD cycle
- Implementing PrescriptionResource driven by tests
- Comparing TDD vs traditional development approaches
- Understanding benefits of test-first development

**Starting Point:** `Bobathon/labs/alt-lab4-test-driven-development/snapTDD/`

| | |
|---|---|
| **Input** | OpenAPI 3.0 specification for prescription management |
| **Output** | Java 21 JAX-RS REST API with JUnit 5 tests |

---

## Lab 5: Security Vulnerability Remediation

**Identify and Fix Security Vulnerabilities**

**Objective:** Learn how to use IBM Bob to identify and remediate security vulnerabilities in your modernized application.

**What You'll Learn:**
- Scanning applications for security vulnerabilities
- Understanding common security issues (SQL injection, XSS, etc.)
- Using Bob to implement security best practices
- Remediating identified vulnerabilities
- Implementing input validation and sanitization
- Validating security improvements

**Starting Point:** `Bobathon/snapshots/start-states/snapE-security-vulnerabilities/`

| | |
|---|---|
| **Before** | Liberty Runtime, Java 21, Angular (with security vulnerabilities) |
| **After** | Liberty Runtime, Java 21, Angular (security-hardened) |

---

## Getting Started

### Prerequisites

Before starting any lab, ensure you have:

**IBM Bob IDE Extension (v1.0.0 or later)** — installed in VS Code and active

**SDKMAN!** (for Java and Maven management):
```bash
curl -s "https://get.sdkman.io" | bash
source "$HOME/.sdkman/bin/sdkman-init.sh"
```

**Maven** (via SDKMAN!):
```bash
sdk install maven
```

**Node.js and npm** (for Lab 3 only) — Node.js v18+ and npm v9+. Download from [nodejs.org](https://nodejs.org/).

**Angular CLI** (for Lab 3 only):
```bash
npm install -g @angular/cli
```

### Understanding Snapshots

Each lab has a **snapshot** — a self-contained starting point with all necessary code and configuration.

**Start State Snapshots:**

| Snapshot | Description |
|----------|-------------|
| `snapA-java-liberty-replatforming` | Traditional WebSphere with Java 8 and Struts (Lab 1) |
| `snapB-java-upgrade` | Liberty with Java 8 and Struts (Lab 2) |
| `snapC-ui-mod` | Liberty with Java 21 and Struts (Lab 3) |
| `snapD-unit-test-gen` | Liberty with Java 21 and Angular (Lab 4) |
| `snapTDD` | OpenAPI specification for prescription management (Lab Alt-4) |
| `snapE-security-vulnerabilities` | Liberty with Java 21 and Angular with security issues (Lab 5) |

**End State Snapshots (Reference Implementations):**

| Snapshot | Description |
|----------|-------------|
| `snapF-unit-tests` | Completed Lab 4 with comprehensive unit tests |
| `snapG-security-vulnerabilities-fix` | Completed Lab 5 with security fixes |
| `snapTDD-generated-api` | Completed Lab Alt-4 with TDD-generated implementation |

### Starting a Lab

1. **Open the lab snapshot folder** in your IDE:

   | Lab | Path |
   |-----|------|
   | Lab 1 | `labs/lab1-java-liberty-replatforming/snapA-java-liberty-replatforming/` |
   | Lab 2 | `labs/lab2-java-upgrade/snapB-java-upgrade/` |
   | Lab 3 | `labs/lab3-ui-modernization/snapC-ui-mod/` |
   | Lab 4 | `labs/lab4-unit-test-generation/snapD-unit-test-gen/` |
   | Lab Alt-4 | `labs/alt-lab4-test-driven-development/snapTDD/` |
   | Lab 5 | `labs/lab5-security-vulnerability-remediation/snapE-security-vulnerabilities/` |

2. **Navigate to the snapshot directory** in your terminal:
   ```bash
   cd Bobathon/labs/lab[X]-[lab-name]/snap[X]-[snapshot-name]/
   ```

3. **Open the lab guide** — navigate to `labs/lab[X]-[lab-name]/LAB[X]-GUIDE.md` and follow the step-by-step instructions.

4. **Activate IBM Bob** — open Bob's chat interface, switch to the appropriate mode (Java Modernization, Code, or Advanced), and follow the lab guide.

### Working Through Labs

**Option 1 — Sequential (recommended for full experience):**
Start with Lab 1 and work through Labs 2–3 for the core modernization journey, then complete Labs 4 and 5 in any order. Lab Alt-4 (TDD) is standalone and can be done anytime.

**Option 2 — Independent:**
Choose any lab based on your learning goals. Each snapshot provides everything needed for that specific lab.

---

## Templates & Resources

**IBM Bob Model Import Template** (`Bobathon/IBM_BOB_Model_Import_Template.pdf`): Reference guide for importing AI models into IBM Bob including configuration instructions, parameter setup, and best practices.

**Java Modernization Export Configuration** (`Bobathon/java-modernization-export.yaml`): YAML configuration template for customizing Java modernization exports — output paths, analysis depth, and metric collection options.

---

## Troubleshooting

| Issue | Resolution |
|-------|-----------|
| Bob Mode Not Available | Ensure IBM Bob is v1.0.0+. Try typing `/mode` in Bob's chat to see available modes. |
| Maven Not Detected | Restart your IDE after installing Maven via SDKMAN! Verify: `mvn --version` |
| Port Already in Use | Check if another app is using the port (typically 9081). Stop any running servers: `./stop-liberty.sh` |
| Build Failures | Ensure all prerequisites are installed, check Java version matches lab requirements, and review the lab guide's troubleshooting section. |

---

## Learning Outcomes

By completing these labs, you will:

- ✅ Understand IBM Bob's AI-powered modernization capabilities
- ✅ Master the Java Modernization workflow
- ✅ Learn to migrate between application servers
- ✅ Upgrade Java applications to modern versions
- ✅ Transform legacy frontends to modern frameworks
- ✅ Validate and test modernized applications
- ✅ Apply best practices for Java modernization projects

---

## Repository Structure

```
bobathon/
├── README.md
└── Bobathon/
    ├── labs/
    │   ├── lab1-java-liberty-replatforming/   LAB1-GUIDE.md
    │   ├── lab2-java-upgrade/                 LAB2-GUIDE.md
    │   ├── lab3-ui-modernization/             LAB3-GUIDE.md
    │   ├── lab4-unit-test-generation/         LAB4-GUIDE.md
    │   ├── alt-lab4-test-driven-development/  LAB-TDD-GUIDE.md + snapTDD/
    │   └── lab5-security-vulnerability-remediation/  LAB5-GUIDE.md
    └── snapshots/
        ├── start-states/
        │   ├── snapA-java-liberty-replatforming/
        │   ├── snapB-java-upgrade/
        │   ├── snapC-ui-mod/
        │   ├── snapD-unit-test-gen/
        │   ├── snapTDD/
        │   └── snapE-security-vulnerabilities/
        └── end-states/
            ├── snapF-unit-tests/
            ├── snapG-security-vulnerabilities-fix/
            └── snapTDD-generated-api/
```

---

> **Happy Modernizing! 🚀** — Workshop material provided for educational purposes by IBM Bob.
