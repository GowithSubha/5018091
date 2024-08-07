--Scenario 1: The bank wants to apply a discount to loan interest rates for customers above 60 years old.

DECLARE
  cust_id NUMBER;
  dob DATE;
  age NUMBER;
  loan_id NUMBER;
  interest_rate NUMBER;

BEGIN
  FOR r IN (SELECT customerid, dob FROM customers) LOOP
    cust_id := r.customerid;
    dob := r.dob;
    age := TRUNC(MONTHS_BETWEEN(SYSDATE, dob) / 12);

    IF age > 60 THEN
      FOR loan_rec IN (SELECT loanid, interestrate FROM loans WHERE customerid = cust_id) LOOP
        loan_id := loan_rec.loanid;
        interest_rate := loan_rec.interestrate;

        UPDATE loans
        SET interestrate = interest_rate - 1
        WHERE loanid = loan_id;
        DBMS_OUTPUT.PUT_LINE('Discount applied to loan ' || loan_id || ' for customer ' || cust_id);
      END LOOP;
    END IF;
  END LOOP;
END;
/

--************************************************************************************************************************************************************************************
--Scenario 2: A customer can be promoted to VIP status based on their balance.
--adding new column for storing true or false as flag value
--ALTER TABLE customers
--ADD (isvip VARCHAR2(5));

BEGIN
   EXECUTE IMMEDIATE '
      ALTER TABLE customers
      ADD (isvip VARCHAR2(5))';
EXCEPTION
   WHEN OTHERS THEN
      DBMS_OUTPUT.PUT_LINE('An error occurred: ' || SQLERRM);
END;
/

DECLARE
  cust_id NUMBER;
  balance NUMBER;

BEGIN
  FOR rec IN (SELECT customerid, balance FROM customers) LOOP
    cust_id := rec.customerid;
    balance := rec.balance;

    IF balance > 10000 THEN
      UPDATE customers
      SET isvip = 'TRUE'
      WHERE customerid = cust_id;

      DBMS_OUTPUT.PUT_LINE('Customer ' || cust_id || ' is now a VIP');
    ELSE
      UPDATE customers
      SET isvip = 'FALSE'
      WHERE customerid = cust_id;
    END IF;
  END LOOP;
END;
/

--************************************************************************************************************************************************************************************
-- Scenario 3: The bank wants to send reminders to customers whose loans are due within the next 30 days.

DECLARE
  loan_id NUMBER;
  cust_id NUMBER;
  due_date DATE;

BEGIN
  FOR rec IN (SELECT loanid, customerid, enddate FROM loans WHERE enddate <= SYSDATE + 30) LOOP
    loan_id := rec.loanid;
    cust_id := rec.customerid;
    due_date := rec.enddate;

    DBMS_OUTPUT.PUT_LINE('Reminder: Loan ' || loan_id || ' for customer ' || cust_id || ' is due on ' || due_date);
  END LOOP;
END;
/





--************************************************************************************************************************************************************************************
--This is for testing

select * from customers;
select * from accounts;
select * from Transactions; 
select * from Loans;
select * from Employees; 

--************************************************************************************************************************************************************************************

