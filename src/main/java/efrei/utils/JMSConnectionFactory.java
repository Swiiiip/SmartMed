package efrei.utils;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;

import org.apache.activemq.ActiveMQConnectionFactory;

public class JMSConnectionFactory {
    private static final String BROKER_URL = "tcp://localhost:61616";
    private static final ConnectionFactory factory = new ActiveMQConnectionFactory(BROKER_URL);

    public static Connection createConnection() throws JMSException {
        return factory.createConnection();
    }
}
