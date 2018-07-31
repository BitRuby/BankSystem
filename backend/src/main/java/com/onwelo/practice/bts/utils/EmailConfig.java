package com.onwelo.practice.bts.utils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class EmailConfig implements Serializable {
    private String to;
    private String subject;
    private String accountNo;
    private String title;
    private String value;
    private String transferDate;
    private String reason;

    public EmailConfig(String to, String subject, String accountNo, String title, String value, String transferDate, String reason) {
        this.to = to;
        this.subject = subject;
        this.accountNo = accountNo;
        this.title = title;
        this.value = value;
        this.transferDate = transferDate;
        this.reason = reason;
    }
}
