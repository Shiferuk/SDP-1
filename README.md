# Assignment 1 - Builder Pattern: Computer Configuration System

**Course:** Software Design Patterns  
**Pattern:** Builder Pattern  
**Language:** Java (JDK 17+)  
**Author:** Abdulla Nurdaulet  
**Presenter:** Abdulla Nurdaulet  

---

## 📌 Project Overview

This repository contains a Java implementation of the **Builder Design Pattern** applied to a **Computer Configuration System**. The project demonstrates how to refactor complex object creation away from cluttered, telescoping constructors into a readable, fluent, and validated API.

### Key Features
* **Fluent Builder API**: Method chaining for expressive object construction (`enableWifi()`, `coolingType(...)`).
* **Robust Domain Validation**: Single-field checks (null CPU, positive RAM/Storage) and cross-field rules (Windows 11 requiring >=8GB RAM, RTX GPU requiring adequate cooling).
* **Director Support**: Pre-defined configurations (`createGaming()`, `createOffice()`) for quick preset initialization.
* **Automated Unit Testing**: Complete JUnit 5 test suite covering valid builds, boundary conditions, cross-field rules, and builder independence.

---

## 🛠 Project Structure

```
assignment-1-builder/
│
├── src/
│   └── builder/
│       ├── ComputerConfiguration.java         # Product & Inner Builder
│       ├── ComputerConfigurationDirector.java # Director for Presets
│       ├── CpuSpecs.java                      # Value Object for CPU
│       └── main.java                          # Client Entry Point
│
├── test/
│   └── builder/
│       └── ComputerConfigurationTest.java     # JUnit 5 Automated Tests
│
├── docs/
│   └── builder-uml.png                        # UML Class Diagram
│
├── README.md                                  # Project Overview & Instructions
└── report.md                                  # Full Technical Report
```

---

## 🚀 Getting Started

### Prerequisites
* **Java Development Kit (JDK):** 17 or higher
* **Build Tool / IDE:** IntelliJ IDEA, Eclipse, or command-line `javac` / `mvn`
* **Testing Framework:** JUnit 5 (JUnit Jupiter)

### How to Run

1. **Clone the Repository:**
   ```bash
   git clone https://https://github.com/Shiferuk/SDP-1.git
   cd assignment-1-builder
   ```

2. **Compile and Run Main:**
   ```bash
   javac -d out src/builder/*.java
   java -cp out builder.main
   ```

3. **Run Unit Tests:**
   Execute `ComputerConfigurationTest.java` in your IDE or via your test runner.

---

## 📋 Compliance Summary

* **Domain**: Computer Configuration
* **Properties**: 11 total properties (5 required, 6 optional).
* **Value Object**: `CpuSpecs` (model, cores, clockSpeedGhz).
* **Presets**: Office, Gaming, and Workstation configurations via Director / Builder.