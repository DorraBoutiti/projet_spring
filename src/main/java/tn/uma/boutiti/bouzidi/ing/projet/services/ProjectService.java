package tn.uma.boutiti.bouzidi.ing.projet.services;

import tn.uma.boutiti.bouzidi.ing.projet.dto.ProjectDTO;

import java.util.List;

public interface ProjectService {

    ProjectDTO save(ProjectDTO projectDTO) ;

    List<ProjectDTO> findAll();

    ProjectDTO findOne(Long id);

    void delete(Long id);

    
  /*  public void assignTaskToProject(Long taskId, Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("Project not found"));

        // Assuming you have a method in TaskService to retrieve a task by ID
        Task task = taskService.findTaskById(taskId);

        if (task != null) {
            task.setProject(project);
            // You might want to save the task again after setting the project
            taskService.saveTask(task);
        } else {
            throw new EntityNotFoundException("Task not found");
        }
    }*/
    
   

}
