package tn.uma.boutiti.bouzidi.ing.projet.services;

import tn.uma.boutiti.bouzidi.ing.projet.dto.TaskDTO;

import java.util.List;
import java.util.Set;


public interface TaskService {
    TaskDTO save (TaskDTO taskDTO);
    List<TaskDTO> findAll();
    TaskDTO findOne(Long id);
    void delete(Long id);
	List<TaskDTO> getTasksByProject(Long projectId);
	List<TaskDTO> getTasksByLabel(Long labelId);
}
