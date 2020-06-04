import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class TopicPersistentConsumer {
    private static final String brokerURL = "tcp://10.211.55.3:61616";
//    private static final String brokerURL = "tcp://10.211.55.3:61616?jms.useAsynSend=true"; //设置异步发送方法一
    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerURL);
//        ((ActiveMQConnectionFactory)connectionFactory).setAlwaysSyncSend(true);//设置异步发送方法二
        Connection connection = null;
        try {
            connection = connectionFactory.createConnection();
            connection.setClientID("client-cassie");
            connection.start();

            Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);

            Topic destination = session.createTopic("myTopic");

            MessageConsumer consumer = session.createDurableSubscriber(destination, "cassie");
            TextMessage message = (TextMessage) consumer.receive();

            System.out.println(message.getText());


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
