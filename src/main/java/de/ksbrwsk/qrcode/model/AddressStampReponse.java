package de.ksbrwsk.qrcode.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressStampReponse {


    private String url;

    public AddressStampReponse() {
    }

    public AddressStampReponse(String url) {
        this.url = url;
    }
}
