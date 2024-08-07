--Scenario 1: Calculate the age of customers for eligibility checks.

CREATE OR REPLACE FUNCTION CalculateAge (
    dob IN DATE
) RETURN NUMBER AS
    age NUMBER;
BEGIN
    SELECT FLOOR(MONTHS_BETWEEN(SYSDATE, dob) / 12) INTO age FROM dual;
    RETURN age;
END CalculateAge;
/


--************************************************************************************************************************************************************************************
--Scenario 2: The bank needs to compute the monthly installment for a loan.

CREATE OR REPLACE FUNCTION CalculateMonthlyInstallment (
    la IN NUMBER,
    annualInterestRate IN NUMBER,
    durationYears IN NUMBER
) RETURN NUMBER AS
    monthlyInterestRate NUMBER;
    totalPayments NUMBER;
    monthlyInstallment NUMBER;
BEGIN
    monthlyInterestRate := annualInterestRate / 1200; 
    totalPayments := durationYears * 12;
    
    IF monthlyInterestRate > 0 THEN
        monthlyInstallment := (la * monthlyInterestRate * POWER(1 + monthlyInterestRate, totalPayments)) /
                                (POWER(1 + monthlyInterestRate, totalPayments) - 1);
    ELSE
        monthlyInstallment := la / totalPayments;
    END IF;

    RETURN monthlyInstallment;
END CalculateMonthlyInstallment;
/



--************************************************************************************************************************************************************************************
--Scenario 3: Check if a customer has sufficient balance before making a transaction.


CREATE OR REPLACE FUNCTION HasSufficientBalance (
    accID IN NUMBER,
    amt IN NUMBER
) RETURN BOOLEAN AS
    v_balance NUMBER;
BEGIN
    SELECT Balance INTO v_balance
    FROM Accounts
    WHERE AccountID = accID;
    
    RETURN v_balance >= amt;
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        RETURN FALSE;
END HasSufficientBalance;
/


--************************************************************************************************************************************************************************************
--This is for testing

select * from customers;
select * from accounts;
select * from Transactions; 
select * from Loans;
select * from Employees; 



DECLARE
    cust_age NUMBER;
BEGIN
    cust_age := CalculateAge(TO_DATE('1985-05-15', 'YYYY-MM-DD'));
    DBMS_OUTPUT.PUT_LINE('Customer age: ' || cust_age);
END;
/



DECLARE
    ins NUMBER;
BEGIN
    ins := CalculateMonthlyInstallment(5000, 5, 2); -- $5000 loan, 5% annual interest, 2 years
    DBMS_OUTPUT.PUT_LINE('Monthly installment: ' || ins);
END;
/


DECLARE
    bal BOOLEAN;
BEGIN
    bal := HasSufficientBalance(1, 5000); 
    DBMS_OUTPUT.PUT_LINE(' Customer has sufficient balance: ' || CASE WHEN bal THEN 'TRUE' ELSE 'FALSE' END);
END;
/


--************************************************************************************************************************************************************************************



