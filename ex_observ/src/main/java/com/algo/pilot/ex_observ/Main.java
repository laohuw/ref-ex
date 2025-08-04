package com.algo.pilot.ex_observ;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class Main {

    static Logger logger= LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        logger.info("ex_observ starts");
        new SpringApplicationBuilder(Main.class)
                .run(args);
    }
}
