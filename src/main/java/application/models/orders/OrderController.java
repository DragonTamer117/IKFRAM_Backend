package application.models.orders;

import application.models.users.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final OrderService orderService;
    private final UserService userService;

    @GetMapping
    @PreAuthorize("hasAnyRole('Role.ADMIN', 'Role.CUSTOMER')")
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
}
