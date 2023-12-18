package tn.uma.boutiti.bouzidi.ing.projet.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO {
    private Long id;
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate dueDate;
    private boolean completed;
    private List<LabelDTO> labels;
    private Long projectId;
    private String projectName;
}
