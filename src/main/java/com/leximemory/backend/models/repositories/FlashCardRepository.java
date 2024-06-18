package com.leximemory.backend.models.repositories;

import com.leximemory.backend.models.entities.FlashCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Flash card repository.
 */
@Repository
public interface FlashCardRepository extends JpaRepository<FlashCard, Integer> {

}
