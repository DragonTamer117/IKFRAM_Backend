package application.models.orders;

import application.dtos.OrderDTO;
import application.enums.OrderStatus;
import application.models.products.ProductService;
import application.models.users.User;
import application.models.users.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final UserService userService;

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public List<Order> findAllCustomerOrders(String bearerToken) {
        User user = userService.getUser(bearerToken);
        UUID userId = user.getId();

        return orderRepository.findAllByUserId(userId);
    }

    // TODO: products is null, because we don't build them along with the order. So products will always be null.
    //  Change it so products is shown as a list of UUID's of products.
    public Order create(OrderDTO orderDTO, String bearerToken) {
        User user = userService.getUser(bearerToken);
        Order order = Order.builder()
                .userId(user.getId())
                .date(new Date())
                .orderStatus(OrderStatus.ORDER_CONFIRMED)
                .build();

        orderRepository.save(order);

        for (UUID productId : orderDTO.getProducts()) {
            productService.create(productId, order);
        }

        return order;
    }
}
