package com.onwelo.practice.bts;

import com.onwelo.practice.bts.service.BankAccountServiceTest;
import com.onwelo.practice.bts.service.TransferServiceTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith({SpringExtension.class,
        BankAccountServiceTest.class,
        TransferServiceTest.class})
@SpringBootTest
public class BtsApplicationTests {

    @Test
    public void contextLoads() {
    }

}
