import com.rabbitmq.client.*;

public class Main {
    private final static String REQUEST_QUEUE_NAME = "request_queue";
    private final static String RESPONSE_QUEUE_NAME = "response_queue";
    private final static String HOST = "72d4efe5-5145-4cce-83e0-7931d08bdf72.hsvc.ir";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(HOST);
        factory.setPort(31234);
        factory.setUsername("rabbitmq");
        factory.setPassword("iti8hM6boEmj2XZiyATiUhhSJgKZoutb");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            channel.queueDeclare(REQUEST_QUEUE_NAME, true, false, false, null);
            channel.queueDeclare(RESPONSE_QUEUE_NAME, true, false, false, null);

            String message = "2+2";
            AMQP.BasicProperties props = new AMQP.BasicProperties
                    .Builder()
                    .deliveryMode(2)
                    .build();

            channel.basicPublish("", REQUEST_QUEUE_NAME, props, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");

            System.out.println(" [*] Waiting for response...");

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String response = new String(delivery.getBody(), "UTF-8");
                System.out.println(" [x] Received response '" + response + "'");
            };

            channel.basicConsume(RESPONSE_QUEUE_NAME, true, deliverCallback, consumerTag -> {
            });
            Thread.sleep(10000);
        }
    }
}
