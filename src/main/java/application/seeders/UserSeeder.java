package application.seeders;

import application.enums.Role;
import application.models.users.User;
import application.models.users.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final List<List<Object>> userList = List.of(
            List.of("guest@ebiknl.nl", "123456", Role.GUEST, "01-01-1990", "Gast", "", "", "", "", "123456", ""),
            List.of("SysAdmin@users.com", "sysadmin", Role.MODERATOR, "16-10-1950", "Sys", "", "Admin", "Longway", "1000", "1111ZZ", "FarAwayTown"),
            List.of("DataAdmin@users.com", "dataAdmin", Role.ADMIN,"16-10-1900", "Data", "", "Boss", "Shortway", "1", "3333CC", "NearyTown"),
            List.of("customer@users.com", "customer", Role.CUSTOMER, "16-10-1900", "Customer", "", "User", "MiddelWay", "100", "2222AA", "AnyTown")
    );

    private void createUsers() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        for (List<Object> users: userList) {
            String email = (String) users.get(0);
            String password = (String) users.get(1);
            Role role = (Role) users.get(2);
            String dateOfBirthString = (String) users.get(3);

            Date dateOfBirth = null;

            if (!dateOfBirthString.isEmpty()) {
                try {
                    dateOfBirth = dateFormat.parse(dateOfBirthString);
                } catch (ParseException e) {
                    throw new IllegalArgumentException("Invalid date format: " + dateOfBirthString);
                }
            }

            String firstName = (String) users.get(4);
            String middleName = (String) users.get(5);
            String lastName = (String) users.get(6);
            String street = (String) users.get(7);
            String houseNr = (String) users.get(8);
            String postalCode = (String) users.get(9);
            String city = (String) users.get(10);

            firstName = firstName.isEmpty() ? null : firstName;
            middleName = middleName.isEmpty() ? null : middleName;
            lastName = lastName.isEmpty() ? null : lastName;
            street = street.isEmpty() ? null : street;
            houseNr = houseNr.isEmpty() ? null : houseNr;
            postalCode = postalCode.isEmpty() ? null : postalCode;
            city = city.isEmpty() ? null : city;

            User user = User.builder()
                    .email(email)
                    .password(passwordEncoder.encode(password))
                    .role(role)
                    .dateOfBirth(dateOfBirth)
                    .firstName(firstName)
                    .middleName(middleName)
                    .lastName(lastName)
                    .street(street)
                    .houseNr(houseNr)
                    .postalCode(postalCode)
                    .city(city)
                    .build();

            Optional<User> existingUser = userRepository.findByEmail(user.getEmail());

            if (existingUser.isEmpty()) {
                userRepository.save(user);
            }
        }
    }

    @Override
    public void run(String ...args) {
        createUsers();
    }
}
