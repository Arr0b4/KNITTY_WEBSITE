package com.knitty.knitty_backend.repositories;

import com.knitty.knitty_backend.models.Publicacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublicacionRepository extends JpaRepository<Publicacion, Integer> {}
