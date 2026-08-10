package domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Entity
@Table(name = "artist")
@Getter
@Setter
public class Artist extends DefaultEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, length = 128)
    private String country;

    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private ContentStatus status;

    protected Artist() {}

    public static Artist create(ArtistRequest request) {
        Artist artist = new Artist();
        artist.status = ContentStatus.ACTIVE;
        artist.apply(request);
        artist.setCreatedAt(OffsetDateTime.now());
        artist.setCreatedBy("admin");
        return artist;
    }

    public void update(ArtistRequest request, String updatedBy) {
        apply(request);
        assignUpdatedBy(updatedBy);
    }

    public void apply(ArtistRequest request) {
        this.name = normalize(request.name());
        this.country = request.country();
    }

    public void inactivate(String updatedBy) {
        this.setStatus(ContentStatus.INACTIVE);
        assignUpdatedBy(updatedBy);
    }

    protected void assignUpdatedBy(String updatedBy) {
        this.setUpdatedBy(updatedBy);
        this.setUpdatedAt(OffsetDateTime.now());
    }

    private static String normalize(String value) {
        return value == null ? null : value.trim().replaceAll("\\s+", " ");
    }
}
