package com.knitty.knitty_backend.services;

import com.knitty.knitty_backend.models.Patron;
import com.knitty.knitty_backend.repositories.PatronRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class PatronService {
    private final PatronRepository repository;
    public PatronService(PatronRepository repository) { this.repository = repository; }
    @Transactional(readOnly = true)
    public List<Patron> listarInternamente() { return repository.findAll(); }
    @Transactional(readOnly = true)
    public Optional<Patron> buscarInternamente(Integer id) { return repository.findById(id); }
}
