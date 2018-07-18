package com.onwelo.practice.bts.entity;

import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "bank_account")
@Where(clause = "is_active=1")
public class BankAccount {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, name = "account_no")
    private String accountNo;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "money_amount")
    private Float moneyAmount;

    @Column(name = "money_blocked")
    private Float moneyBlocked;

    @OneToMany(mappedBy = "sourceAcc", cascade = CascadeType.ALL)
    private List<Transfer> transfers;

    @Column(name = "is_active")
    private Boolean active = true;

    @Override
    public String toString() {
        return "BankAccount{" +
                "id=" + id +
                ", accountNo='" + accountNo + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", moneyAmount=" + moneyAmount +
                ", moneyBlocked=" + moneyBlocked +
                ", active=" + active +
                '}';
    }

    public BankAccount() {
    }

    public BankAccount(String accountNo, String firstName, String lastName, Float moneyAmount, Float moneyBlocked) {
        this.accountNo = accountNo;
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

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
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

    public List<Transfer> getTransfers() {
        return transfers;
    }

    public void setTransfers(List<Transfer> transfers) {
        this.transfers = transfers;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
