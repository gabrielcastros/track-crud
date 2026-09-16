package com.gabriel.trackcrud.controller;

import com.gabriel.trackcrud.service.ArtistService;
import com.gabriel.trackcrud.domain.Artist;
import com.gabriel.trackcrud.domain.ArtistRequest;
import com.gabriel.trackcrud.domain.ArtistResponse;
import com.gabriel.trackcrud.domain.ArtistResponseAssembler;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/artists")
public class ArtistController {

    private final ArtistService service;
    private final ArtistResponseAssembler assembler;

    public ArtistController(ArtistService service, ArtistResponseAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @PostMapping
    public ResponseEntity<ArtistResponse> create(@Valid @RequestBody ArtistRequest artistRequest) {
        Artist artist = service.create(artistRequest);
        return ResponseEntity.created(URI.create("/api/artists/" + artist.getId())).body(assembler.toResponse(artist));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArtistResponse> update(@PathVariable Long id, @Valid @RequestBody ArtistRequest artistRequest) {
        return ResponseEntity.ok(assembler.toResponse(service.update(id, artistRequest)));
    }

    @GetMapping
    public ResponseEntity<Page<ArtistResponse>> list(@RequestParam(defaultValue = "") String q,
                                                     @RequestParam(defaultValue = "") String country,
                                                     @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(service.list(q, country, pageable).map(assembler::toResponse));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArtistResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(assembler.toResponse(service.findById(id)));
    }

    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void inactivate(@PathVariable Long id) {
        service.inactivateById(id);
    }
}
