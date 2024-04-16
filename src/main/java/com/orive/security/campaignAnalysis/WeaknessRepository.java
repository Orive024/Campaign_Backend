package com.orive.security.campaignAnalysis;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeaknessRepository extends JpaRepository<Weakness, Long> {
}

