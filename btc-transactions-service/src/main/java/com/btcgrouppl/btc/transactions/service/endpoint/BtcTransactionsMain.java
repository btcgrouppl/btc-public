package com.btcgrouppl.btc.transactions.service.endpoint;

import com.btcgrouppl.btc.transactions.service.BtcTransactionsServiceSpringConfiguration;
import org.springframework.boot.SpringApplication;

/**
 * Created by Sebastian Mekal <sebitg@gmail.com> on 11.05.15.
 */
public class BtcTransactionsMain {

    public static void main(String[] args) {
        SpringApplication.run(BtcTransactionsServiceSpringConfiguration.class, args);
    }
}
