package com.example.internshipfinal.repository;

import com.example.internshipfinal.model.Face;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FaceRepository extends JpaRepository<Face, Long> {

}
