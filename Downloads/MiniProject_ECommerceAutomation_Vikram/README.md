
# Automating E-Commerce Workflows – Selenium Mini Project

## Project Overview
This project demonstrates an end-to-end automation of an e-commerce workflow using **Selenium WebDriver**, **TestNG**, **Maven**, and **Apache POI**.  
A dummy e-commerce website (**DemoBlaze**) is used to reliably automate shopping functionality without authentication or anti-bot restrictions.

The automation validates the complete flow starting from product selection up to payment confirmation, along with screenshots captured at each major step.

---

## Project Objectives
- Automate an e-commerce workflow from product selection to checkout
- Validate cart handling and price details
- Validate payment redirection and order confirmation
- Execute the same test on multiple browsers (Chrome & Edge)
- Implement data-driven testing using Excel
- Capture screenshots after each step for evidence

---

## Tools & Technologies Used
- **Java**
- **Selenium WebDriver**
- **TestNG**
- **Maven**
- **Apache POI** (Excel integration)
- **Chrome & Edge browsers**

---

## Website Used
**DemoBlaze (Dummy E-Commerce Site)**  
https://www.demoblaze.com/

DemoBlaze is a public demo e-commerce site commonly used for Selenium practice. It allows cart operations and simulated payment without user authentication.

---

## Project Structure

```
EcommerceAutomation
│
├── Screenshot/                 # Screenshots captured after each step
├── src/
│   └── test/
│       ├── java/
│       │   └── com/ecommerce/miniproject/
│       │       └── MiniProject_ECommerceAutomation_2485875.java
│       └── resources/
│           └── TestData.xlsx
├── pom.xml
├── testng.xml
└── README.md
```

---

## Test Data (Excel – Data Driven)

**File:** `src/test/resources/TestData.xlsx`

| Column | Description |
|------|------------|
| A1 | Product Name |

**Sample Value:**
```
Samsung galaxy s6
```

The product name is read dynamically from Excel using Apache POI, making the test reusable and avoiding hardcoding.

---

## Automated Test Workflow

The script performs the following steps:

1. Launch browser (Chrome / Edge)
2. Open DemoBlaze website
3. Read product name from Excel
4. Open the selected product
5. Capture and validate product price
6. Add product to cart
7. Handle confirmation alert
8. Navigate to cart page
9. Click **Place Order**
10. Validate payment modal redirection
11. Enter dummy payment details
12. Complete purchase
13. Validate order confirmation message

---

## Payment Redirection Validation

Since DemoBlaze is a dummy e-commerce site, payment is simulated using the **Place Order → Purchase** flow.  
The automation validates:
- Display of the payment modal
- Entry of payment details
- Successful order confirmation message

This satisfies the payment redirection validation requirement for a demo environment.

---

## Screenshots

- Screenshots are captured **after every major step**
- Stored automatically in the `Screenshot/` folder
- File names include step name and timestamp

Example:
```
01_SiteOpened_XXXXXXXX.png
02_ProductOpened_XXXXXXXX.png
03_PriceCaptured_XXXXXXXX.png
...
```

---

## Browser Support

The test supports multi-browser execution using TestNG parameters:
- Google Chrome
- Microsoft Edge

Browser selection is controlled via `testng.xml`.

---

## How to Run the Project

### Prerequisites
- Java JDK 17 or higher
- Maven installed
- Chrome and/or Edge browser

### Steps
1. Import the project as a **Maven project** in IntelliJ or Eclipse
2. Ensure `TestData.xlsx` is present in `src/test/resources`
3. Run the test using:
   - `testng.xml` (recommended), or
   - Right-click the test class and select **Run**

---

## Expected Output

- Browser opens and executes the full e-commerce flow
- Screenshots captured at each step
- Console logs confirming validations
- TestNG summary similar to:

```
Total tests run: 2
Passes: 2
Failures: 0
Skips: 0
```

---

## Conclusion

This mini project successfully implements an automated e-commerce workflow with data-driven testing, multi-browser execution, cart handling, price validation, payment redirection, and screenshot evidence. The solution is stable, reusable, and aligned with the Automating E-Commerce Workflows project objectives.
