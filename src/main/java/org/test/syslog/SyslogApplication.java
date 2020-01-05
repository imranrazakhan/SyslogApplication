package org.test.syslog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//CHECKSTYLE:OFF

@SpringBootApplication
public class SyslogApplication {
    /**
     * A main method to start this application.
     */
    public static void main(String[] args) {
        SpringApplication.run(SyslogApplication.class, args);
    }

}
//CHECKSTYLE:ON