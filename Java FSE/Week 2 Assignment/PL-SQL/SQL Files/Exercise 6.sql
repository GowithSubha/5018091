--Scenario 1: Generate monthly statements for all customers.


DECLARE
    CURSOR c_tran IS
        SELECT t.TransactionID, t.AccountID, t.TransactionDate, t.Amount, t.TransactionType, a.CustomerID, c.Name
        FROM Transactions t
        JOIN Accounts a ON t.AccountID = a.AccountID
        JOIN Customers c ON a.CustomerID = c.CustomerID
        WHERE EXTRACT(MONTH FROM t.TransactionDate) = EXTRACT(MONTH FROM SYSDATE)
          AND EXTRACT(YEAR FROM t.TransactionDate) = EXTRACT(YEAR FROM SYSDATE);
    
    tID Transactions.TransactionID%TYPE;
    accID Transactions.AccountID%TYPE;
    tDate Transactions.TransactionDate%TYPE;
    amt Transactions.Amount%TYPE;
    tType Transactions.TransactionType%TYPE;
    custID Accounts.CustomerID%TYPE;
    custname Customers.Name%TYPE;
BEGIN
    FOR r_transaction IN c_tran LOOP
        tID := r_transaction.TransactionID;
        accID := r_transaction.AccountID;
        tDate := r_transaction.TransactionDate;
        amt := r_transaction.Amount;
        tType := r_transaction.TransactionType;
        custID := r_transaction.CustomerID;
        custname := r_transaction.Name;
        
        DBMS_OUTPUT.PUT_LINE('Statement for Customer: ' || custname);
        DBMS_OUTPUT.PUT_LINE('Account ID: ' || accID);
        DBMS_OUTPUT.PUT_LINE('Transaction ID: ' || tID);
        DBMS_OUTPUT.PUT_LINE('Date: ' || tDate);
        DBMS_OUTPUT.PUT_LINE('Amount: ' || amt);
        DBMS_OUTPUT.PUT_LINE('Type: ' || tType);
        DBMS_OUTPUT.PUT_LINE('-----------------------------');
    END LOOP;
END;
/





--************************************************************************************************************************************************************************************
--Scenario 2: Apply annual fee to all accounts.


DECLARE
    CURSOR c_acc IS
        SELECT AccountID, Balance
        FROM Accounts;
    
    accID Accounts.AccountID%TYPE;
    bal Accounts.Balance%TYPE;
    fee NUMBER := 42; 
BEGIN
    FOR ac IN c_acc LOOP
        accID := ac.AccountID;
        bal := ac.Balance;
        
        UPDATE Accounts
        SET Balance = bal - fee
        WHERE AccountID = accID;
        
        DBMS_OUTPUT.PUT_LINE('Annual fee applied to Account ID: ' || accID);
    END LOOP;
    
    COMMIT;
END;
/





--************************************************************************************************************************************************************************************
--Scenario 3: Update the interest rate for all loans based on a new policy.



DECLARE
    CURSOR c_loans IS
        SELECT LoanID, InterestRate
        FROM Loans;
    
    cust_loanID Loans.LoanID%TYPE;
    currentInterestRate Loans.InterestRate%TYPE;
    newInterestRate Loans.InterestRate%TYPE;
BEGIN
    newInterestRate := 10; 

    FOR r_loan IN c_loans LOOP
        cust_loanID := r_loan.LoanID;
        currentInterestRate := r_loan.InterestRate;
        
        UPDATE Loans
        SET InterestRate = newInterestRate
        WHERE LoanID = cust_loanID;
        
        DBMS_OUTPUT.PUT_LINE('Updated Interest Rate for Loan ID: ' || cust_loanID || ' to ' || newInterestRate || '%');
    END LOOP;
    
    COMMIT;
END;
/



--************************************************************************************************************************************************************************************
--This is for testing

select * from customers;
select * from accounts;
select * from Transactions; 
select * from Loans;
select * from Employees; 
select * from AuditLog;






--************************************************************************************************************************************************************************************

