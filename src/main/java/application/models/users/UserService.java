package application.models.users;

import application.config.JwtService;
import application.dtos.UserDTO;
import application.enums.Role;
import com.zhaofujun.automapper.AutoMapper;
import lombok.RequiredArgsConstructor;
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

    private boolean shouldSkipFieldUpdate(Field field, Object dtoValue, Object userValue) {
        String fieldName = field.getName();

        if (fieldName.equals("role") || fieldName.equals("password")) {
            return dtoValue == null;
        } else if (fieldName.equals("email")) {
            String newEmail = (String) dtoValue;
            String currentEmail = (String) userValue;

            return newEmail.equals(currentEmail);
        }

        return false;
    }

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

    // TODO: This is for later, when we change a User but the password does not go through the passwordEncoder.
    protected User updateUserAsAdmin(UUID id, UserDTO userDTO) {
        User user = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        User updatedUser = mapper.map(userDTO, User.class);
        Field[] fields = User.class.getDeclaredFields();

        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Object value = field.get(updatedUser);
                if (value != null) {
                    if (field.getName().equals("password")) {
                        String encodedPassword = passwordEncoder.encode((String) value);
                        field.set(user, encodedPassword);
                    } else {
                        field.set(user, value);
                    }
                }
            } catch (IllegalAccessException e) {
                System.out.println("User not found with id: " + id);
            }
        }

        return userRepository.save(user);
    }

    // TODO: Refactor code, so that I am not changing the modifiers of the variables in the User class.
    protected User updateUser(UUID id, UserDTO userDTO) {
        User alreadySavedUser = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        User incomingUser = mapper.map(userDTO, User.class);

        for (Field field : User.class.getDeclaredFields()) {
            try {
                field.setAccessible(true);
                Object dtoValue = field.get(incomingUser);
                Object userValue = field.get(alreadySavedUser);

                if (shouldSkipFieldUpdate(field, dtoValue, userValue)) {
                    continue;
                }

                if (!field.getName().equals("id")) {
                    field.set(alreadySavedUser, dtoValue);
                }
            } catch (IllegalAccessException e) {
                System.out.println("User not found with id: " + id);
            }
        }

        return userRepository.save(alreadySavedUser);
    }

    protected List<User> findAll() {
        return userRepository.findAll();
    }

    protected User findById(UUID id) {
        return userRepository.findById(id).get();
    }

    protected boolean isEmailPresent(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public User getUser(String bearerToken) {
        String token = bearerToken.substring(7);
        String email = jwtService.extractUsername(token);

        return userRepository.findByEmail(email).get();
    }

    protected void saveTempPassword(String password, String email) {
        UUID userId = userRepository.findByEmail(email).get().getId();

        userRepository.updatePassword(userId, password);
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
