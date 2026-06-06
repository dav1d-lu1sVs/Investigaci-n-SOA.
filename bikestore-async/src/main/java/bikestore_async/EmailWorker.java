package bikestore_async;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class EmailWorker {

    @RabbitListener(queues = RabbitConfig.EMAIL_QUEUE)
    public void sendEmail(String message) {

        String timestamp = LocalDateTime.now().toString();
        String thread = Thread.currentThread().getName();

        System.out.println(
                "[EMAIL] Confirmación enviada → "
                        + message
                        + " | timestamp=" + timestamp
                        + " | thread=" + thread
        );
    }
}