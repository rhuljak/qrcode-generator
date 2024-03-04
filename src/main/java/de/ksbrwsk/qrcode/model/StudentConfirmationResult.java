package de.ksbrwsk.qrcode.model;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Validated
public class StudentConfirmationResult {


    private String registerNumber;
    private Address address;
    private String printedDate;
    private String studentName;
    private String dateBirth;
    private String sex;
    private Float normalizeSum;
    private Float jpzMat;
    private Float jpzCj;
    private Float motivation;
    private Float interview;
    private Float creative;
    private Float offSchool;
    private Float reportCard;
    private String viewed;
    private int order;
    // true - splneno
    // false - nesplneno
    private Boolean cermatCondition;

    // true - splneno
    // false - nesplneno
    private Boolean gpnCondition;

    // 1 - prijat
    // 2 - neprijat - kapacita
    // 3 - neprijat
    private int admitted;
    private Boolean p4;

    public StudentConfirmationResult(String registerNumber, Address address, String printedDate, String studentName, String dateBirth, String sex, Float normalizeSum, Float jpzMat, Float jpzCj, Float motivation, Float interview, Float creative, Float offSchool, Float reportCard, String viewed, int order, Boolean cermatCondition, Boolean gpnCondition, int admitted, Boolean p4) {
        this.registerNumber = registerNumber;
        this.address = address;
        this.printedDate = printedDate;
        this.studentName = studentName;
        this.dateBirth = dateBirth;
        this.sex = sex;
        this.normalizeSum = normalizeSum;
        this.jpzMat = jpzMat;
        this.jpzCj = jpzCj;
        this.motivation = motivation;
        this.interview = interview;
        this.creative = creative;
        this.offSchool = offSchool;
        this.reportCard = reportCard;
        this.viewed = viewed;
        this.order = order;
        this.cermatCondition = cermatCondition;
        this.gpnCondition = gpnCondition;
        this.admitted = admitted;
        this.p4 = p4;
    }

    @Override
    public String toString() {
        return "StudentConfirmationResult{" +
                "registerNumber='" + registerNumber + '\'' +
                ", address=" + address +
                ", printedDate='" + printedDate + '\'' +
                ", studentName='" + studentName + '\'' +
                ", dateBirth='" + dateBirth + '\'' +
                ", sex='" + sex + '\'' +
                ", normalizeSum=" + normalizeSum +
                ", jpzMat=" + jpzMat +
                ", jpzCj=" + jpzCj +
                ", motivation=" + motivation +
                ", interview=" + interview +
                ", creative=" + creative +
                ", offSchool=" + offSchool +
                ", reportCard=" + reportCard +
                ", viewed='" + viewed + '\'' +
                ", order=" + order +
                ", cermatCondition=" + cermatCondition +
                ", gpnCondition=" + gpnCondition +
                ", admitted=" + admitted +
                ", p4=" + p4 +
                '}';
    }

    @Getter
    @Setter
    public static class Address {
        private String parentTitle1;
        @NotBlank
        private String parentName;
        @NotBlank
        private String parentLastName;
        private String parentTitle2;
        private String street;
        private String streetNumber1;
        private String streetNumber2;
        @NotBlank
        private String city;
        private String cityPart;
        @NotBlank
        private String zipCode;

        public Address(String parentTitle1, String parentName, String parentLastName, String parentTitle2, String street, String streetNumber1, String streetNumber2, String city, String cityPart, String zipCode) {
            this.parentTitle1 = parentTitle1;
            this.parentName = parentName;
            this.parentLastName = parentLastName;
            this.parentTitle2 = parentTitle2;
            this.street = street;
            this.streetNumber1 = streetNumber1;
            this.streetNumber2 = streetNumber2;
            this.city = city;
            this.cityPart = cityPart;
            this.zipCode = zipCode;
        }

        @Override
        public String toString() {
            return "Address{" +
                    "parentTitle1='" + parentTitle1 + '\'' +
                    ", parentName='" + parentName + '\'' +
                    ", parentLastName='" + parentLastName + '\'' +
                    ", parentTitle2='" + parentTitle2 + '\'' +
                    ", street='" + street + '\'' +
                    ", streetNumber1='" + streetNumber1 + '\'' +
                    ", streetNumber2='" + streetNumber2 + '\'' +
                    ", city='" + city + '\'' +
                    ", cityPart='" + cityPart + '\'' +
                    ", zipCode='" + zipCode + '\'' +
                    '}';
        }
    }


}
