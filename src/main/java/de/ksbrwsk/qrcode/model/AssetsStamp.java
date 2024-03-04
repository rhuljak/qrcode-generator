package de.ksbrwsk.qrcode.model;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Validated
public class AssetsStamp {

    @NotBlank
    private String id;
    @NotBlank
    private String title;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public AssetsStamp() {
    }

    public AssetsStamp(String id, String title) {
        this.id = id;
        this.title = title;
    }

    @Override
    public String toString() {
        return "AssetsStamp{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
