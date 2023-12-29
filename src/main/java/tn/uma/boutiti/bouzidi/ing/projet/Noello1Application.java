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
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.springframework.context.annotation.Bean;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
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

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
        corsConfiguration.setAllowedHeaders(Arrays.asList("Origin", "Access-Control-Allow-Origin", "Content-Type",
                "Accept", "Authorization", "Origin, Accept", "X-Requested-With", "Access-Control-Request-Method",
                "Access-Control-Request-Headers"));
        corsConfiguration.setExposedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Authorization",
                "Access-Control-Allow-Origin", "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"));
        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(urlBasedCorsConfigurationSource);
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
        project.setDescription("This is a sample project");

        Project project2 = new Project();
        project2.setName("Sample Project 2");
        project2.setDescription("This is a sample project 2");
        // Create two Tasks
        Task task1 = new Task();
        task1.setTitle("Task 1");
        task1.setDescription("Description for Task 1");
        task1.setStartDate(LocalDate.now());
        task1.setDueDate(LocalDate.now().plusDays(7));
        task1.setCompleted(false);
        task1.setStatus("Done");


        Task task2 = new Task();
        task2.setTitle("Task 2");
        task2.setDescription("Description for Task 2");
        task2.setStartDate(LocalDate.now());
        task2.setDueDate(LocalDate.now().plusDays(7));
        task2.setCompleted(false);
        task2.setStatus("Done");


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
        
        task1.setMembers(Arrays.asList(member1, member2));
        task2.setMembers(Collections.singletonList(member1));
        // Save entities
        memberRepository.saveAll(Arrays.asList(member1, member2));
        projectRepository.save(project);
        projectRepository.save(project2);
        taskRepository.saveAll(Arrays.asList(task1, task2));
        labelRepository.saveAll(Arrays.asList(label1, label2));
     }

}
