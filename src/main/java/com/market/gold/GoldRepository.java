package com.market.gold;

import com.market.gold.model.Gold;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoldRepository extends JpaRepository<Gold, Long> {
}
