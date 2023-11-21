package com.hostfully.javatest.controller;

import com.hostfully.javatest.domain.Block;
import com.hostfully.javatest.repository.BlockRepository;
import com.hostfully.javatest.service.BlockService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/blocks")
public class BlockController {

    private final BlockService blockService;

    private final BlockRepository blockRepository;

    public BlockController(BlockService blockService, BlockRepository blockRepository) {
        this.blockService = blockService;
        this.blockRepository = blockRepository;
    }

    @PostMapping
    public ResponseEntity<Block> createBlock(@Valid @RequestBody Block block) throws URISyntaxException {
        if (block.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A new block cannot already have an ID");
        }
        Block result = blockService.save(block);

        if (result == null) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "The block can't be scheduled in that date range");
        }

        return ResponseEntity
                .created(new URI("/api/blocks/" + result.getId()))
                .body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Block> updateBlock(
            @PathVariable final Long id,
            @Valid @RequestBody Block block
    ) throws URISyntaxException {
        if (block.getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid id");
        }
        if (!Objects.equals(id, block.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid id");
        }
        if (!blockRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Entity not found");
        }

        Block result = blockService.update(block);
        if (result == null) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "The block can't be scheduled in that date range");
        }

        return ResponseEntity
                .ok()
                .body(result);
    }

    @PatchMapping(value = "/{id}", consumes = {"application/json", "application/merge-patch+json"})
    public ResponseEntity<Block> partialUpdateBlock(
            @PathVariable final Long id,
            @NotNull @RequestBody Block block
    ) throws URISyntaxException {
        if (block.getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid id");
        }
        if (!Objects.equals(id, block.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid id");
        }
        if (!blockRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Entity not found");
        }

        Optional<Block> result = blockService.partialUpdate(block);

        return result
                .map((response) -> ResponseEntity.ok().body(response))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "The block can't be scheduled in that date range"));
    }

    @GetMapping
    public ResponseEntity<List<Block>> getAllBlocks() {
        List<Block> list = blockService.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countBlocks() {
        return ResponseEntity.ok().body(blockService.count());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Block> getBlock(@PathVariable Long id) {
        Optional<Block> block = blockService.findOne(id);
        return block
                .map((response) -> ResponseEntity.ok().body(response))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBlock(@PathVariable Long id) {
        blockService.delete(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}
