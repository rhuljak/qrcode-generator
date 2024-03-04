package de.ksbrwsk.qrcode.model;


import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;

@Getter
@Setter
@Validated
public class PublicDate {

    private LocalDateTime publicDate;

    private PublicDate() {
    }

    @Override
    public String toString() {
        return "PublicTime{" +
                "publicDate=" + publicDate +
                '}';
    }

    public PublicDate(LocalDateTime publicDate) {
        this.publicDate = publicDate;
    }
}
