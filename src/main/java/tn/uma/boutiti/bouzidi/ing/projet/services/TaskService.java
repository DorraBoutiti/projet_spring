package tn.uma.boutiti.bouzidi.ing.projet.services;

import org.springframework.stereotype.Service;

import tn.uma.boutiti.bouzidi.ing.projet.models.Task;
import tn.uma.boutiti.bouzidi.ing.projet.repository.TaskRepository;

@Service
public class TaskService {

    private TaskRepository taskRepository;

    

    public Task createTask(Task task) {        
        return taskRepository.save(task);
    }
}
