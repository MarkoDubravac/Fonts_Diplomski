package com.mdubravac.fonts.repositories;

import com.mdubravac.fonts.entities.ResponseTest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResponseRepository extends JpaRepository<ResponseTest, Long> {
}
