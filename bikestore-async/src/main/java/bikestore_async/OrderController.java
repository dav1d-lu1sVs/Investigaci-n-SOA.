package bikestore_async;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderProducer orderProducer;

    public OrderController(OrderProducer orderProducer) {
        this.orderProducer = orderProducer;
    }

    @PostMapping
    public String createOrder(@RequestBody OrderMessage orderMessage) {

        orderProducer.sendOrder(orderMessage);

        return "Pedido enviado correctamente";
    }
}