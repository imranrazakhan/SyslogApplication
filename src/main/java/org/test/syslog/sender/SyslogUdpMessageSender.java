package org.test.syslog.sender;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.syslog.SyslogFacility;
import org.apache.camel.component.syslog.SyslogMessage;
import org.apache.camel.component.syslog.SyslogSeverity;
import org.springframework.stereotype.Component;

import java.util.Calendar;

@Component
public class SyslogUdpMessageSender extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("timer:hello?period=60s")
         .routeId("sl").routeGroup("sl-group")
         .process(new Processor() {
             public void process(Exchange exchange) {
                 Calendar calendar = Calendar.getInstance();
                 //calendar.set(2016, Calendar.SEPTEMBER, 26, 19, 30, 55);

                 SyslogMessage message = new SyslogMessage();
                // message.setHostname("syslog-test-client");
                // message.setSeverity(SyslogSeverity.EMERG);
               //  message.setFacility(SyslogFacility.KERN);
               //  message.setLogMessage("mymachine su: 'su root' failed for lonvick on /dev/pts/8");
                 message.setLogMessage("<10>this is critical");
                // message.setTimestamp(calendar);
                 exchange.getIn().setBody(message);
             }
         })
         .marshal().syslog()
         .to("netty:udp://127.0.0.1:514?sync=false&allowDefaultCodec=false&useByteBuf=true")
         //.to("stream:out")
          ;
    }

}