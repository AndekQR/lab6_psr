package model;

public class Address {

    private String street;
    private Integer houseNumber;
    private String postalCode;

    public Address() {
    }

    public Address(String street, Integer houseNumber, String postalCode) {
        this.street=street;
        this.houseNumber=houseNumber;
        this.postalCode=postalCode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street=street;
    }

    public Integer getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(Integer houseNumber) {
        this.houseNumber=houseNumber;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode=postalCode;
    }

    @Override
    public String toString() {
        return "Address{" +
                "street='" + street + '\'' +
                ", houseNumber=" + houseNumber +
                ", postalCode='" + postalCode + '\'' +
                '}';
    }
}
