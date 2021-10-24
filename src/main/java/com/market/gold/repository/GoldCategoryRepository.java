package com.market.gold.repository;

import com.market.gold.model.GoldCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GoldCategoryRepository extends JpaRepository<GoldCategory, Long> {

    @Query(value = "select distinct g.id, g.name, g.group_name from Gold g where g.date = '2021-09-10'", nativeQuery = true)
    Optional<List<GoldCategory>> getAllCategories();
}
