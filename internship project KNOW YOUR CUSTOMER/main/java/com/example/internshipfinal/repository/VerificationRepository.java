package com.example.internshipfinal.repository;

import com.example.internshipfinal.model.Verification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface VerificationRepository extends JpaRepository<Verification, Long> {

}
