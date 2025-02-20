# ☕ Coffee Machine Simulator - Java Coffee Machine Unit Testing

A **Java-based coffee machine simulation**, designed with **Object-Oriented Programming (OOP)** principles, **custom exceptions**, and **100% unit test coverage** using **JUnit & JaCoCo** (Java Code Coverage). This project includes a **CI/CD pipeline with GitHub Actions** to ensure high code quality.


## 🚀 Features
✅ Supports multiple **coffee types**: `Espresso`, `Latte`, `Cappuccino`, etc.  
✅ **Encapsulation & Abstraction** for reservoirs (**water, beans, milk**)  
✅ **Custom exceptions** for resource shortages  
✅ **100% unit test coverage** with **JaCoCo**  
✅ **Mockito-powered tests** for mocking dependencies  
✅ **Automated testing & coverage reporting** via **GitHub Actions**


## 📁 Project Structure
```
io.testsmith.coffeemachine
│── enums
│   ├── CoffeeType.java              # Menu of coffee types
│── exceptions
│   ├── InsufficientBeansException.java
│   ├── InsufficientWaterException.java
│   ├── InsufficientMilkException.java
│── models
│   ├── AbstractReservoir.java       # Base class for reservoirs
│   ├── WaterTank.java               # Water container
│   ├── BeanReservoir.java           # Bean container
│   ├── MilkReservoir.java           # Milk container
├── CoffeeMachine.java               # Main coffee machine logic
│── tests
│   ├── CoffeeMachineTest.java       # Unit tests with Mockito
```

---

## ⚙️ Installation & Setup

### 1️⃣ Clone the Repository
```sh
git clone https://github.com/testsmith-io/java-coffee-machine-unit-testing.git
cd java-coffee-machine-unit-testing
```

### 2️⃣ Run Tests
```sh
mvn clean test
```

### 3️⃣ Generate Code Coverage Report
```sh
mvn jacoco:report
```
- **View Report:** Open `target/site/jacoco/index.html` in a browser.


## ☕ Coffee Menu
The machine can brew the following **coffee types**, with predefined **water, bean, and milk requirements**:

| Coffee Type       | Water (ml) | Beans (g) | Milk (ml) |
|------------------|------------|----------|-----------|
| **Espresso**     | 100        | 30       | 0         |
| **Double Espresso** | 150      | 40       | 0         |
| **Coffee**       | 200        | 20       | 0         |
| **Latte**        | 150        | 20       | 100       |
| **Cappuccino**   | 100        | 25       | 150       |
| **Macchiato**    | 100        | 15       | 50        |


## 🛠 CI/CD - Automated Testing

This project uses **GitHub Actions** to automate testing and coverage reporting.  
Every push & pull request triggers:
- **JUnit Tests**
- **JaCoCo Code Coverage Report**
- **Test Artifacts Upload**

### 📄 GitHub Actions Workflow

[.github/workflows/ci.yml](.github/workflows/ci.yml)

### ⭐ Enjoy your coffee! 🚀