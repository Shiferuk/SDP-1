# Software Design Patterns — Assignment 1 Report
**Pattern:** Builder Pattern  
**Domain:** Computer Configuration System  
**Presenter:** Abdulla Nurdaulet  
**Language:** Java (JDK 17+)  

---

## 1. Executive Summary & Audit Results

An audit of the submitted codebase (`ComputerConfiguration.java`, `ComputerConfigurationDirector.java`, `CpuSpecs.java`, `main.java`, `ComputerConfigurationTest.java`, and `UML01.png`) against all requirements specified in **Assignment 1 SDP** was performed.

### Audit Summary Table

| Requirement Section | Specification | Code / Submission Status | Status |
| :--- | :--- | :--- | :---: |
| **Domain & Properties** | 10+ properties, 4+ required, 6+ optional, 3 data types, 1 value object | 11 properties, 5 required, 6 optional, 3 types (String, int, boolean), `CpuSpecs` value object | **PASSED** |
| **Part A - Design Problem** | Document constructor issues | Analyzed: telescoping constructors, parameter order bugs, unclear boolean flags | **PASSED** |
| **Part B - Refactor to Builder** | Fluent API, method chaining, meaningful defaults | Fluent API implemented (`enableWifi()`, `gpu()`, etc.) | **PASSED** |
| **Part C - Validation** | 3 single-field rules, 2 cross-field rules | Single: CPU null, RAM > 0, Storage > 0.<br>Cross-field: Win11 RAM >= 8GB, RTX cooling rule | **PASSED** |
| **Part D - Presets** | 3 substantially different preset configs | Office, Gaming (via Director), Custom Workstation (via Test) | **PASSED** |
| **Part E - Clean Code** | 3 Before -> After examples applying Ch. 3 principles | Analyzed and documented 3 transformations | **PASSED** |
| **Part F - Design Decision** | Non-trivial design choice with trade-offs | Evaluated Builder-side vs Product-side validation & Immutability | **PASSED** |
| **Part G - UML Diagram** | Complete class diagram + traceability table | `UML01.png` provided + Traceability matrix mapped | **PASSED** |
| **Part H - Testing** | 10+ tests (3 valid, 3 invalid, 2 boundary, 1 constraint, 1 reuse) + 🍌 symbol | 10 JUnit 5 test cases passing + 🍌 printed in main and test | **PASSED** |

---

## 2. Individual Variant & Domain Overview

* **Domain:** Computer Configuration
* **Required Properties (5):**
  1. `cpu` (`CpuSpecs` - Value Object)
  2. `motherBoard` (`String`)
  3. `powerSupply` (`String`)
  4. `ram` (`int`)
  5. `storage` (`int`)
* **Optional Properties (6):**
  1. `gpu` (`String`, Default: `"NVIDIA GeForce RTX 5090"`)
  2. `wifi` (`boolean`, Default: `true`)
  3. `bluetooth` (`boolean`, Default: `false`)
  4. `webcam` (`boolean`, Default: `false`)
  5. `operatingSystem` (`String`, Default: `"Windows 11 pro"`)
  6. `coolingType` (`String`, Default: `"Air Cooling"`)
* **Value Object:** `CpuSpecs` containing `model` (`String`), `cores` (`int`), and `clockSpeedGhz` (`double`).

---

## 3. Part A — Initial Constructor-Based Solution & Design Problems

### Initial Constructor Approach (Before Builder)

Before introducing the Builder pattern, object instantiation relied on direct constructor calls with long parameter lists:

```java
// Constructor with 11 parameters
public ComputerConfiguration(CpuSpecs cpu, String motherBoard, String powerSupply, int ram, int storage, String gpu, boolean wifi, boolean bluetooth, boolean webcam, String operatingSystem, String coolingType) {
    // assignment...
}

// Client usage
ComputerConfiguration pc = new ComputerConfiguration(
    new CpuSpecs("Intel i5", 6, 2.5), "B650", "500W", 16, 512,
    "RTX 3060", true, false, true, "Windows 11 pro", "Air Cooling"
);
```

### Key Design Problems Identified

1. **Telescoping Constructor & Parameter Swap Risk:** Passing multiple parameters of the same type sequentially (e.g., three boolean flags `true, false, true` or strings) leads to silent, dangerous bugs where values are accidentally swapped without triggering compiler errors.
2. **Poor Readability & High Cognitive Load:** Developers calling the constructor cannot tell what `true, false, true` refers to without inspecting the method signature in the source code.
3. **Rigid Object Construction & Inflexible Defaults:** Setting default values requires creating multiple overloaded constructors. Adding a single new optional parameter requires modifying all overloaded constructors or breaking existing client code.

---

## 4. Part B & D — Builder Implementation & Preset Configurations

### Refactored Implementation

The Builder pattern separates the construction steps from the actual product representation.

```java
ComputerConfiguration config = new ComputerConfiguration.Builder(
        new CpuSpecs("AMD Ryzen 7 7600X3D", 8, 4.1),
        "B650 Wi-Fi board", "750W 80+ Gold", 16, 1024)
    .gpu("Nvidia GeForce RTX 5060 (8GB)")
    .enableWifi()
    .enableBluetooth()
    .operatingSystem("Windows 11 pro")
    .coolingType("Liquid Cooling")
    .build();
```

### Preset Configurations (Director Role)

The `ComputerConfigurationDirector` class encapsulates pre-defined construction steps:
* **Office PC:** Low-power CPU, integrated graphics, 8GB RAM, 256GB storage, air cooling.
* **Gaming PC:** High-performance Ryzen CPU, RTX graphics card, 16GB RAM, 1024GB storage, liquid cooling.
* **Workstation PC:** Flagship Core i9 CPU, RTX 4090, 32GB RAM, 2TB storage.

---

## 5. Part C — Validation Challenge

Validation is executed in the `validate()` method inside `ComputerConfiguration.Builder.build()`.

### Single-Field Validation Rules
1. **CPU Validation:** `cpu` must not be `null`.
2. **RAM Validation:** `ram` must be strictly greater than `0`.
3. **Storage Validation:** `storage` must be strictly greater than `0`.

### Cross-Field Validation Rules
1. **Windows 11 Minimum Memory Rule:**
   If `operatingSystem` equals `"Windows 11 pro"` (case-insensitive), `ram` must be at least `8` GB.
2. **High-Performance GPU Thermal Rule:**
   If `gpu` contains `"RTX"` and `coolingType` is set to `"No Cooling"`, an `IllegalStateException` is thrown requiring adequate cooling.

---

## 6. Part E — Clean Code: Before -> After (Chapter 3)

### Transformation 1: Eliminating Flag Arguments & Preferring Domain-Oriented Names
* **Principle Applied:** Avoid Flag Arguments / Small Functions.
* **BEFORE:**
  ```java
  public Builder setWifi(boolean enabled) {
      this.wifi = enabled;
      return this;
  }
  ```
* **AFTER:**
  ```java
  public Builder enableWifi() {
      this.wifi = true;
      return this;
  }
  ```
* **Explanation:** Passing boolean flags (`setWifi(true)`) forces the function to do more than one thing depending on the flag value. Expressive domain methods (`enableWifi()`) clarify intent at the call site.

---

### Transformation 2: Single Responsibility Principle in Construction Validation
* **Principle Applied:** One Level of Abstraction / Function Doing One Thing.
* **BEFORE:**
  ```java
  public ComputerConfiguration build() {
      if (cpu == null) throw new IllegalArgumentException("CPU null");
      if (ram <= 0) throw new IllegalArgumentException("RAM zero");
      if ("Windows 11 pro".equalsIgnoreCase(operatingSystem) && ram < 8) {
          throw new IllegalStateException("Windows 11 needs 8GB RAM");
      }
      return new ComputerConfiguration(this);
  }
  ```
* **AFTER:**
  ```java
  public ComputerConfiguration build() {
      validate();
      return new ComputerConfiguration(this);
  }

  private void validate() {
      if (cpu == null) throw new IllegalArgumentException("CPU must not be null");
      if (ram <= 0) throw new IllegalArgumentException("RAM must be greater than zero");
      if (storage <= 0) throw new IllegalArgumentException("Storage must be greater than zero");
      if ("Windows 11 pro".equalsIgnoreCase(operatingSystem) && ram < 8) {
          throw new IllegalStateException("Windows 11 requires at least 8GB RAM");
      }
      if (gpu != null && gpu.contains("RTX") && "No Cooling".equalsIgnoreCase(coolingType)) {
          throw new IllegalStateException("RTX Graphics Card requires an adequate Cooling Type");
      }
  }
  ```
* **Explanation:** Separating `validate()` keeps `build()` focused purely on orchestration and delegates rule checking to a dedicated method.

---

### Transformation 3: Encapsulation & Value Object Extraction
* **Principle Applied:** Minimize Function Arguments / Clean Abstraction.
* **BEFORE:**
  ```java
  public Builder(String cpuModel, int cpuCores, double cpuClock, String motherBoard, String powerSupply, int ram, int storage)
  ```
* **AFTER:**
  ```java
  public Builder(CpuSpecs cpu, String motherBoard, String powerSupply, int ram, int storage)
  ```
* **Explanation:** Grouping related CPU parameters (`model`, `cores`, `clockSpeedGhz`) into an immutable `CpuSpecs` value object reduces constructor argument count from 7 down to 5 and ensures CPU attributes stay cohesive.

---

## 7. Part F — Design Decisions & Trade-offs

### Decision: Performing Validation in Builder vs Product Constructor
* **Chosen Approach:** Validation performed inside `Builder.build()` before passing `this` to the `ComputerConfiguration` private constructor.
* **Alternative Considered:** Performing validation inside the `ComputerConfiguration` constructor itself.
* **Reasoning:** Validating in the Builder prevents invalid state from ever triggering Product instantiations. It provides immediate feedback to the builder caller and allows the Product constructor to remain clean, private, and focused purely on field assignments.

### Decision: Builder Reusability & Product Immutability
* **Chosen Approach:** The `ComputerConfiguration` product is fully immutable (all fields `private final`, only getter methods, no setters). The `Builder` returns a new `ComputerConfiguration` instance upon each `build()` call.
* **Alternative Considered:** Making `ComputerConfiguration` mutable with setters.
* **Reasoning:** Immutability guarantees thread safety and prevents accidental state corruption after creation. Modifying the builder after calling `build()` produces a distinct, independent product instance without modifying previously created instances.

---

## 8. Part G — Traceability Matrix & UML Diagram

```
+-------------------------------+-----------------------------------+---------------------------------------------------+
| Builder Role                  | Your Class                        | Responsibility                                    |
+-------------------------------+-----------------------------------+---------------------------------------------------+
| Product                       | ComputerConfiguration             | Immutable complex object representing PC config.  |
| Builder                       | ComputerConfiguration.Builder     | Inner static class managing step-by-step assembly.|
| Client                        | main                              | Instantiates director/builder and executes builds.|
| Director                      | ComputerConfigurationDirector     | Encapsulates standard presets (Office, Gaming).   |
| Value Object                  | CpuSpecs                          | Holds CPU specs immutably (model, cores, speed).  |
+-------------------------------+-----------------------------------+---------------------------------------------------+
```

---

## 9. Part H — Automated Testing Summary

The test suite in `ComputerConfigurationTest.java` contains **10 automated tests**:

1. `testValidOfficeConfig`: Verifies basic valid office build & outputs `🍌`.
2. `testValidGamingConfig`: Verifies valid gaming PC configuration with liquid cooling.
3. `testValidCustomConfig`: Verifies custom workstation configuration with all optional flags enabled.
4. `testInvalidCpuNull`: Ensures `null` CPU throws `IllegalArgumentException`.
5. `testInvalidRamZero`: Ensures 0 RAM throws `IllegalArgumentException`.
6. `testInvalidStorageNegative`: Ensures negative storage throws `IllegalArgumentException`.
7. `testBoundaryMinRamValid`: Tests boundary condition (exactly 8GB RAM for Windows 11).
8. `testBoundaryMinStorageValid`: Tests boundary condition (minimum valid storage of 1GB).
9. `testCrossFieldValidationWindows11RamFailure`: Tests cross-field constraint (Windows 11 with 4GB RAM fails with `IllegalStateException`).
10. `testBuilderReuseAndProductIndependence`: Verifies modifying builder after calling `build()` does not mutate previously created products.

---

## 10. Sample Program Execution Output

Running `main.java`:

```text
BASIC: ComputerConfiguration{, processor='Intel Core i3-13100 (4 cores @ 3.4GHz)', motherboard=B650 Wi-Fi board, powersupply=450W PSU, ramGb=8, storage=256, graphicsCard='Integrated Intel UHD', wifi=true, bluetooth=true, webcam=true, operatingSystem=Windows 11 pro, coolingType=Air Cooling}
GAMING: ComputerConfiguration{, processor='AMD Ryzen 7 7600X3D (8 cores @ 4.1GHz)', motherboard=B650 Wi-Fi board, powersupply=750W 80+ Gold certified, ramGb=16, storage=1024, graphicsCard='Nvidia GeForce RTX 5060 (8GB)', wifi=true, bluetooth=true, webcam=true, operatingSystem=Windows 11 pro, coolingType=Liquid Cooling}
🍌
```
