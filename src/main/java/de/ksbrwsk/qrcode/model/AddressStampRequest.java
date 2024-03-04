package de.ksbrwsk.qrcode.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Validated
public class AddressStampRequest {

    @NotEmpty
    private List<AddressStamp> stampList = new ArrayList<>();


    public AddressStampRequest() {
    }
}
