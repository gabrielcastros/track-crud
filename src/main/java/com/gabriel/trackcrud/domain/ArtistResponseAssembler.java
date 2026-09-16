package com.gabriel.trackcrud.domain;

import org.springframework.stereotype.Component;

@Component
public class ArtistResponseAssembler {

    public ArtistResponse toResponse(Artist artist) {
        return new ArtistResponse(artist.getId(),
                artist.getName(),
                artist.getCountry(),
                artist.getStatus(),
                artist.getCreatedAt(),
                artist.getUpdatedAt(),
                artist.getCreatedBy(),
                artist.getUpdatedBy());
    }
}
