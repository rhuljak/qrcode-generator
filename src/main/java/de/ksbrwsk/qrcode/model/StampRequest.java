package de.ksbrwsk.qrcode.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter

public class StampRequest {


    private List<Stamp> stampList = new ArrayList<>();


    public StampRequest() {
    }
}
