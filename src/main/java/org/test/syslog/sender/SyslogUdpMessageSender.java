package org.test.syslog.sender;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.syslog.SyslogMessage;
import org.springframework.stereotype.Component;

import java.util.Calendar;

@Component
public class SyslogUdpMessageSender extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("timer:hello?period=5s")
         .routeId("sl").routeGroup("sl-group")
         .process(new Processor() {
             public void process(Exchange exchange) {
                 Calendar calendar = Calendar.getInstance();
                 //calendar.set(2016, Calendar.SEPTEMBER, 26, 19, 30, 55);

                 SyslogMessage message = new SyslogMessage();
                 message.setHostname("camel-test-host");
                 message.setLogMessage("<165>Aug  4 05:34:00 mymachine myproc[10]: %% It's time to make the do-nuts.  %%"
                         + "  Ingredients: Mix=OK, Jelly=OK #\n"
                         + "  Devices: Mixer=OK, Jelly_Injector=OK, Frier=OK # Transport:\n"
                         + "  Conveyer1=OK, Conveyer2=OK # %%");
                 message.setTimestamp(calendar);
                 exchange.getIn().setBody(message);
             }
         })
         .marshal().syslog()
         .to("mina:udp://localhost:5140?sync=false")
         .to("stream:out");
    }

}