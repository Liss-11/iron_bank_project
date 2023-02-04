<img src="./images/Screenshot 2023-02-04 at 03.50.25.png">

# IRON_BANK

> APPLICATION

- API designed in Java17 and Spring Boot, with Security.

- Implements JWT Tokem for Login, and Cookies for the authentification management.

- Permits RESET your password using a custom question and and secret answer, associated to your account.

- You can have different accounts, and use our CreditCard service.




> USER_CREATIOIN

    - CUSTOMER: ordinary user that can ask for open an user_account in the bank. 

    - ADMIN: can manage all users and accounts, and also cretae/delete other admins.

    - THIRD_PARTY: external entities, that are registered in the bank. They haven't their own account, but can send and extract money from users_accaunts, using a [hashedKey and name] for his authentication, and account [id and a related secret key] -> in order to indicate the destination account.

- You CAN ask for a Customer account creation. Your request will be saved in the system, and an ADMIN will ACTIVATE your account. After that you will be able to use it. [ADMIN cap directly create a Admin or Customer account].

- Our ADMINS will create the accounts you will ask for.

- You can also ask for DELETE you userAccount, and wait for the acception of our system. 

> LOGIN

- If your USER is not ACTIVE, you can't access to your CUSER_ACCOUNT. Also your accounts are BLOCKED, nobody can make transactions with them. -> an ADMIN car reactive your CUSER_ACCOUNT.

- If you forgot your Customer_password, you can use the [forgot_passwordE]ENDPOINT, to request se secret question, and after the [reset_password] ENDPOINT, passing you email, secret answer, and new password.

> ACCOUNTS
    
    CHECKING_ACCOUNT: 
        with minimum balance, penality_fee, monthly manteinance_fee.

    STUDENT_CHECKING_ACCOUNT:
        without minimum_balance and fees.

    SAVING_ACCOUNT:
        with minimum_balance, penality_fee and interest_rate (year).

    CREDIT_CARD_ACCOUNT:
        with maximum_credit, penality_fee, monthly interest_rate

- When a checking account is created, the age of the user is checked, and in case to be lower than 24 years old, a Student Account will be assigned. 

- A Customer can see only his own accounts, and make autorized transaction, if the status of the CUSTOMER and of the ACCOUNTS permit it.

- If you have a Checking or a Student ACCOUNT -> you can ask for a CREDIT_CARD_ACCOUNT. It will be associated to you existent, choosed Checking/Student Account. If your principal account is not ACTIVE, you will not be able to use your CREDIT_CARD.
- ADMIN can manage account properties.

> PENALITY_FEES AND INTERESTS

- In creation User process, ADMIN can personalize interests and minimum_balances [within specified range].
- Fees are applied authomatically.
-"Interests management" have a specific ENDPOINT -> the entity that implements the API must call this enpoint periodically in order to have all account information up to date [].

> TRANSACTIONS

    CHECKING_ACCOUNT: 
        transfers, payments, withdraw, diposit.

    STUDENT_CHECKING_ACCOUNT:
        transfers, payments, withdraw, diposit.

    SAVING_ACCOUNT:
        withdraw, diposit. [withdraws from ThirdPartyEntities]

    CREDIT_CARD_ACCOUNT:
        withdraws, payments

The transactions are checked for: 
- Existent account, with status ACTIVE or EMPTY
- User_account with Status.ACTIVE
- Allowed transactions for the accout.TYPE
- Enought money in the account in case of payments and withdraw












