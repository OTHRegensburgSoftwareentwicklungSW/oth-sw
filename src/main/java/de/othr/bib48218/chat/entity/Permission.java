package de.othr.bib48218.chat.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Permission extends IdEntity {
    @NonNull
    @NotBlank
    @lombok.NonNull
    private String name;

    @OneToMany(
        mappedBy = "permission",
        cascade = CascadeType.ALL)
    private Set<UserPermission> userPermissions = Collections.emptySet();
}
