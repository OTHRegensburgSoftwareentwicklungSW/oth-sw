package de.othr.bib48218.chat.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;

/**
 * A group chat consisting of multiple {@link User}s.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class GroupChat extends Chat {

    /**
     * The visibility.
     */
    @NonNull
    @lombok.NonNull
    private GroupVisibility visibility;

    /**
     * The public profile.
     */
    @OneToOne(
        cascade = CascadeType.ALL
        //fetch = FetchType.EAGER,
        //orphanRemoval = true
    )
    private ChatProfile profile = new ChatProfile();

    @Override
    public String toString() {
        if (profile == null) {
            return super.toString() + " (" + visibility + ")";
        } else {
            return profile.getName();
        }
    }
}
