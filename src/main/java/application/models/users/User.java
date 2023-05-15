package application.models.users;

import application.enums.Role;
import com.sun.istack.NotNull;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Data
@Entity
@Builder
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue
    @Column(nullable = false, unique = true)
    private UUID id;

    @NotNull
    @Column(nullable = false, unique = true)
    private String email;

    @NotNull
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Role role;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "street")
    private String street;

    @Column(name = "house_number")
    private String houseNr;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "city")
    private String city;

    public List<GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();

        switch (role) {
            case ADMIN:
                authorities.add(new SimpleGrantedAuthority(Role.ADMIN.toString()));
                authorities.add(new SimpleGrantedAuthority(Role.MODERATOR.toString()));
                authorities.add(new SimpleGrantedAuthority(Role.CUSTOMER.toString()));
                authorities.add(new SimpleGrantedAuthority(Role.GUEST.toString()));
                break;
            case MODERATOR:
                authorities.add(new SimpleGrantedAuthority(Role.MODERATOR.toString()));
                authorities.add(new SimpleGrantedAuthority(Role.CUSTOMER.toString()));
                authorities.add(new SimpleGrantedAuthority(Role.GUEST.toString()));
                break;
            case CUSTOMER:
                authorities.add(new SimpleGrantedAuthority(Role.CUSTOMER.toString()));
                authorities.add(new SimpleGrantedAuthority(Role.GUEST.toString()));
                break;
            case GUEST:
                authorities.add(new SimpleGrantedAuthority(Role.GUEST.toString()));
                break;
        }

        return authorities;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
