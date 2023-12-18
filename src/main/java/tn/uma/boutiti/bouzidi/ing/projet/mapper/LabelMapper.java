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
        return labelDTO;
    }

    public Label toLabel(LabelDTO labelDTO) {
        Label label = new Label();
        label.setId(labelDTO.getId());
        label.setName(labelDTO.getName());
               
        return label;
    }
}
