package cz.muni.fi.pv243.ars.messaging;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import java.util.logging.Logger;

@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "java:jboss/exported/jms/queue/dataQueue"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class Listener implements MessageListener {

    @Inject
    private Logger log;

    @Override
    public void onMessage(Message message) {
        try {
            String json = message.getBody(String.class);

            log.info("Received message: " + json);


        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

}