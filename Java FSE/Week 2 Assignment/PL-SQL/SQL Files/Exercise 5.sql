--Scenario 1: Automatically update the last modified date when a customer's record is updated.


CREATE OR REPLACE TRIGGER UpdateCustomerLastModified
BEFORE UPDATE ON Customers
FOR EACH ROW
BEGIN
    :NEW.LastModified := SYSDATE;
END UpdateCustomerLastModified;
/



--************************************************************************************************************************************************************************************
--Scenario 2: Maintain an audit log for all transactions.


--First we need to creae AuditLog tavle 
BEGIN
   EXECUTE IMMEDIATE '
      CREATE TABLE AuditLog (
         AuditID NUMBER PRIMARY KEY,
         TransactionID NUMBER,
         ActionType VARCHAR2(50),
         ActionDate DATE,
         OldBalance NUMBER,
         NewBalance NUMBER
      )';
EXCEPTION
   WHEN OTHERS THEN
      DBMS_OUTPUT.PUT_LINE('An error occurred: ' || SQLERRM);
END;
/

--Now we need to create sequence

BEGIN
   EXECUTE IMMEDIATE '
      CREATE SEQUENCE AuditLog_seq
      START WITH 1
      INCREMENT BY 1';
EXCEPTION
   WHEN OTHERS THEN
      DBMS_OUTPUT.PUT_LINE('An error occurred: ' || SQLERRM);
END;
/


--Now perform creating the trigger

CREATE OR REPLACE TRIGGER LogTransaction
AFTER INSERT ON Transactions
FOR EACH ROW
DECLARE
    oldBal NUMBER;
    newBal NUMBER;
BEGIN
    SELECT Balance INTO oldBal
    FROM Accounts
    WHERE AccountID = :NEW.AccountID;
    
    IF :NEW.TransactionType = 'Deposit' THEN
        newBal := oldBal + :NEW.Amount;
    ELSE
        newBal := oldBal - :NEW.Amount;
    END IF;

    INSERT INTO AuditLog (AuditID, TransactionID, ActionType, ActionDate, OldBalance, NewBalance)
    VALUES (
        AuditLog_seq.NEXTVAL,  -- Assume AuditLog_seq is a sequence for generating AuditID
        :NEW.TransactionID,
        :NEW.TransactionType,
        SYSDATE,
        oldBal,
        newBal
    );
END LogTransaction;
/




--************************************************************************************************************************************************************************************
--Scenario 3: Enforce business rules on deposits and withdrawals.

CREATE OR REPLACE TRIGGER CheckTransactionRules
BEFORE INSERT ON Transactions
FOR EACH ROW
DECLARE
    bal NUMBER;
BEGIN
    SELECT Balance INTO bal
    FROM Accounts
    WHERE AccountID = :NEW.AccountID;

    IF :NEW.TransactionType = 'Withdrawal' AND :NEW.Amount > bal THEN
        RAISE_APPLICATION_ERROR(-20002, 'Insufficient funds for withdrawal.');
    END IF;

    IF :NEW.TransactionType = 'Deposit' AND :NEW.Amount <= 0 THEN
        RAISE_APPLICATION_ERROR(-20003, 'Deposit amount must be positive.');
    END IF;
END CheckTransactionRules;
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
   -- Update statement
   EXECUTE IMMEDIATE '
      UPDATE Customers
      SET Name = ''Rishi''
      WHERE CustomerID = 3';

   -- Insert statements
   EXECUTE IMMEDIATE '
      INSERT INTO Transactions (TransactionID, AccountID, TransactionDate, Amount, TransactionType)
      VALUES (4, 1, SYSDATE, 500, ''Deposit'')';

   EXECUTE IMMEDIATE '
      INSERT INTO Transactions (TransactionID, AccountID, TransactionDate, Amount, TransactionType)
      VALUES (5, 1, SYSDATE, 1500, ''Withdrawal'')';

EXCEPTION
   WHEN OTHERS THEN
      DBMS_OUTPUT.PUT_LINE('An error occurred: ' || SQLERRM);
END;
/




--************************************************************************************************************************************************************************************

