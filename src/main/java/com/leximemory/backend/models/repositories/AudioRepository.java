package com.leximemory.backend.models.repositories;

import com.leximemory.backend.models.entities.Audio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * The interface Audio repository.
 */
@Repository
public interface AudioRepository extends JpaRepository<Audio, Integer> {

}
