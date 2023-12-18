package tn.uma.boutiti.bouzidi.ing.projet.mapper;

import org.mapstruct.Mapper;
import tn.uma.boutiti.bouzidi.ing.projet.dto.MemberDTO;
import tn.uma.boutiti.bouzidi.ing.projet.models.Member;

@Mapper(componentModel = "spring", uses = {ProjectMapper.class})
public interface MemberMapper extends EntityMapper<MemberDTO, Member>{

    MemberDTO toDto(Member member);

    Member toEntity(MemberDTO memberDTO);


}
