package efrei.lab;

import efrei.utils.JMSConnectionFactory;

import javax.jms.*;

public class LabTestRequester {
    public static void main(String[] args) throws Exception {
        Connection connection = JMSConnectionFactory.createConnection();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Queue requestQueue = session.createQueue("LabRequestQueue");
        Queue replyQueue = session.createQueue("LabResponseQueue");

        MessageProducer producer = session.createProducer(requestQueue);
        MessageConsumer consumer = session.createConsumer(replyQueue);

        connection.start();

        for (int i = 1; i <= 3; i++) {
            TextMessage requestMessage = session.createTextMessage("Blood Test for Patient #" + i);
            requestMessage.setJMSReplyTo(replyQueue);
            producer.send(requestMessage);
        }

        Message response = consumer.receive();
        if (response instanceof TextMessage) {
            System.out.println("Received Lab Result: " + ((TextMessage) response).getText());
        }

        session.close();
        connection.close();
    }
}
