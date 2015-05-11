package com.btcgrouppl.btc.restclient.service;

import com.btcgrouppl.btc.restclient.backend.BtcRestClientBackendSpringConfiguration;
import com.btcgrouppl.btc.restclient.web.BtcRestClientWebSpringConfiguration;
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
@Import({BtcRestClientBackendSpringConfiguration.class, BtcRestClientWebSpringConfiguration.class})
public class BtcRestClientServiceSpringConfiguration {
}
