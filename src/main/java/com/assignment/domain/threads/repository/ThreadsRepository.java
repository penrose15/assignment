package com.assignment.domain.threads.repository;

import com.assignment.domain.threads.domain.Threads;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThreadsRepository extends JpaRepository<Threads, Long> {
}
