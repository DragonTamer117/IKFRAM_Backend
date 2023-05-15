package application.models.orders;

import application.dtos.OrderDTO;
import application.enums.OrderStatus;
import application.models.products.ProductService;
import application.models.users.User;
import application.models.users.UserService;
import com.zhaofujun.automapper.AutoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final AutoMapper mapper = new AutoMapper();
    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final UserService userService;

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public List<Order> findAllCustomerOrders(String bearerToken) {
        User user = userService.getUser(bearerToken);
        String userId = user.getId().toString();

        return orderRepository.findAllByUserId(userId);
    }

    public Order create(OrderDTO orderDTO, String token) {
        User user = userService.getUser(token);
        Order order = Order.builder()
                .userId(user.getId())
                .orderStatus(OrderStatus.ORDER_CONFIRMED)
                .build();

        orderRepository.save(order);

        for (UUID productId : orderDTO.getProducts()) {
            productService.create(productId, order);
        }

        return order;
    }
}
