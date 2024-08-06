--Scenario 1: The bank needs to process monthly interest for all savings accounts.


CREATE OR REPLACE PROCEDURE ProcessMonthlyInterest AS
BEGIN
    UPDATE Accounts
    SET Balance = Balance * 1.01
    WHERE AccountType = 'Savings';

    COMMIT;

    DBMS_OUTPUT.PUT_LINE('Monthly interest processed for all savings accounts of our database.');
END ProcessMonthlyInterest;
/


--************************************************************************************************************************************************************************************
--Scenario 2: The bank wants to implement a bonus scheme for employees based on their performance.
CREATE OR REPLACE PROCEDURE UpdateEmployeeBonus (
    emp_department IN Employees.Department%TYPE,
    emp_bonusPercentage IN NUMBER
) AS
BEGIN
    UPDATE Employees
    SET Salary = Salary * (1 + emp_bonusPercentage / 100)
    WHERE Department = emp_department;

    COMMIT;

    DBMS_OUTPUT.PUT_LINE('Employee bonuses updated for the department of ' || emp_department);
END UpdateEmployeeBonus;
/


--************************************************************************************************************************************************************************************
--Scenario 3: Customers should be able to transfer funds between their accounts.

CREATE OR REPLACE PROCEDURE TransferFunds (
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
        RAISE_APPLICATION_ERROR(-20001, 'Insufficient funds available in the source account.');
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

    DBMS_OUTPUT.PUT_LINE('Funds transferred successfully from the account no ' || fromAccountID || ' to the account no ' || toAccountID);
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('Error happen during fund transfer: ' || SQLERRM);
END TransferFunds;
/


--************************************************************************************************************************************************************************************
--This is for testing

select * from customers;
select * from accounts;
select * from Transactions; 
select * from Loans;
select * from Employees; 




BEGIN
    ProcessMonthlyInterest;
END;
/


BEGIN
    UpdateEmployeeBonus('HR', 5); 
END;
/

BEGIN
    TransferFunds(1, 2, 100);
END;
/

BEGIN
    TransferFunds(1, 2, 2000); 
END;
/

--************************************************************************************************************************************************************************************

