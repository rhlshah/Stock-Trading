# Stock-Trading System

Tech Stack: Java, Spring Boot, Spring MVC, Spring Data JPA, Spring Security, Thymeleaf, PostgreSQL

The task is to create a stock trading platform where users can buy and sell stocks. The system should
support two types of users. One is the customer of the stock trading platform the other is an
administrator of the system. In this project the administrator will be responsible for creating the stocks
and setting the initial price.


User Requirements:
- Create a user account with full name, username, and email.
- Can buy and sell stocks at market price.
- Can buy and sell stocks using limit order.
o The user will set a desired price to buy or sell the stock and a date when to expire the
limit order if it is not fulfilled. The user should also have the option to cancel this order
before it gets executed.

- View their current portfolio of stocks and cash.
- View their history of transactions.
- Ability to deposit and withdraw cash.
o The user when depositing cash will have the funds go into a cash account.
o The user should only be able to withdraw money from their cash account.
o When stocks are sold the funds will go to cash account.

Administrators Requirements:
- Create new stocks.
o Include Company name, stock ticker, volume, and initial price.
o Volume will be total amount of shares purchased.
- Change market hours.
o Users should only be able to execute trades during market hours.
- Change market schedule
o Market should only be open during weekdays and closed on holidays.

User interface Requirements:
- Display available stocks that can be traded.
o Show stock ticker, price, volume, and market capitalization (volume X price)
o Show opening price for the stock
o Show high and low during the day
- Perform the user and administrator functions as listed in the requirements from the UI.
Random Stock Price Generator:
- Allow the stock prices to fluctuate during the day with a custom random price generator. The
price should gradually go up or down throughout the day.

