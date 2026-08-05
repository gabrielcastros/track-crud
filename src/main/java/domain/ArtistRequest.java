package domain;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ArtistRequest(
        @NotNull @Size(max = 255) String name,
        @NotNull @Size(max = 128) String country
){}
