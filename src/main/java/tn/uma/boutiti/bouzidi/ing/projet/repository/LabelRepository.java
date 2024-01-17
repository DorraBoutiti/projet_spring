package tn.uma.boutiti.bouzidi.ing.projet.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.uma.boutiti.bouzidi.ing.projet.models.Label;

@Repository
public interface LabelRepository extends JpaRepository<Label, Long> {
    List<Label> findByName(String name);
    Optional<Label> findById(Long id);
}
