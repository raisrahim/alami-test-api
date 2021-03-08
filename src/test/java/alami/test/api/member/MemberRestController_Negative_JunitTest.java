package alami.test.api.member;

import alami.test.api.AbstractTest;
import java.time.LocalDate;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 *
 * @author Muhammad Rais Rahim
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Order(2)
public class MemberRestController_Negative_JunitTest extends AbstractTest {

    private MemberDomain constructNewMember() {
        return MemberDomain.builder().name("Wawan Setiawan")
                .birthDate(LocalDate.parse("1990-01-10"))
                .address("kompleks Asia Serasi No 100").build();
    }

    @Test
    public void createMember_Failed_ShortName() throws Exception {

        MemberDomain member = constructNewMember();
        member.setName("as"); // min 3 chars

        postObjectAsJson("/members", member)
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createMember_Failed_NullName() throws Exception {

        MemberDomain member = constructNewMember();
        member.setName(null); // name must not null

        postObjectAsJson("/members", member)
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createMember_Failed_FutureBirthDate() throws Exception {

        MemberDomain member = constructNewMember();
        member.setBirthDate(LocalDate.now().plusYears(1)); // future date

        postObjectAsJson("/members", member)
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createMember_Failed_BlankAddress() throws Exception {

        MemberDomain member = constructNewMember();
        member.setAddress(""); // must not blank

        postObjectAsJson("/members", member)
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createMember_Failed_IdMustNull() throws Exception {

        MemberDomain member = constructNewMember();
        member.setId(10l); // id must null

        postObjectAsJson("/members", member)
                .andExpect(status().isBadRequest());
    }

}
