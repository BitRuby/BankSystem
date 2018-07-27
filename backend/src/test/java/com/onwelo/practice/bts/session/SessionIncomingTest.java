package com.onwelo.practice.bts.session;

import com.onwelo.practice.bts.entity.Transfer;
import com.onwelo.practice.bts.ftp.FtpService;
import com.onwelo.practice.bts.service.CsvService;
import com.onwelo.practice.bts.service.TransferService;
import com.onwelo.practice.bts.utils.TransferStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.File;
import java.util.ArrayList;

@SpringBootConfiguration
@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations = "classpath:application-test.properties")
public class SessionIncomingTest {
    private static org.slf4j.Logger Logger = LoggerFactory.getLogger(SessionIncoming.class);
    @Value("${bank.iban}")
    private static String bankDirectory;
    private ArrayList<Transfer> transferArrayList;
    @Autowired
    private TransferService transferService;
    @Autowired
    private FtpService ftpService;
    @Autowired
    private CsvService csvService;

    @BeforeAll
    void prepareTestEnvironment() {
        ArrayList<Transfer> transfers = (ArrayList<Transfer>) transferService.getTransfersByStatus(TransferStatus.PENDING);
        File file = csvService.getCsvFromTransfers(transfers, "tmp.csv");
    }

}
