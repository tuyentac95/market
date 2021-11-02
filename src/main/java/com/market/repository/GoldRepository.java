package com.market.repository;

import com.market.model.Gold;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface GoldRepository extends JpaRepository<Gold, Long> {
    Optional<List<Gold>> findByDate(LocalDate date);

    @Query("select g from Gold g where g.name = :name and g.groupName = :groupName " +
            "and g.date between :from and :to order by g.date")
    Optional<List<Gold>> findByType(
            @Param("name") String name,
            @Param("groupName") String groupName,
            @Param("from") LocalDate from,
            @Param("to") LocalDate to
    );
}
