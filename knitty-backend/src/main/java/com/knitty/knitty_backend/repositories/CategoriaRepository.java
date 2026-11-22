package com.knitty.knitty_backend.repositories;

import com.knitty.knitty_backend.models.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {}
