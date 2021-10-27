package com.market.repository;

import com.market.model.Gold;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface GoldRepository extends JpaRepository<Gold, Long> {
    Optional<List<Gold>> findByDate(LocalDate date);
}
