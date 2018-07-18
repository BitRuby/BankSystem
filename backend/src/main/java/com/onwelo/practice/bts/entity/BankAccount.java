package com.onwelo.practice.bts.entity;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "bank_account")
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String bankNumber;

    private String firstName;

    private String lastName;

    private Float moneyAmount;

    private Float moneyBlocked;

    @OneToMany(mappedBy = "destination", targetEntity = Transfer.class, fetch = FetchType.EAGER)
    private Collection incomingTransfers;

    @OneToMany(mappedBy = "source", targetEntity = Transfer.class, fetch = FetchType.EAGER)
    private Collection outgoingTransfers;

    public BankAccount() {
    }

    public BankAccount(String bankNumber, String firstName, String lastName, Float moneyAmount, Float moneyBlocked) {
        this.bankNumber = bankNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.moneyAmount = moneyAmount;
        this.moneyBlocked = moneyBlocked;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBankNumber() {
        return bankNumber;
    }

    public void setBankNumber(String bankNumber) {
        this.bankNumber = bankNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Float getMoneyAmount() {
        return moneyAmount;
    }

    public void setMoneyAmount(Float moneyAmount) {
        this.moneyAmount = moneyAmount;
    }

    public Float getMoneyBlocked() {
        return moneyBlocked;
    }

    public void setMoneyBlocked(Float moneyBlocked) {
        this.moneyBlocked = moneyBlocked;
    }

    public Collection getIncomingTransfers() {
        return incomingTransfers;
    }

    public Collection getOutgoingTransfers() {
        return outgoingTransfers;
    }
}
