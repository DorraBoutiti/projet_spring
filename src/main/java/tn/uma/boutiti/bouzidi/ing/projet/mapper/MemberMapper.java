package tn.uma.boutiti.bouzidi.ing.projet.mapper;

import org.springframework.stereotype.Component;

import tn.uma.boutiti.bouzidi.ing.projet.dto.MemberDTO;
import tn.uma.boutiti.bouzidi.ing.projet.models.Member;

@Component
public class MemberMapper {

    public MemberDTO toMemberDTO(Member member) {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId(member.getId());
        memberDTO.setUsername(member.getUsername());
               
        return memberDTO;
    }

    public Member toMember(MemberDTO memberDTO) {
        Member member = new Member();
        member.setId(memberDTO.getId());
        member.setUsername(memberDTO.getUsername());
     
        return member;
    }
}
