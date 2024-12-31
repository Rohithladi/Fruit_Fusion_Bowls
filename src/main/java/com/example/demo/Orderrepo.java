package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface Orderrepo extends JpaRepository<Orders, Long> {
}
