package tn.uma.boutiti.bouzidi.ing.projet.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import tn.uma.boutiti.bouzidi.ing.projet.exceptions.EntityNotFoundException;
import tn.uma.boutiti.bouzidi.ing.projet.models.Label;
import tn.uma.boutiti.bouzidi.ing.projet.models.Member;
import tn.uma.boutiti.bouzidi.ing.projet.models.Project;
import tn.uma.boutiti.bouzidi.ing.projet.models.Task;
import tn.uma.boutiti.bouzidi.ing.projet.repository.MemberRepository;
import tn.uma.boutiti.bouzidi.ing.projet.repository.ProjectRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
@Service
@Transactional
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final MemberRepository memberRepository;
    private TaskService taskService;

    public ProjectService(ProjectRepository projectRepository, TaskService taskService, MemberRepository memberRepository) {
        this.projectRepository = projectRepository;
        this.taskService = taskService;
        this.memberRepository = memberRepository;
    }

    public Project createProject(String name) {
        Project project = new Project();
        project.setName(name);
        return projectRepository.save(project);
    }
    
    public void assignTaskToProject(Long taskId, Long projectId) {
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
    }
    
   

}
