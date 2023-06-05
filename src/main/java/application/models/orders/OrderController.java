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

    @GetMapping("/all")
    public ResponseEntity<List<Order>> findAll(
            @RequestHeader("Authorization") String bearerToken
    ) {
        if (userService.isAllowedRole(bearerToken) || userService.isCustomer(bearerToken)) {
            if (userService.isAllowedRole(bearerToken)) {
                return ResponseEntity.ok(orderService.findAll());
            } else {
                return ResponseEntity.ok(orderService.findAllCustomerOrders(bearerToken));
            }
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    // TODO: Find orders of users, based on the id as PathVariable.
    @GetMapping("/{id}")
    public ResponseEntity<List<Order>> findAllByUserID(
            @RequestHeader("Authorization") String bearerToken,
            @PathVariable String id) {
        if (userService.isAllowedRole(bearerToken) || userService.isCustomer(bearerToken)) {
            return ResponseEntity.ok(orderService.findByUserId(id));
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

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
