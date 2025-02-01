package efrei.broker;

import efrei.utils.JMSConnectionFactory;
import org.apache.activemq.broker.BrokerService;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;

public class BrokerSetup {
    public static void main(String[] args) throws Exception {
        BrokerService broker = new BrokerService();
        ConnectionFactory factory = JMSConnectionFactory.getConnectionFactory();
        Connection connection = factory.createConnection();
        broker.addConnector(connection.getClientID());
        broker.start();
        System.out.println("ActiveMQ Broker Started...");
    }
}