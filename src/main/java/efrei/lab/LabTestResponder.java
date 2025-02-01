package efrei.lab;

import efrei.utils.JMSConnectionFactory;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class LabTestResponder {
    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = JMSConnectionFactory.getConnectionFactory();
        Connection connection = factory.createConnection();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Queue requestQueue = session.createQueue("LabRequestQueue");
        MessageConsumer consumer = session.createConsumer(requestQueue);

        connection.start();

        while (true) {
            Message request = consumer.receive();
            if (request instanceof TextMessage) {
                Queue replyQueue = (Queue) request.getJMSReplyTo();
                MessageProducer producer = session.createProducer(replyQueue);

                TextMessage response = session.createTextMessage("Blood Test Normal");
                producer.send(response);
                System.out.println("Processed Lab Request.");
            }
        }
    }
}
