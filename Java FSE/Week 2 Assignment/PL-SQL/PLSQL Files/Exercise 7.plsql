--Scenario 1: Group all customer-related procedures and functions into a package.


--Creating Package:

CREATE OR REPLACE PACKAGE CustomerManagement AS
    PROCEDURE AddNewCustomer (
        custID IN Customers.CustomerID%TYPE,
        custname IN Customers.Name%TYPE,
        dob IN Customers.DOB%TYPE,
        bal IN Customers.Balance%TYPE
    );
    
    PROCEDURE UpdateCustomerDetails (
        custID IN Customers.CustomerID%TYPE,
        custname IN Customers.Name%TYPE,
        dob IN Customers.DOB%TYPE,
        bal IN Customers.Balance%TYPE
    );
    
    FUNCTION GetCustomerBalance (
        custID IN Customers.CustomerID%TYPE
    ) RETURN NUMBER;
END CustomerManagement;
/


--Creating Package Body:


CREATE OR REPLACE PACKAGE BODY CustomerManagement AS

    PROCEDURE AddNewCustomer (
        custID IN Customers.CustomerID%TYPE,
        custname IN Customers.Name%TYPE,
        dob IN Customers.DOB%TYPE,
        bal IN Customers.Balance%TYPE
    ) IS
    BEGIN
        INSERT INTO Customers (CustomerID, Name, DOB, Balance, LastModified)
        VALUES (custID, custname, dob, bal, SYSDATE);
        COMMIT;
    EXCEPTION
        WHEN DUP_VAL_ON_INDEX THEN
            DBMS_OUTPUT.PUT_LINE('Error: Customer with ID ' || custID || ' already exists in database.');
    END AddNewCustomer;

    PROCEDURE UpdateCustomerDetails (
        custID IN Customers.CustomerID%TYPE,
        custname IN Customers.Name%TYPE,
        dob IN Customers.DOB%TYPE,
        bal IN Customers.Balance%TYPE
    ) IS
    BEGIN
        UPDATE Customers
        SET Name = custname,
            DOB = dob,
            Balance = bal,
            LastModified = SYSDATE
        WHERE CustomerID = custID;
        COMMIT;
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            DBMS_OUTPUT.PUT_LINE('Error: Customer with ID ' || custID || ' not found in database.');
    END UpdateCustomerDetails;

    FUNCTION GetCustomerBalance (
        custID IN Customers.CustomerID%TYPE
    ) RETURN NUMBER IS
        v_balance NUMBER;
    BEGIN
        SELECT Balance INTO v_balance
        FROM Customers
        WHERE CustomerID = custID;
        RETURN v_balance;
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            DBMS_OUTPUT.PUT_LINE('Error: Customer with ID ' || custID || ' not found in database.');
            RETURN NULL;
    END GetCustomerBalance;

END CustomerManagement;
/





--************************************************************************************************************************************************************************************
--Scenario 2: Create a package to manage employee data.

--Creating Package:

CREATE OR REPLACE PACKAGE EmployeeManagement AS
    PROCEDURE hiringEmployee (
        empID IN Employees.EmployeeID%TYPE,
        empName IN Employees.Name%TYPE,
        ampPosition IN Employees.Position%TYPE,
        empSal IN Employees.Salary%TYPE,
        empDept IN Employees.Department%TYPE,
        empHiringDate IN Employees.HireDate%TYPE
    );
    
    PROCEDURE UpdateEmployeeDetails (
        empID IN Employees.EmployeeID%TYPE,
        empName IN Employees.Name%TYPE,
        ampPosition IN Employees.Position%TYPE,
        empSal IN Employees.Salary%TYPE,
        empDept IN Employees.Department%TYPE,
        empHiringDate IN Employees.HireDate%TYPE
    );
    
    FUNCTION CalculateAnnualSal (
        empID IN Employees.EmployeeID%TYPE
    ) RETURN NUMBER;
END EmployeeManagement;
/


--Creating Package Body:

CREATE OR REPLACE PACKAGE BODY EmployeeManagement AS

    PROCEDURE hiringEmployee (
        empID IN Employees.EmployeeID%TYPE,
        empName IN Employees.Name%TYPE,
        ampPosition IN Employees.Position%TYPE,
        empSal IN Employees.Salary%TYPE,
        empDept IN Employees.Department%TYPE,
        empHiringDate IN Employees.HireDate%TYPE
    ) IS
    BEGIN
        INSERT INTO Employees (EmployeeID, Name, Position, Salary, Department, HireDate)
        VALUES (empID, empName, ampPosition, empSal, empDept, empHiringDate);
        COMMIT;
    EXCEPTION
        WHEN DUP_VAL_ON_INDEX THEN
            DBMS_OUTPUT.PUT_LINE('Error: Employee with ID ' || empID || ' already exists in database.');
    END hiringEmployee;

    PROCEDURE UpdateEmployeeDetails (
        empID IN Employees.EmployeeID%TYPE,
        empName IN Employees.Name%TYPE,
        ampPosition IN Employees.Position%TYPE,
        empSal IN Employees.Salary%TYPE,
        empDept IN Employees.Department%TYPE,
        empHiringDate IN Employees.HireDate%TYPE
    ) IS
    BEGIN
        UPDATE Employees
        SET Name = empName,
            Position = ampPosition,
            Salary = empSal,
            Department = empDept,
            HireDate = empHiringDate
        WHERE EmployeeID = empID;
        COMMIT;
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            DBMS_OUTPUT.PUT_LINE('Error: Employee with ID ' || empID || ' not found in database.');
    END UpdateEmployeeDetails;

    FUNCTION CalculateAnnualSal (
        empID IN Employees.EmployeeID%TYPE
    ) RETURN NUMBER IS
        v_salary Employees.Salary%TYPE;
    BEGIN
        SELECT Salary INTO v_salary
        FROM Employees
        WHERE EmployeeID = empID;
        RETURN v_salary * 12; 
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            DBMS_OUTPUT.PUT_LINE('Error: Employee with ID ' || empID || ' not found in database.');
            RETURN NULL;
    END CalculateAnnualSal;

END EmployeeManagement;
/





--************************************************************************************************************************************************************************************
--Scenario 3: Group all account-related operations into a package.

--Creating Package:

CREATE OR REPLACE PACKAGE AccountOperations AS
    PROCEDURE OpenNewAccount (
        accID IN Accounts.AccountID%TYPE,
        custID IN Accounts.CustomerID%TYPE,
        accType IN Accounts.AccountType%TYPE,
        bal IN Accounts.Balance%TYPE
    );
    
    PROCEDURE CloseAccount (
        accID IN Accounts.AccountID%TYPE
    );
    
    FUNCTION SeeTotalBalance (
        custID IN Accounts.CustomerID%TYPE
    ) RETURN NUMBER;
END AccountOperations;
/



--Creating Package Body:


CREATE OR REPLACE PACKAGE BODY AccountOperations AS

    PROCEDURE OpenNewAccount (
        accID IN Accounts.AccountID%TYPE,
        custID IN Accounts.CustomerID%TYPE,
        accType IN Accounts.AccountType%TYPE,
        bal IN Accounts.Balance%TYPE
    ) IS
    BEGIN
        INSERT INTO Accounts (AccountID, CustomerID, AccountType, Balance, LastModified)
        VALUES (accID, custID, accType, bal, SYSDATE);
        COMMIT;
    EXCEPTION
        WHEN DUP_VAL_ON_INDEX THEN
            DBMS_OUTPUT.PUT_LINE('Error: Account with ID ' || accID || ' already exists in database.');
    END OpenNewAccount;

    PROCEDURE CloseAccount (
        accID IN Accounts.AccountID%TYPE
    ) IS
    BEGIN
        DELETE FROM Accounts
        WHERE AccountID = accID;
        COMMIT;
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            DBMS_OUTPUT.PUT_LINE('Error: Account with ID ' || accID || ' not found in database.');
    END CloseAccount;

    FUNCTION SeeTotalBalance (
        custID IN Accounts.CustomerID%TYPE
    ) RETURN NUMBER IS
        v_totalBalance NUMBER := 0;
    BEGIN
        SELECT SUM(Balance) INTO v_totalBalance
        FROM Accounts
        WHERE CustomerID = custID;
        RETURN v_totalBalance;
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            DBMS_OUTPUT.PUT_LINE('Error: No accounts found with Customer ID ' || custID || ' in database.');
            RETURN 0;
    END SeeTotalBalance;

END AccountOperations;
/


--************************************************************************************************************************************************************************************
--This is for testing

select * from customers;
select * from accounts;
select * from Transactions; 
select * from Loans;
select * from Employees; 
select * from AuditLog;


BEGIN
    --adding a new customer
    CustomerManagement.AddNewCustomer(4, 'subha', TO_DATE('1988-09-05', 'YYYY-MM-DD'), 1200);
END;
/


BEGIN    
    --updating recently added customer balance & name
    CustomerManagement.UpdateCustomerDetails(4, 'Mr. Subha Saha', TO_DATE('1988-09-05', 'YYYY-MM-DD'), 1500);
END;
/



BEGIN
    -- fetch customer balance
    DBMS_OUTPUT.PUT_LINE('Customer Balance: ' || CustomerManagement.GetCustomerBalance(4));
END;
/

--************************************************************************************************************************************************************************************

