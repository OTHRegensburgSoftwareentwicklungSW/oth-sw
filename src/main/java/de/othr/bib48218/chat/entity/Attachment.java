package de.othr.bib48218.chat.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;

@Entity
@Getter
@Setter
@NoArgsConstructor(force = true)
public class Attachment extends IdEntity {
    @NonNull
    @lombok.NonNull
    @NotBlank
    private String name;

    @NonNull
    @lombok.NonNull
    @NotBlank
    private String mimeType;

    @NonNull
    @OneToOne(mappedBy = "attachment")
    private Message message;

    public Attachment(@lombok.NonNull @NonNull String name, @lombok.NonNull @NonNull String mimeType) {
        this.name = name;
        this.mimeType = mimeType;
    }
}
