package com.example.repository;

import com.example.entity.EntryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntryRepository extends JpaRepository<EntryEntity, Long> {
}
