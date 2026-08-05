package com.gabriel.trackcrud.service;

import com.gabriel.trackcrud.repository.ArtistRepository;
import com.gabriel.trackcrud.shared.error.NotFoundException;
import domain.Artist;
import domain.ArtistRequest;
import domain.ContentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ArtistService {

    private final ArtistRepository repository;

    public ArtistService(ArtistRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Artist create(ArtistRequest artistRequest) {
        return repository.save(Artist.create(artistRequest));
    }

    @Transactional
    public Artist update(Long id, ArtistRequest artistRequest) {
        Artist artist = findById(id);
        artist.update(artistRequest, "admin");
        return repository.save(artist);
    }

    public Page<Artist> list(String q, String country, Pageable pageable) {
        return repository.search(q, country, pageable);
    }

    public Artist findById(Long id) {
        return repository.
                findByIdAndStatus(id, ContentStatus.ACTIVE)
                .orElseThrow(() -> new NotFoundException("artist.error.notFound"));
    }

    @Transactional
    public void inactivateById(Long id) {
        Artist artist = findById(id);
        artist.inactivate("admin");
    }
}
