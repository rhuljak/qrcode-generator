package de.ksbrwsk.qrcode.model;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Validated
public class FolderStamp {

    @NotBlank
    private String regNumber;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String birthDate;


    public FolderStamp() {
    }

    public FolderStamp(String regNumber, String firstName, String lastName, String birthDate) {
        this.regNumber = regNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        return "FolderStamp{" +
                "regNumber='" + regNumber + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDate='" + birthDate + '\'' +
                '}';
    }
}
