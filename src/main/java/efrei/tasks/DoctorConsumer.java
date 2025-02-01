package efrei.tasks;

import efrei.utils.JMSConnectionFactory;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class DoctorConsumer {
    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = JMSConnectionFactory.getConnectionFactory();
        Connection connection = factory.createConnection();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Queue queue = session.createQueue("DoctorAssignmentQueue");
        MessageConsumer consumer = session.createConsumer(queue);

        connection.start();

        while (true) {
            Message message = consumer.receive();
            if (message instanceof TextMessage) {
                System.out.println("Doctor received: " + ((TextMessage) message).getText());
            }
        }
    }
}
