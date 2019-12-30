package com.sesh.demo.persistence.repos;

import com.sesh.demo.persistence.models.Gift;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface GiftRepository extends CrudRepository<Gift, Long> {
    Optional<List<Gift>> findByName(String name);
}
