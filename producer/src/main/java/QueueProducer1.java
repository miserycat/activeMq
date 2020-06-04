import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class QueueProducer1 {
    private static final String brokerURL = "tcp://10.211.55.3:61616";
    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerURL);
        Connection connection = null;
        try {
            connection = connectionFactory.createConnection();
            connection.start();

            Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);

            Destination destination = session.createQueue("myQueue");
            MessageProducer producer = session.createProducer(destination);

            TextMessage message = session.createTextMessage("Hello World");

            producer.send(message);

            session.commit();
            session.close();
        } catch (JMSException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }

            }
        }


    }
}
