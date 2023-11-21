package com.hostfully.javatest.controller;

import com.hostfully.javatest.domain.Booking;
import com.hostfully.javatest.repository.BookingRepository;
import com.hostfully.javatest.service.BookingService;
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
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    private final BookingRepository bookingRepository;

    public BookingController(BookingService bookingService, BookingRepository bookingRepository) {
        this.bookingService = bookingService;
        this.bookingRepository = bookingRepository;
    }

    @PostMapping
    public ResponseEntity<Booking> createBooking(@Valid @RequestBody Booking booking) throws URISyntaxException {
        if (booking.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A new block cannot already have an ID");
        }

        Booking result = bookingService.save(booking);

        if (result == null) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "The booking can't be scheduled in that date range");
        }

        return ResponseEntity
                .created(new URI("/api/bookings/" + result.getId()))
                .body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Booking> updateBooking(
            @PathVariable(value = "id", required = false) final Long id,
            @Valid @RequestBody Booking booking
    ) throws URISyntaxException {
        if (booking.getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid id");
        }
        if (!Objects.equals(id, booking.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid id");
        }
        if (!bookingRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Entity not found");
        }

        Booking result = bookingService.update(booking);
        if (result == null) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "The booking can't be scheduled in that date range");
        }

        return ResponseEntity
                .ok()
                .body(result);
    }

    @PatchMapping(value = "/{id}", consumes = {"application/json", "application/merge-patch+json"})
    public ResponseEntity<Booking> partialUpdateBooking(
            @PathVariable(value = "id", required = false) final Long id,
            @NotNull @RequestBody Booking booking
    ) throws URISyntaxException {
        if (booking.getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid id");
        }
        if (!Objects.equals(id, booking.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid id");
        }
        if (!bookingRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Entity not found");
        }

        Optional<Booking> result = bookingService.partialUpdate(booking);

        return result
                .map((response) -> ResponseEntity.ok().body(response))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "The booking can't be scheduled in that date range"));
    }

    @GetMapping
    public ResponseEntity<List<Booking>> getAllBookings() {
        List<Booking> list = bookingService.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countBookings() {
        return ResponseEntity.ok().body(bookingService.count());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBooking(@PathVariable Long id) {
        Optional<Booking> booking = bookingService.findOne(id);
        return booking
                .map((response) -> ResponseEntity.ok().body(response))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
        bookingService.delete(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}
