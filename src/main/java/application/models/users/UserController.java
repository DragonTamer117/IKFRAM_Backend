package application.models.users;

import application.dtos.UserDTO;
import application.enums.Role;
import com.zhaofujun.automapper.AutoMapper;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final AutoMapper mapper = new AutoMapper();
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> findAll(
            @RequestHeader("Authorization") String bearerToken
    ) {
        System.out.println("User: " + bearerToken);
        if (userService.isAllowedRole(bearerToken)) {
            var users = userService.findAll().stream()
                    .map(user -> mapper.map(user, User.class))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(users);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> find(
            @RequestHeader("Authorization") String bearerToken,
            @PathVariable UUID id
    ) {
        if (userService.isAllowedRole(bearerToken)) {
            return ResponseEntity.ok(userService.findById(id));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    // TODO: Small changes needed, such as verification and other things that can be discussed.
    @GetMapping("/current")
    public ResponseEntity<User> getCurrentUser(
            @RequestHeader("Authorization") String bearerToken
    ) {
        return ResponseEntity.ok(userService.getUser(bearerToken));
    }

    @PostMapping
    public ResponseEntity<User> create(
            @RequestHeader("Authorization") String token,
            @RequestBody UserDTO userDTO
    ) throws Exception {
        if (userService.isAllowedRole(token)) {
            if (userService.isEmailPresent(userDTO.getEmail())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }

            User user = userService.createUser(userDTO);
            return ResponseEntity.ok(user);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> update(
            @RequestHeader("Authorization") String bearerToken,
            @PathVariable UUID id,
            @RequestBody UserDTO userDTO
    ) {
        if (userService.isAllowedRole(bearerToken) || userService.isCustomer(bearerToken)) {
            if (userService.isAllowedRole(bearerToken)) {
                User user = userService.updateUserAsAdmin(id, userDTO);
                return ResponseEntity.ok(user);
            } else {
                User user = userService.updateUser(id, userDTO);
                return ResponseEntity.ok(user);
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
