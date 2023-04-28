package bikeShop.shopUser.shopUserModels;


public class ShopUserView {
    private Long shopUserId;
    private String shopUserEmail;
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

    public ShopUserView(Long shopUserId, String shopUserEmail,
                        ShopUserRole shopUserRole, Integer yearOfBirth,
                        Integer monthOfBirth, Integer dayOfBirth,
                        String firstName, String middleName,
                        String lastName, String street,
                        String houseNr, String postalCode,
                        String city) {
        this.shopUserId = shopUserId;
        this.shopUserEmail = shopUserEmail;
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

    public void setShopUserEmail(String shopUserEmail) {
        this.shopUserEmail = shopUserEmail;
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
}
