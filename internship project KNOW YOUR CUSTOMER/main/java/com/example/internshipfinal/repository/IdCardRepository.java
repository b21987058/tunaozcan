package com.example.internshipfinal.repository;

import com.example.internshipfinal.model.IdCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IdCardRepository extends JpaRepository<IdCard, Long> {

}
