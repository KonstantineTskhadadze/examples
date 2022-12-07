package com.example.entity.app3;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.NamedQuery;

@Entity
@IdClass(AccountId.class)
@NamedQuery(name = "Account.findAccountByAccountId", query = "from Account a where a.accountNumber = :accNum and a.accountType = :accType")
public class Account {
    @Id
    private String accountNumber;

    @Id
    private String accountType;

    private String description;

    public String getAccountNumber() {
        return this.accountNumber;
    }

    public void setAccountNumber(final String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountType() {
        return this.accountType;
    }

    public void setAccountType(final String accountType) {
        this.accountType = accountType;
    }


    public String getDescription() {
        return this.description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Account [accountNumber=" + this.accountNumber + ", accountType=" + this.accountType + ", description="
                + this.description + "]";
    }
}
