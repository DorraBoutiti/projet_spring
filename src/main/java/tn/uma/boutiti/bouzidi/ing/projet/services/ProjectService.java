package tn.uma.boutiti.bouzidi.ing.projet.services;

import tn.uma.boutiti.bouzidi.ing.projet.dto.ProjectDTO;

import java.util.List;

public interface ProjectService {
    ProjectDTO save(ProjectDTO projectDTO) ;
    List<ProjectDTO> findAll();
    ProjectDTO findOne(Long id);
    void delete(Long id);
}
