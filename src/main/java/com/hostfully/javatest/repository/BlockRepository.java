package com.hostfully.javatest.repository;

import com.hostfully.javatest.domain.Block;
import com.hostfully.javatest.domain.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BlockRepository extends JpaRepository<Block, Long>, JpaSpecificationExecutor<Block> {

    boolean existsByDateFromGreaterThanEqualAndDateFromLessThanEqual(LocalDate bookingDateFrom, LocalDate bookingDateTo);

    List<Booking> findByDateFromGreaterThanEqualAndDateFromLessThanEqual(LocalDate bookingDateFrom, LocalDate bookingDateTo);

    boolean existsByDateToGreaterThanEqualAndDateToLessThanEqual(LocalDate bookingDateFrom, LocalDate bookingDateTo);

    List<Booking> findByDateToGreaterThanEqualAndDateToLessThanEqual(LocalDate bookingDateFrom, LocalDate bookingDateTo);

    boolean existsByDateFromLessThanEqualAndDateToGreaterThanEqual(LocalDate bookingDateFromAndTo1, LocalDate bookingDateFromAndTo2);

    List<Booking> findByDateFromLessThanEqualAndDateToGreaterThanEqual(LocalDate bookingDateFromAndTo1, LocalDate bookingDateFromAndTo2);
}
