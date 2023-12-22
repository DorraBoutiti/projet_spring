package tn.uma.boutiti.bouzidi.ing.projet.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.uma.boutiti.bouzidi.ing.projet.models.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
	// List<Member> findByUsername(String username);
	Optional<Member> findByUsername(String username);


}