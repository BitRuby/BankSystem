package com.onwelo.practice.bts.ftp;


import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Configuration
@Component
public class FtpConfig {
    @Value("${ftp.host}")
    private String host;
    @Value("${ftp.user}")
    private String user;
    @Value("${ftp.passphrase}")
    private String passphrase;
    @Value("${ftp.port}")
    private int port;
    @Value("${bank.iban}")
    private String ftpDirectory;
    @Value("${ftp.autostart}")
    private boolean isAutostart;
    @Value("${ftp.timeout}")
    private short timeout;
}
