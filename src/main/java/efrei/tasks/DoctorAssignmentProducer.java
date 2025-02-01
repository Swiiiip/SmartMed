package efrei.tasks;

import efrei.utils.JMSConnectionFactory;

import javax.jms.*;

public class DoctorAssignmentProducer {
    public static void main(String[] args) throws Exception {
        Connection connection = JMSConnectionFactory.createConnection();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Queue queue = session.createQueue("DoctorAssignmentQueue");
        MessageProducer producer = session.createProducer(queue);
        producer.setDeliveryMode(DeliveryMode.PERSISTENT);

        for (int i = 1; i <= 3; i++) {
            String task = "Patient Case #" + i;
            Message message = session.createTextMessage(task);
            System.out.println("Assigned Task: " + task);
            producer.send(message);
        }

        session.close();
        connection.close();
    }
}
