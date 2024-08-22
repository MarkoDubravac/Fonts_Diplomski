package com.mdubravac.fonts.repositories;

import com.mdubravac.fonts.entities.TextCollection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TextCollectionRepository extends JpaRepository<TextCollection, Long> {
    TextCollection findByName(String name);
}
