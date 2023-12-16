package tn.uma.boutiti.bouzidi.ing.projet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.uma.boutiti.bouzidi.ing.projet.models.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    
}

