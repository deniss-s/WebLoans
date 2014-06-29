# WebLoans microservice

## Compiling

Compile with maven: `mvn clean install`

## Running

Run as a simple jar: `java -jar webloans-1.0-SNAPSHOT.jar`

## Configuration

Config file is `application.yml` in `src/main/resources`

Note that cucumber tests expect the default port, i.e. 8080.

Defaults are:

    server:
        port: 8080
    
    loan:
        interest: 5.00
        factor: 1.50
        weekIncreasePerExtension: 1
    
        assessment:
            maxAmount: EUR 1000.00
            maxLoansPerDay: 3
            morningHour: 7

## REST endpoints

#####Loan history:

GET    `/rest/loans`

#####Apply for a loan:

POST   `/rest/loans` 

    params:
        amount: 500.00
        term: 30
    
#####Extend loan:
    
POST   `/rest/loans/{loanId}/extend`