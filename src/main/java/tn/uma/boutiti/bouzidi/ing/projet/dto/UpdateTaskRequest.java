package tn.uma.boutiti.bouzidi.ing.projet.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tn.uma.boutiti.bouzidi.ing.projet.models.Label;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTaskRequest {
    private Long taskId;
    private List<Label> labels;
    private String status;

   
}
