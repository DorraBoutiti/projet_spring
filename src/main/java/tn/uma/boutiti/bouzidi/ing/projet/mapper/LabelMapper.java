package tn.uma.boutiti.bouzidi.ing.projet.mapper;

import org.springframework.stereotype.Component;

import tn.uma.boutiti.bouzidi.ing.projet.dto.LabelDTO;
import tn.uma.boutiti.bouzidi.ing.projet.models.Label;
@Component
public class LabelMapper {

    public LabelDTO toLabelDTO(Label label) {
        LabelDTO labelDTO = new LabelDTO();
        labelDTO.setId(label.getId());
        labelDTO.setName(label.getName());
        // Set task IDs if needed
        
        return labelDTO;
    }

    public Label toLabel(LabelDTO labelDTO) {
        Label label = new Label();
        label.setId(labelDTO.getId());
        label.setName(labelDTO.getName());
        // Mapping of IDs to entities for tasks needs additional logic
        
        return label;
    }
}
