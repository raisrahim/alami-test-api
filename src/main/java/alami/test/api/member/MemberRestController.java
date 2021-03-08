package alami.test.api.member;

import alami.test.api.page.PageRequestDto;
import alami.test.api.page.PageResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import java.util.Collection;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Muhammad Rais Rahim
 */
@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
@Slf4j
public class MemberRestController {

    private final MemberService service;

    @Operation(description = "Create a new member")
    @PostMapping
    public MemberDomain createMember(@Valid @RequestBody MemberDomain member) {

        return service.createMember(member);
    }

    @Operation(description = "Get a member")
    @GetMapping("/{memberId}")
    public MemberDomain getMember(@PathVariable Long memberId) {

        return service.getMemberById(memberId);
    }

    @Operation(description = "Get all members")
    @GetMapping
    public Collection<MemberDomain> getAllMembers() {

        return service.getAllMembers();
    }

    @Operation(description = "Get all members by page")
    @GetMapping("/by-page")
    public PageResponseDto<MemberDomain> getAllMembersByPage(
            @Valid PageRequestDto req) {

        return service.getAllMembersByPage(req);
    }
}
