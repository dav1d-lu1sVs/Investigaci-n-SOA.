package bikestore_async;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String EXCHANGE = "orders.exchange";
    public static final String PAYMENT_QUEUE = "payment.queue";
    public static final String EMAIL_QUEUE = "email.queue";
    public static final String DLQ = "dead-letter.queue";

    @Bean
    public FanoutExchange ordersExchange() {
        return new FanoutExchange(EXCHANGE, true, false);
    }

    @Bean
    public Queue paymentQueue() {
        return new Queue(PAYMENT_QUEUE, true);
    }

    @Bean
    public Queue emailQueue() {
        return new Queue(EMAIL_QUEUE, true);
    }

    @Bean
    public Queue deadLetterQueue() {
        return new Queue(DLQ, true);
    }

    @Bean
    public Binding paymentBinding() {
        return BindingBuilder.bind(paymentQueue()).to(ordersExchange());
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public CommandLineRunner declareRabbitElements(RabbitAdmin rabbitAdmin) {
        return args -> {
            rabbitAdmin.declareExchange(ordersExchange());
            rabbitAdmin.declareQueue(paymentQueue());
            rabbitAdmin.declareQueue(emailQueue());
            rabbitAdmin.declareQueue(deadLetterQueue());
            rabbitAdmin.declareBinding(paymentBinding());

            System.out.println("RabbitMQ configurado correctamente");
        };
    }
}