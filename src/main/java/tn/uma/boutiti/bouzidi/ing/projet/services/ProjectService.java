package tn.uma.boutiti.bouzidi.ing.projet.services;

import tn.uma.boutiti.bouzidi.ing.projet.dto.ProjectDTO;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProjectService {
    ProjectDTO save(ProjectDTO projectDTO) ;
    Page<ProjectDTO> findAll(Pageable pageable);
    ProjectDTO findOne(Long id);
    void delete(Long id);
}
