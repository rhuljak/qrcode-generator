package de.ksbrwsk.qrcode.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter

public class Stamp {

    @NotEmpty
    private String urlToBeEncoded;
    @NotEmpty
    private String text;

    public Stamp() {
    }

    public Stamp(String urlToBeEncoded) {
        this.urlToBeEncoded = urlToBeEncoded;
    }

    public Stamp(String urlToBeEncoded, String text) {
        this.urlToBeEncoded = urlToBeEncoded;
        this.text = text;
    }
}
