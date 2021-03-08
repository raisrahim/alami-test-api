package alami.test.api.transaction;

import alami.test.api.AbstractTest;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static alami.test.api.member.MemberRestController_Positive_JunitTest.memberList;
import org.junit.jupiter.api.Assertions;

/**
 *
 * @author Muhammad Rais Rahim
 */
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
@Order(2)
public class TransactionRestController_Positive_JunitTest extends AbstractTest {

    @Test
    @Order(1)
    public void createTransactions_OK() throws Exception {

        for (TransactionDomain trx : trxList) {
            postObjectAsJson("/transactions", trx)
                    .andExpect(status().isOk());
        }
    }
    public static List<TransactionDomain> trxList = List.of(
            TransactionDomain.builder()
                    .amount(BigInteger.valueOf(1000000l))
                    .date(LocalDate.now().minusMonths(1))
                    .transactionType(TransactionType.DEPOSIT)
                    .memberId(memberList.get(0).getId())
                    .build(),
            TransactionDomain.builder()
                    .amount(BigInteger.valueOf(500000l))
                    .date(LocalDate.now())
                    .transactionType(TransactionType.WITHDRAWAL)
                    .memberId(memberList.get(0).getId())
                    .build(),
            TransactionDomain.builder()
                    .amount(BigInteger.valueOf(100000l))
                    .date(LocalDate.now())
                    .transactionType(TransactionType.BORROW)
                    .memberId(memberList.get(1).getId())
                    .build()
    );

    @Test
    @Order(2)
    public void getAllTransactionsByExample_OK() throws Exception {

        TransactionDomain example = trxList.get(0);

        mockMvc.perform(
                get("/transactions")
                        .param("date", example.getDate().toString())
                        .param("amount", example.getAmount().toString())
                        .param("transactionType", example.getTransactionType().toString())
                        .param("memberId", example.getMemberId().toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].date").value(example.getDate().toString()))
                .andExpect(jsonPath("$[0].amount").value(example.getAmount()))
                .andExpect(jsonPath("$[0].transactionType").value(example.getTransactionType().toString()))
                .andExpect(jsonPath("$[0].memberId").value(example.getMemberId()));
    }

    @Test
    @Order(3)
    public void getAllTransactionsByDateRange_OK() throws Exception {

        mockMvc.perform(
                get("/transactions/by-date-range")
                        .param("from", LocalDate.now().minusMonths(6).toString())
                        .param("to", LocalDate.now().toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].date").isString())
                .andExpect(jsonPath("$[0].amount").isNumber())
                .andExpect(jsonPath("$[0].transactionType").isString())
                .andExpect(jsonPath("$[0].memberId").isNumber());
    }

    @Test
    @Order(3)
    public void getAllTransactionsFromMongodb_OK_ByMemberId() throws Exception {

        Long memberId = trxList.get(0).getMemberId();

        String result = mockMvc.perform(
                get("/transactions/from-mongodb")
                        .param("memberId", memberId.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].memberId").isNumber())
                .andReturn()
                .getResponse()
                .getContentAsString();

        TransactionDomain[] list = objectMapper.readValue(result, TransactionDomain[].class);
        for (TransactionDomain trx : list) {
            Assertions.assertEquals(memberId, trx.getMemberId());
        }
    }

}
