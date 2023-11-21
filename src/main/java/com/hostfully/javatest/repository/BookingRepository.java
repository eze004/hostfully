package com.hostfully.javatest.repository;

import com.hostfully.javatest.domain.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long>, JpaSpecificationExecutor<Booking> {

    boolean existsByDateFromGreaterThanEqualAndDateFromLessThanEqualAndState(LocalDate bookingDateFrom, LocalDate bookingDateTo, Booking.State state);

    List<Booking> findByDateFromGreaterThanEqualAndDateFromLessThanEqualAndState(LocalDate bookingDateFrom, LocalDate bookingDateTo, Booking.State state);

    boolean existsByDateToGreaterThanEqualAndDateToLessThanEqualAndState(LocalDate bookingDateFrom, LocalDate bookingDateTo, Booking.State state);

    List<Booking> findByDateToGreaterThanEqualAndDateToLessThanEqualAndState(LocalDate bookingDateFrom, LocalDate bookingDateTo, Booking.State state);

    boolean existsByDateFromLessThanEqualAndDateToGreaterThanEqualAndState(LocalDate bookingDateFromAndTo1, LocalDate bookingDateFromAndTo2, Booking.State state);

    List<Booking> findByDateFromLessThanEqualAndDateToGreaterThanEqualAndState(LocalDate bookingDateFromAndTo1, LocalDate bookingDateFromAndTo2, Booking.State state);
}
