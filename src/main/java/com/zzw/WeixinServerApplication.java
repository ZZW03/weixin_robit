package com.zzw;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.zzw.infrastuction.dao")
@SpringBootApplication
public class WeixinServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeixinServerApplication.class, args);
    }

}
