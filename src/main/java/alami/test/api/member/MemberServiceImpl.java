package alami.test.api.member;

import alami.test.api.member.jpa.MemberRepository;
import alami.test.api.page.PageRequestDto;
import alami.test.api.page.PageResponseDto;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author Muhammad Rais Rahim
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepo;

    private final KafkaTemplate<String, MemberDomain> kafkaTemplate;

    @Transactional
    @Override
    public MemberDomain createMember(MemberDomain req) {

        if (req.getId() != null) {
            // prevent update existing record
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "id must be null");
        }

        req = memberRepo.save(req);

        kafkaTemplate.send("member", req);

        return req;
    }

    @Override
    public Collection<MemberDomain> getAllMembers() {

        return memberRepo.findAll();
    }

    @Override
    public MemberDomain getMemberById(Long id) {

        return memberRepo.findById(id).get();
    }

    @Override
    public PageResponseDto<MemberDomain> getAllMembersByPage(PageRequestDto req) {

        Pageable p = PageRequest.of(req.getPageNumber(), req.getPageSize());
        return PageResponseDto.of(memberRepo.findAll(p));
    }

}
