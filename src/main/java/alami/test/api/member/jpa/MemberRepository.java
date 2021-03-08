package alami.test.api.member.jpa;

import alami.test.api.member.MemberDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Muhammad Rais Rahim
 */
@Repository
public interface MemberRepository extends JpaRepository<MemberDomain, Long> {

}
