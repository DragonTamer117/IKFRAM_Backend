package application.models.auth;

import application.config.JwtService;
import application.dtos.UserDTO;
import application.enums.Role;
import application.models.users.User;
import application.models.users.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public void register(UserDTO userDTO) {
        User user = User.builder()
                .email(userDTO.getEmail())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .role(Role.CUSTOMER)
                .firstName(userDTO.getFirstName())
                .middleName(userDTO.getMiddleName())
                .lastName(userDTO.getLastName())
                .dateOfBirth(userDTO.getDateOfBirth())
                .street(userDTO.getStreet())
                .houseNr(userDTO.getHouseNr())
                .postalCode(userDTO.getPostalCode())
                .city(userDTO.getCity())
                .build();

        userRepository.save(user);
        AuthenticationResponse.builder().build();
    }

    public AuthenticationResponse login(AuthenticationRequest login) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            login.getEmail(),
                            login.getPassword()
                    )
            );
        } catch (Exception e) {
            throw new Exception();
        }

        User user = userRepository.findByEmail(login.getEmail())
                .orElseThrow();
        String jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
