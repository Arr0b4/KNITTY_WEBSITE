package com.knitty.knitty_backend.repositories;

import com.knitty.knitty_backend.models.Patron;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import java.util.List;
import java.util.Optional;

public interface PatronRepository extends JpaRepository<Patron, Integer> {
    @Override
    @EntityGraph(attributePaths = "categoria")
    List<Patron> findAll();

    @Override
    @EntityGraph(attributePaths = "categoria")
    Optional<Patron> findById(Integer id);
}
