package com.hostfully.javatest.service;

import com.hostfully.javatest.domain.Block;
import com.hostfully.javatest.repository.BlockRepository;
import com.hostfully.javatest.repository.BookingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.hostfully.javatest.domain.Booking.State.BOOKED;

@Service
@Transactional
public class BlockService {

    private final BlockRepository blockRepository;
    private final BookingRepository bookingRepository;

    public BlockService(
            BlockRepository blockRepository,
            BookingRepository bookingRepository
    ) {
        this.blockRepository = blockRepository;
        this.bookingRepository = bookingRepository;
    }

    public Block save(Block block) {
        if (!isBlockValid(block)) {
            return null;
        }

        block = blockRepository.save(block);
        return block;
    }

    public Block update(Block block) {
        if (!isBlockValid(block)) {
            return null;
        }

        block = blockRepository.save(block);
        return block;
    }

    public Optional<Block> partialUpdate(Block block) {
        return blockRepository
                .findById(block.getId())
                .map(existingBlock -> {
                    if (block.getDateFrom() != null) {
                        existingBlock.setDateFrom(block.getDateFrom());
                    }
                    if (block.getDateTo() != null) {
                        existingBlock.setDateTo(block.getDateTo());
                    }
                    if (block.getManager() != null) {
                        existingBlock.setManager(block.getManager());
                    }

                    return existingBlock;
                })
                .map(newBlock -> {
                    if (!isBlockValid(block)) {
                        return null;
                    }
                    return blockRepository.save(newBlock);
                });
    }

    @Transactional(readOnly = true)
    public List<Block> findAll() {
        return blockRepository.findAll();
    }

    @Transactional(readOnly = true)
    public long count() {
        return blockRepository.count();
    }

    @Transactional(readOnly = true)
    public Optional<Block> findOne(Long id) {
        return blockRepository.findById(id);
    }

    public void delete(Long id) {
        blockRepository.deleteById(id);
    }

    public boolean isBlockValid(Block block) {
        return !hasBookingConflicts(block);
    }

    public boolean hasBookingConflicts(Block block) {
        return bookingRepository.existsByDateFromGreaterThanEqualAndDateFromLessThanEqualAndState(block.getDateFrom(), block.getDateTo(), BOOKED)
                || bookingRepository.existsByDateToGreaterThanEqualAndDateToLessThanEqualAndState(block.getDateFrom(), block.getDateTo(), BOOKED)
                || bookingRepository.existsByDateFromLessThanEqualAndDateToGreaterThanEqualAndState(block.getDateTo(), block.getDateTo(), BOOKED)
                || bookingRepository.existsByDateFromLessThanEqualAndDateToGreaterThanEqualAndState(block.getDateFrom(), block.getDateFrom(), BOOKED);
    }
}
