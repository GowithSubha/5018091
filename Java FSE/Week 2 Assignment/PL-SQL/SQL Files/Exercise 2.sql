--Scenario 1: Handle exceptions during fund transfers between accounts.

CREATE OR REPLACE PROCEDURE SafeTransferFunds (
    fromAccountID IN Accounts.AccountID%TYPE,
    toAccountID IN Accounts.AccountID%TYPE,
    amount IN NUMBER
) AS
    fromBalance Accounts.Balance%TYPE;
    toBalance Accounts.Balance%TYPE;
BEGIN
    SELECT Balance INTO fromBalance
    FROM Accounts
    WHERE AccountID = fromAccountID
    FOR UPDATE;

    IF fromBalance < amount THEN
        RAISE_APPLICATION_ERROR(-20001, 'Insufficient funds in the source account.');
    END IF;

    SELECT Balance INTO toBalance
    FROM Accounts
    WHERE AccountID = toAccountID
    FOR UPDATE;

    UPDATE Accounts
    SET Balance = fromBalance - amount
    WHERE AccountID = fromAccountID;

    UPDATE Accounts
    SET Balance = toBalance + amount
    WHERE AccountID = toAccountID;

    COMMIT;

    DBMS_OUTPUT.PUT_LINE('Funds transferred successfully.');

EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('Error occurd during fund transfer: ' || SQLERRM);
END SafeTransferFunds;
/


--************************************************************************************************************************************************************************************
--Scenario 2: Manage errors when updating employee salaries.


CREATE OR REPLACE PROCEDURE UpdateSalary (
    p_employeeID IN Employees.EmployeeID%TYPE,
    p_percentage IN NUMBER
) AS
    v_salary Employees.Salary%TYPE;
BEGIN
    SELECT Salary INTO v_salary
    FROM Employees
    WHERE EmployeeID = p_employeeID;

    UPDATE Employees
    SET Salary = v_salary * (1 + p_percentage / 100)
    WHERE EmployeeID = p_employeeID;

    COMMIT;

    DBMS_OUTPUT.PUT_LINE('Salary updated successfully for Employee ID ' || p_employeeID);

EXCEPTION
    WHEN NO_DATA_FOUND THEN
        DBMS_OUTPUT.PUT_LINE('Error: Employee ID ' || p_employeeID || ' does not exist.');
    WHEN TOO_MANY_ROWS THEN
        DBMS_OUTPUT.PUT_LINE('Error: More than one employee with ID ' || p_employeeID || ' found.');
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error during salary update: ' || SQLERRM);
END UpdateSalary;
/


--************************************************************************************************************************************************************************************
--Scenario 3: Ensure data integrity when adding a new customer.
CREATE OR REPLACE PROCEDURE AddNewCustomer (
    customerID IN Customers.CustomerID%TYPE,
    c_name IN Customers.Name%TYPE,
    c_dob IN Customers.DOB%TYPE,
    c_balance IN Customers.Balance%TYPE
) AS
BEGIN
    INSERT INTO Customers (CustomerID, Name, DOB, Balance, LastModified)
    VALUES (customerID, c_name, c_dob, c_balance, SYSDATE);

    COMMIT;

    DBMS_OUTPUT.PUT_LINE('New customer added successfully in the database.');

EXCEPTION
    WHEN DUP_VAL_ON_INDEX THEN
        DBMS_OUTPUT.PUT_LINE('Error: A customer with ID ' || customerID || 'is already exists in your database.');
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error during customer insertion: ' || SQLERRM);
END AddNewCustomer;
/

--************************************************************************************************************************************************************************************
--This is for testing

select * from customers;
select * from accounts;
select * from Transactions; 
select * from Loans;
select * from Employees; 




BEGIN
    SafeTransferFunds(1, 2, 500);
END;
/


BEGIN
    SafeTransferFunds(1, 2, 2000);
END;
/

BEGIN
    UpdateSalary(1, 10); 
END;
/


BEGIN
    UpdateSalary(3, 10); 
END;
/


BEGIN
    AddNewCustomer(3, 'Subha Saha', TO_DATE('1978-09-25', 'YYYY-MM-DD'), 2000);
END;
/


BEGIN
    AddNewCustomer(1, 'Subha Saha', TO_DATE('1980-01-01', 'YYYY-MM-DD'), 500);
END;
/

--************************************************************************************************************************************************************************************

