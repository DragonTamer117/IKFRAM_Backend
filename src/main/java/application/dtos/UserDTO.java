package application.dtos;

import application.enums.Role;
import lombok.Data;

import java.util.Date;

@Data
public class UserDTO {
    private String email;
    private String password;
    private Role role;
    private String firstName;
    private String middleName;
    private String lastName;
    private Date dateOfBirth;
    private String street;
    private String houseNr;
    private String postalCode;
    private String city;
}
