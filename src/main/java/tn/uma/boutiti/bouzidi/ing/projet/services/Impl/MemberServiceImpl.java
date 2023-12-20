package tn.uma.boutiti.bouzidi.ing.projet.services.Impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.uma.boutiti.bouzidi.ing.projet.dto.MemberDTO;
import tn.uma.boutiti.bouzidi.ing.projet.mapper.MemberMapper;
import tn.uma.boutiti.bouzidi.ing.projet.models.Member;
import tn.uma.boutiti.bouzidi.ing.projet.models.Project;
import tn.uma.boutiti.bouzidi.ing.projet.repository.MemberRepository;
import tn.uma.boutiti.bouzidi.ing.projet.services.MemberService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberMapper memberMapper;


    @Override
    public MemberDTO save(MemberDTO memberDTO) {
        Member member = memberMapper.toEntity(memberDTO);
        member = memberRepository.save(member);
        return memberMapper.toDto(member);
    }


    @Override
    public List<MemberDTO> findAll() {
    return memberMapper.toDto(memberRepository.findAll());
    }

    @Override
    public MemberDTO findOne(Long id) {
        Optional<Member> member =memberRepository.findById(id);
        return memberMapper.toDto(member.get());
    }

    @Override
    public void delete(Long id) {
        memberRepository.deleteById(id);
    }

    @Override
    public List<Project> getAllProjectsAndTasksByUsername(String username) {
        List<Member> members = memberRepository.findByUsername(username);

        if (!members.isEmpty()) {
            Member member = members.get(0); // Assuming username is unique

            return member.getProjects();
        }

        return Collections.emptyList();
    }
}
