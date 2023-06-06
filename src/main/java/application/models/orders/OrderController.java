package application.models.orders;

import application.dtos.OrderDTO;
import application.models.users.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final OrderService orderService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<Order>> findAll(
            @RequestHeader("Authorization") String bearerToken
    ) {
        if (userService.isAllowedRole(bearerToken)) {
            return ResponseEntity.ok(orderService.findAll());
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @GetMapping("/current")
    public ResponseEntity<List<Order>> findAllOrdersOfCurrentUser(
            @RequestHeader("Authorization") String bearerToken
    ) {
        return ResponseEntity.ok(orderService.findAllCustomerOrders(bearerToken));
    }

    // TODO: When trying to create a order, maybe ask for an id of user. So that an admin can create an order for a user.
    @PostMapping
    public ResponseEntity<Order> create(
            @RequestHeader("Authorization") String bearerToken,
            @RequestBody OrderDTO orderDTO
    ) {
        if (userService.isAllowedRole(bearerToken) || userService.isCustomer(bearerToken)) {
            return ResponseEntity.ok(orderService.create(orderDTO, bearerToken));
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
}
