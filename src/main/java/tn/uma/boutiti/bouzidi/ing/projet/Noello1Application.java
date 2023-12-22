package tn.uma.boutiti.bouzidi.ing.projet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tn.uma.boutiti.bouzidi.ing.projet.models.Label;
import tn.uma.boutiti.bouzidi.ing.projet.models.Member;
import tn.uma.boutiti.bouzidi.ing.projet.models.Project;
import tn.uma.boutiti.bouzidi.ing.projet.models.Task;
import tn.uma.boutiti.bouzidi.ing.projet.repository.LabelRepository;
import tn.uma.boutiti.bouzidi.ing.projet.repository.MemberRepository;
import tn.uma.boutiti.bouzidi.ing.projet.repository.ProjectRepository;
import tn.uma.boutiti.bouzidi.ing.projet.repository.TaskRepository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class Noello1Application implements CommandLineRunner {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private LabelRepository labelRepository;

    public static void main(String[] args) {
        SpringApplication.run(Noello1Application.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        // Create two Members
      Member member1 = new Member();
        member1.setUsername("JohnDoe");
        member1.setPassword("password1");

        Member member2 = new Member();
        member2.setUsername("JaneSmith");
        member2.setPassword("password2");

        // Create a Project
        Project project = new Project();
        project.setName("Sample Project");

        // Create two Tasks
        Task task1 = new Task();
        task1.setTitle("Task 1");
        task1.setDescription("Description for Task 1");
        task1.setStartDate(LocalDate.now());
        task1.setDueDate(LocalDate.now().plusDays(7));
        task1.setCompleted(false);

        Task task2 = new Task();
        task2.setTitle("Task 2");
        task2.setDescription("Description for Task 2");
        task2.setStartDate(LocalDate.now());
        task2.setDueDate(LocalDate.now().plusDays(7));
        task2.setCompleted(false);

        // Create two Labels
        Label label1 = new Label();
        label1.setName("Label 1");

        Label label2 = new Label();
        label2.setName("Label 2");

        // Establish relationships
        project.setMembers(Arrays.asList(member1, member2));

        task1.setProject(project);
        task2.setProject(project);

        Set<Task> tasks = new HashSet<>(Arrays.asList(task1, task2));
        label1.setTasks(tasks);
        label2.setTasks(tasks);

        // Save entities
        memberRepository.saveAll(Arrays.asList(member1, member2));
        projectRepository.save(project);
        taskRepository.saveAll(Arrays.asList(task1, task2));
        labelRepository.saveAll(Arrays.asList(label1, label2)); 
     }

}
