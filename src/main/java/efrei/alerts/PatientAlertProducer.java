package efrei.alerts;

import javax.jms.*;

import efrei.utils.JMSConnectionFactory;

public class PatientAlertProducer {
    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = JMSConnectionFactory.getConnectionFactory();
        Connection connection = factory.createConnection();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Topic topic = session.createTopic("EmergencyAlerts");
        MessageProducer producer = session.createProducer(topic);
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

        String alert = "Patient in Room 5 requires immediate attention!";
        Message message = session.createTextMessage(alert);
        producer.send(message);

        System.out.println("Sent Alert: " + alert);

        session.close();
        connection.close();
    }
}
