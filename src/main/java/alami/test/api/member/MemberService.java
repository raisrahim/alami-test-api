package alami.test.api.member;

import alami.test.api.page.PageRequestDto;
import alami.test.api.page.PageResponseDto;
import java.util.Collection;

/**
 *
 * @author Muhammad Rais Rahim
 */
public interface MemberService {

    MemberDomain createMember(MemberDomain req);

    Collection<MemberDomain> getAllMembers();

    MemberDomain getMemberById(Long id);

    PageResponseDto<MemberDomain> getAllMembersByPage(PageRequestDto req);

}
