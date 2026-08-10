package com.gabriel.trackcrud.artist;

import domain.Artist;
import domain.ArtistRequest;
import domain.ContentStatus;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ArtistTest {

    private static ArtistRequest minimal(String name, String country) {
        return new ArtistRequest(name, country);
    }

    @Test
    void newArtistCreatesAsACTIVE() {
        Artist artist = Artist.create(minimal("name", "country"));
        assertThat(artist.getStatus()).isEqualTo(ContentStatus.ACTIVE);
    }

    @Test
    void inactivateChangeStatusToInactiveAndSetUpdatedBy() {
        Artist artist = Artist.create(minimal("name", "country"));
        artist.inactivate("u1");
        assertThat(artist.getStatus()).isEqualTo(ContentStatus.INACTIVE);
        assertThat(artist.getUpdatedBy()).isEqualTo("u1");
    }

    @Test
    void shouldNormalizeStringFields() {
        Artist artist = Artist.create(minimal(" Bob  Marley  ", "country"));
        assertThat(artist.getName()).isEqualTo("Bob Marley");
    }
}
