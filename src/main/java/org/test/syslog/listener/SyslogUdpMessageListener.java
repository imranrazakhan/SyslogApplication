package org.test.syslog.listener;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.syslog.SyslogDataFormat;
import org.apache.camel.component.syslog.SyslogMessage;
import org.springframework.stereotype.Component;

@Component
public class SyslogUdpMessageListener extends RouteBuilder {

            @Override
            public void configure() throws Exception {

                from("netty:udp://127.0.0.1:514?sync=false&allowDefaultCodec=false")
                //from("mina:udp://127.0.0.1:514")
                .routeId("sysloglistener").routeGroup("sysloglistener-group")
                .unmarshal().syslog()
                .process(new Processor() {
                    public void process(Exchange ex) {
                        if(ex.getIn().getBody() instanceof SyslogMessage) {
                            SyslogMessage smsg  = (SyslogMessage) ex.getIn().getBody();

                            System.out.println("SyslogMessage{ content='" + smsg.getLogMessage() + "', facility=" + smsg.getFacility() + ", severity=" + smsg.getSeverity() + ", remoteAddress='" + smsg.getRemoteAddress() + "' , localAddress='" + smsg.getLocalAddress() + "', hostname='" + smsg.getHostname() + "', messageTime=" + smsg.getTimestamp().getTime() + "}" );

                        } else {
                            System.out.println("######################## No Syslog Message #########################");
                        }

                    }
                })
                //.to("log:DEBUG?showBody=true&showHeaders=true")
                ;
            }

}
