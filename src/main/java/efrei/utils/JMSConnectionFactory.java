package efrei.utils;

import javax.jms.ConnectionFactory;
import org.apache.activemq.ActiveMQConnectionFactory;

public class JMSConnectionFactory {
    private static final String BROKER_URL = "tcp://localhost:61616";

    public static ConnectionFactory getConnectionFactory() {
        return new ActiveMQConnectionFactory(BROKER_URL);
    }
}
