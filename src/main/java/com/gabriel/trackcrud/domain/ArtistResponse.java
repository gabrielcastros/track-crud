package com.gabriel.trackcrud.domain;

import java.time.OffsetDateTime;

public record ArtistResponse(
    Long id,
    String name,
    String country,
    ContentStatus status,
    OffsetDateTime createdAt,
    OffsetDateTime updatedAt,
    String createdBy,
    String updatedBy
) {}