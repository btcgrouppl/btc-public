package com.btcgrouppl.btc.transactions.service;

import com.btcgrouppl.btc.transactions.backend.BtcTransactionsBackendSpringConfiguration;
import com.btcgrouppl.btc.transactions.web.BtcTransactionsWebSpringConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by Sebastian Mekal <sebitg@gmail.com> on 11.05.15.
 */
 @Configuration
 @ComponentScan
 @EnableAutoConfiguration
 @Import({BtcTransactionsBackendSpringConfiguration.class, BtcTransactionsWebSpringConfiguration.class})
public class BtcTransactionsServiceSpringConfiguration {



}