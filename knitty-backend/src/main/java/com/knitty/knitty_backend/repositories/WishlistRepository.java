package com.knitty.knitty_backend.repositories;

import com.knitty.knitty_backend.models.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishlistRepository extends JpaRepository<Wishlist, Integer> {}
