package com.btcgrouppl.btc.restclient.service.endpoint;

import com.btcgrouppl.btc.restclient.service.BtcRestClientServiceSpringConfiguration;
import org.springframework.boot.SpringApplication;

/**
 * Created by Sebastian Mekal <sebitg@gmail.com> on 11.05.15.
 */
public class BtcRestClientMain {

    public static void main(String[] args) {
        SpringApplication.run(BtcRestClientServiceSpringConfiguration.class, args);
    }
}
