package org.test.syslog.itest;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import java.util.concurrent.TimeUnit;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.NotifyBuilder;
import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.apache.camel.test.spring.junit5.EnableRouteCoverage;
import org.apache.camel.test.spring.junit5.MockEndpoints;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.test.syslog.SyslogApplication;

import static org.apache.camel.builder.Builder.body;
import static org.junit.Assert.assertTrue;

@CamelSpringBootTest
@SpringBootTest(classes = SyslogApplication.class)
@EnableRouteCoverage
@MockEndpoints("mina:udp://localhost:5140?sync=false") // mock the log:foo endpoint => mock:log:foo which we then use in the testing
public class testSyslogUdpMessageSender {

    private final String message = "<165>Aug  4 05:34:00 mymachine myproc[10]: %% It's time to make the do-nuts.  %%"
            + "  Ingredients: Mix=OK, Jelly=OK #\n"
            + "  Devices: Mixer=OK, Jelly_Injector=OK, Frier=OK # Transport:\n"
            + "  Conveyer1=OK, Conveyer2=OK # %%";

    @Autowired
    private CamelContext camelContext;


    @Test
    public void testSendingSyslogUDP() throws Exception {

        // we expect that one or more messages is automatic done by the Camel
        // route as it uses a timer to trigger
        NotifyBuilder notify = new NotifyBuilder(camelContext)
                .whenAnyDoneMatches(
                    body().isEqualTo(message )
                ).create();

    }



}
