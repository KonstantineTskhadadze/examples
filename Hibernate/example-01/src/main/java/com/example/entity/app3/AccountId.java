package com.example.entity.app3;


import java.io.Serializable;

public class AccountId implements Serializable {

    private static final long serialVersionUID = 1L;

    private String accountNumber;
    private String accountType;

    public AccountId() {

    }

    public AccountId(final String accountNumber, final String accountType) {
        this.accountNumber = accountNumber;
        this.accountType = accountType;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.accountNumber == null) ? 0 : this.accountNumber.hashCode());
        result = prime * result + ((this.accountType == null) ? 0 : this.accountType.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (this.getClass() != obj.getClass())
            return false;
        final AccountId other = (AccountId) obj;
        if (this.accountNumber == null) {
            if (other.accountNumber != null)
                return false;
        } else if (!this.accountNumber.equals(other.accountNumber))
            return false;
        if (this.accountType == null) {
            if (other.accountType != null)
                return false;
        } else if (!this.accountType.equals(other.accountType))
            return false;
        return true;
    }

}
