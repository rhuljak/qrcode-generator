package de.ksbrwsk.qrcode.model;


import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Validated
public class StudentResult {

    private int order;
    private String studentName;
    private String registerNumber;
    private Float jpzMat;
    private Float jpzCj;

    // true - splneno
    // false - nesplneno
    private Boolean cermatCondition;
    private Float motivation;
    private Float interview;
    private Float creative;
    private Float offSchool;

    // true - splneno
    // false - nesplneno
    private Boolean gpnCondition;
    private Float reportCard;
    private Float sum;

    private Float normalizeSum;

    // 1 - prijat
    // 2 - neprijat - kapacita
    // 3 - neprijat
    private int admitted;


    private StudentResult() {
    }

    public StudentResult(int order, String studentName, String registerNumber, Float jpzMat, Float jpzCj, Boolean cermatCondition, Float motivation, Float interview, Float creative, Float offSchool, Boolean gpnCondition, Float reportCard, Float sum, Float normalizeSum, int admitted) {
        this.order = order;
        this.studentName = studentName;
        this.registerNumber = registerNumber;
        this.jpzMat = jpzMat;
        this.jpzCj = jpzCj;
        this.cermatCondition = cermatCondition;
        this.motivation = motivation;
        this.interview = interview;
        this.creative = creative;
        this.offSchool = offSchool;
        this.gpnCondition = gpnCondition;
        this.reportCard = reportCard;
        this.sum = sum;
        this.normalizeSum = normalizeSum;
        this.admitted = admitted;
    }

    @Override
    public String toString() {
        return "StudentResult{" +
                "order=" + order +
                ", studentName='" + studentName + '\'' +
                ", registerNumber='" + registerNumber + '\'' +
                ", jpzMat=" + jpzMat +
                ", jpzCj=" + jpzCj +
                ", cermatCondition=" + cermatCondition +
                ", motivation=" + motivation +
                ", interview=" + interview +
                ", creative=" + creative +
                ", offSchool=" + offSchool +
                ", gpnCondition=" + gpnCondition +
                ", reportCard=" + reportCard +
                ", sum=" + sum +
                ", normalizeSum=" + normalizeSum +
                ", admitted=" + admitted +
                '}';
    }
}
