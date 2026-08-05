package com.gabriel.trackcrud.repository;

import domain.Artist;
import domain.ContentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ArtistRepository extends JpaRepository<Artist, Long> {

    @Query("""
        SELECT a FROM Artist a
            WHERE a.status = ContentStatus.ACTIVE
                AND LOWER(a.name) LIKE LOWER(CONCAT('%', :q, '%'))
                AND LOWER(a.country) LIKE LOWER(CONCAT('%', :country, '%'))
        """)
    Page<Artist> search(
            @Param("q") String q,
            @Param("country") String country,
            Pageable pageable);

    Optional<Artist> findByIdAndStatus(Long id, ContentStatus status);
}
