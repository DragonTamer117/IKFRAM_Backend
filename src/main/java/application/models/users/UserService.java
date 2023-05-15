package application.models.users;

import application.config.JwtService;
import application.dtos.UserDTO;
import application.enums.Role;
import application.exceptions.UnauthorizedException;
import com.zhaofujun.automapper.AutoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final AutoMapper mapper = new AutoMapper();
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    protected User createUser(UserDTO userDTO) throws Exception {
        try {
            User user = User.builder()
                    .email(userDTO.getEmail())
                    .role(userDTO.getRole())
                    .password(passwordEncoder.encode(userDTO.getPassword()))
                    .firstName(userDTO.getFirstName())
                    .middleName(userDTO.getMiddleName())
                    .lastName(userDTO.getLastName())
                    .build();

            userRepository.save(user);
            return user;
        } catch (Exception e) {
            throw new Exception();
        }
    }

    protected User updateUserAsAdmin(UUID id, UserDTO userDTO) {
        User user = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        User updatedUser = mapper.map(userDTO, User.class);
        Field[] fields = User.class.getDeclaredFields();

        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Object value = field.get(updatedUser);
                if (value != null) {
                    field.set(user, value);
                }
            } catch (IllegalAccessException e) {
                System.out.println("User not found with id: " + id);
            }
        }

        return userRepository.save(user);
    }

    protected User updateUser(UUID id, UserDTO userDTO) {
        User user = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        User updatedUser = mapper.map(userDTO, User.class);
        Field[] fields = User.class.getDeclaredFields();

        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Object dtoValue = field.get(updatedUser);
                if (field.getName().equals("role")) {
                    throw new UnauthorizedException("You are not allowed to change your role", HttpStatus.UNAUTHORIZED);
                }
            } catch (IllegalAccessException e) {
                System.out.println("User not found with id: " + id);
            }
        }

        return userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(UUID id) {
        return userRepository.findById(id).get();
    }

    public boolean isEmailPresent(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public User getUser(String bearerToken) {
        String token = bearerToken.substring(7);
        String email = jwtService.extractUsername(token);

        return userRepository.findByEmail(email).get();
    }

    public boolean isAllowedRole(String bearerToken) {
        User user = getUser(bearerToken);

        return user.getRole() == Role.ADMIN || user.getRole() == Role.MODERATOR;
    }

    public boolean isCustomer(String bearerToken) {
        User user = getUser(bearerToken);

        return user.getRole() == Role.CUSTOMER;
    }
}
