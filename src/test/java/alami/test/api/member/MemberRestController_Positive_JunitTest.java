package alami.test.api.member;

import alami.test.api.AbstractTest;
import java.time.LocalDate;
import java.util.List;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 *
 * @author Muhammad Rais Rahim
 */
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
@Order(1)
public class MemberRestController_Positive_JunitTest extends AbstractTest {

    @Test
    @Order(1)
    public void createMembers_OK() throws Exception {

        List list = List.of(
                MemberDomain.builder().name("Wawan Setiawan")
                        .birthDate(LocalDate.parse("1990-01-10"))
                        .address("kompleks Asia Serasi No 100").build(),
                MemberDomain.builder().name("Teguh Sudibyantoro")
                        .birthDate(LocalDate.parse("1990-02-10"))
                        .address("Jalan Pemekaran No 99").build(),
                MemberDomain.builder().name("Joko Widodo")
                        .birthDate(LocalDate.parse("1990-03-10"))
                        .address("Dusun Pisang Rt 10 Rw 20").build()
        );

        for (Object member : list) {
            postObjectAsJson("/members", member)
                    .andExpect(status().isOk());
        }
    }

    @Test
    @Order(2)
    public void getAllMembers_OK() throws Exception {

        String content = mockMvc
                .perform(get("/members").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].name").isString())
                .andExpect(jsonPath("$[0].birthDate").isString())
                .andExpect(jsonPath("$[0].address").isString())
                .andReturn()
                .getResponse()
                .getContentAsString();

        MemberDomain[] array = objectMapper.readValue(content, MemberDomain[].class);
        memberList = (List) Arrays.asList(array);
    }
    public static List<MemberDomain> memberList;

}
