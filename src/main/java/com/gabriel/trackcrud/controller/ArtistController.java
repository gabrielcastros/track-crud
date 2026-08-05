package com.gabriel.trackcrud.controller;

import com.gabriel.trackcrud.service.ArtistService;
import domain.Artist;
import domain.ArtistRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/artists")
public class ArtistController {

    private final ArtistService service;

    public ArtistController(ArtistService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Artist create(@Valid @RequestBody ArtistRequest artistRequest) {
        return service.create(artistRequest);
    }

    @PutMapping("/{id}")
    public Artist update(@PathVariable Long id, @Valid @RequestBody ArtistRequest artistRequest) { return service.update(id, artistRequest); }

    @GetMapping
    public Page<Artist> list(@RequestParam(defaultValue = "") String q,
                             @RequestParam(defaultValue = "") String country,
                             @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return service.list(q, country, pageable);
    }

    @GetMapping("/{id}")
    public Artist findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @PostMapping("/{id}/inactivate")
    public void inactivate(@PathVariable Long id) {
        service.inactivateById(id);
    }
}
