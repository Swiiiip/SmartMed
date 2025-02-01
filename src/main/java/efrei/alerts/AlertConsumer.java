package efrei.alerts;

import efrei.utils.JMSConnectionFactory;

import javax.jms.*;

public class AlertConsumer implements MessageListener {
    private final String name;
    private static final int LISTENER_DELAY = 60_000;

    public AlertConsumer(String name) {
        this.name = name;
    }

    public static void main(String[] args) throws Exception {
        Connection connection = JMSConnectionFactory.createConnection();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Topic topic = session.createTopic("EmergencyAlerts");

        MessageConsumer doctor = session.createConsumer(topic);
        doctor.setMessageListener(new AlertConsumer("Doctor"));

        MessageConsumer nurse = session.createConsumer(topic);
        nurse.setMessageListener(new AlertConsumer("Nurse"));

        connection.start();
        Thread.sleep(LISTENER_DELAY);

        session.close();
        connection.close();
    }

    @Override
    public void onMessage(Message message) {
        if (message instanceof TextMessage) {
            try {
                System.out.println(name + " received alert: " + ((TextMessage) message).getText());
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }
}
