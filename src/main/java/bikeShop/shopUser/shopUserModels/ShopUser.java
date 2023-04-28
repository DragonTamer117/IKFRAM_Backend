package bikeShop.shopUser.shopUserModels;

import com.sun.istack.NotNull;
import org.springframework.security.crypto.bcrypt.BCrypt;

import javax.persistence.*;
import java.io.Serializable;


@Entity
public class ShopUser implements Serializable {
    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    private Long shopUserId;
    @NotNull
    @Column(unique = true,name = "shop_user_email")
    private String shopUserEmail;
    @NotNull
    private String password;
    // not in constructor
    private String passwordSalt;
    private ShopUserRole shopUserRole;
    private Integer yearOfBirth;
    private Integer monthOfBirth;
    private Integer dayOfBirth;
    private String firstName;
    private String middleName;
    private String lastName;
    private String street;
    private String houseNr;
    private String postalCode;
    private String city;


    public ShopUser() {
    }

    public ShopUser(String shopUserEmail, String password,
                    ShopUserRole shopUserRole, Integer yearOfBirth,
                    Integer monthOfBirth,Integer dayOfBirth,
                    String firstName, String middleName,
                    String lastName, String street,
                    String houseNr, String postalCode,
                    String city) {
        this.shopUserEmail = shopUserEmail;
        this.password = password;
        this.shopUserRole = shopUserRole;
        this.yearOfBirth = yearOfBirth;
        this.monthOfBirth = monthOfBirth;
        this.dayOfBirth = dayOfBirth;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.street = street;
        this.houseNr = houseNr;
        this.postalCode = postalCode;
        this.city = city;
    }

    public ShopUser(Long shopUserId, String shopUserEmail,
                    String password, ShopUserRole shopUserRole,
                    Integer yearOfBirth, Integer monthOfBirth,
                    Integer dayOfBirth, String firstName,
                    String middleName, String lastName,
                    String street, String houseNr,
                    String postalCode, String city) {
        this.shopUserId = shopUserId;
        this.shopUserEmail = shopUserEmail;
        this.password = password;
        this.shopUserRole = shopUserRole;
        this.yearOfBirth = yearOfBirth;
        this.monthOfBirth = monthOfBirth;
        this.dayOfBirth = dayOfBirth;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.street = street;
        this.houseNr = houseNr;
        this.postalCode = postalCode;
        this.city = city;
    }

    public Long getShopUserId() {
        return shopUserId;
    }

    public void setShopUserId(Long shopUserId) {
        this.shopUserId = shopUserId;
    }

    public String getShopUserEmail() {
        return shopUserEmail;
    }

    public void seteShopUserEmail(String eMail) {
        this.shopUserEmail = eMail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ShopUserRole getShopUserRole() {
        return shopUserRole;
    }

    public void setShopUserRole(ShopUserRole shopUserRole) {
        this.shopUserRole = shopUserRole;
    }

    public Integer getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(Integer yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public Integer getMonthOfBirth() {
        return monthOfBirth;
    }

    public void setMonthOfBirth(Integer monthOfBirth) {
        this.monthOfBirth = monthOfBirth;
    }

    public Integer getDayOfBirth() {
        return dayOfBirth;
    }

    public void setDayOfBirth(Integer dayOfBirth) {
        this.dayOfBirth = dayOfBirth;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNr() {
        return houseNr;
    }

    public void setHouseNr(String houseNr) {
        this.houseNr = houseNr;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "ShopUser{" +
                "shopUserId=" + shopUserId +
                ", shopUserEmail='" + shopUserEmail + '\'' +
                ", password='" + password + '\'' +
                ", shopUserRole=" + shopUserRole +
                ", yearOfBirth=" + yearOfBirth +
                ", monthOfBirth=" + monthOfBirth +
                ", dayOfBirth=" + dayOfBirth +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", street='" + street + '\'' +
                ", houseNr='" + houseNr + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", city='" + city + '\'' +
                '}';
    }

    public void setSaltedPassword() {
        this.passwordSalt = BCrypt.gensalt(10);
        String unSaltedPassword = this.password;
        this.password = BCrypt.hashpw(unSaltedPassword,this.passwordSalt);
    }

    public boolean passwordCorrect(String passToBeChecked){
        return BCrypt.checkpw(passToBeChecked, this.password);
    }

}