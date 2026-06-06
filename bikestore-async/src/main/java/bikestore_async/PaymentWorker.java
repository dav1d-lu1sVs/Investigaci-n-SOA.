package bikestore_async;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class PaymentWorker {

    private final Random random = new Random();
    private final RabbitTemplate rabbitTemplate;

    public PaymentWorker(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = RabbitConfig.PAYMENT_QUEUE)
    public void processPayment(String message) {

        boolean success = random.nextBoolean();

        String timestamp = LocalDateTime.now().toString();
        String thread = Thread.currentThread().getName();

        int retryCount = extractRetryCount(message);

        if (success) {
            System.out.println("[PAID] pedido=" + message
                    + " | timestamp=" + timestamp
                    + " | thread=" + thread);

            rabbitTemplate.convertAndSend(RabbitConfig.EMAIL_QUEUE, message);

        } else {
            retryCount++;

            System.out.println("[FAILED] pedido=" + message
                    + " | intento=" + retryCount
                    + " | timestamp=" + timestamp
                    + " | thread=" + thread);

            if (retryCount >= 3) {
                System.out.println("[DLQ] Pedido enviado a dead-letter.queue → " + message);

                rabbitTemplate.convertAndSend(
                        RabbitConfig.DLQ,
                        updateRetryCount(message, retryCount)
                );

            } else {
                rabbitTemplate.convertAndSend(
                        RabbitConfig.PAYMENT_QUEUE,
                        updateRetryCount(message, retryCount)
                );
            }
        }
    }

    private int extractRetryCount(String message) {
        Pattern pattern = Pattern.compile("retryCount=(\\d+)");
        Matcher matcher = pattern.matcher(message);

        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        }

        return 0;
    }

    private String updateRetryCount(String message, int retryCount) {
        return message.replaceAll("retryCount=\\d+", "retryCount=" + retryCount);
    }
}