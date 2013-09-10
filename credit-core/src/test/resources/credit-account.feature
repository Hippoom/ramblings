Feature: Operator create credit account

In order to store credits for the customer
As an operator
I want to create a credit account

Scenario: Operator create a credit account

When I specify account id as '6'
And I specify effective date range between '2012-01-01' and '2012-02-01'
And I specify initial balance as '100'
Then a credit account is created