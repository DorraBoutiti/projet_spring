package tn.uma.boutiti.bouzidi.ing.projet.mapper;

import org.mapstruct.Mapper;
import tn.uma.boutiti.bouzidi.ing.projet.dto.LabelDTO;
import tn.uma.boutiti.bouzidi.ing.projet.models.Label;
@Mapper(componentModel = "spring")
public interface LabelMapper extends EntityMapper<LabelDTO, Label>
{
    LabelDTO toDto(Label label);

    Label toEntity(LabelDTO labelDTO);



}
