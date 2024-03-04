package de.ksbrwsk.qrcode.model;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Validated
public class AddressStamp {

    private String title1;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;

    private String title2;
    private String street;
    private String number1;
    private String number2;
    @NotBlank
    private String city;
    private String cityPart;
    @NotBlank
    private String zip;


    public AddressStamp() {
    }

    public AddressStamp(String firstName, String lastName, String city, String zip) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
        this.zip = zip;
    }

    public AddressStamp(String firstName, String lastName, String street, String number1, String number2, String city, String cityPart, String zip) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.street = street;
        this.number1 = number1;
        this.number2 = number2;
        this.city = city;
        this.cityPart = cityPart;
        this.zip = zip;
    }

    public AddressStamp(String title1, String firstName, String lastName, String title2, String street, String number1, String number2, String city, String cityPart, String zip) {
        this.title1 = title1;
        this.firstName = firstName;
        this.lastName = lastName;
        this.title2 = title2;
        this.street = street;
        this.number1 = number1;
        this.number2 = number2;
        this.city = city;
        this.cityPart = cityPart;
        this.zip = zip;
    }

    @Override
    public String toString() {
        return "AddressStamp{" +
                "title1='" + title1 + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", title2='" + title2 + '\'' +
                ", street='" + street + '\'' +
                ", number1='" + number1 + '\'' +
                ", number2='" + number2 + '\'' +
                ", city='" + city + '\'' +
                ", cityPart='" + cityPart + '\'' +
                ", zip='" + zip + '\'' +
                '}';
    }
}
