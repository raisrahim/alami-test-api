package alami.test.api.transaction;

import alami.test.api.AbstractTest;
import static alami.test.api.member.MemberRestController_Positive_JunitTest.memberList;
import java.math.BigInteger;
import java.time.LocalDate;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.Map;
import org.springframework.http.MediaType;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

/**
 *
 * @author Muhammad Rais Rahim
 */
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
@Order(3)
public class TransactionRestController_Negative_JunitTest extends AbstractTest {

    private TransactionDomain constructNewTrx() {

        return TransactionDomain.builder()
                .amount(BigInteger.valueOf(500000l))
                .date(LocalDate.now())
                .transactionType(TransactionType.WITHDRAWAL)
                .memberId(memberList.get(0).getId())
                .build();
    }

    @Test
    public void createTransaction_Failed_NegativeAmount() throws Exception {

        TransactionDomain trx = constructNewTrx();
        trx.setAmount(BigInteger.valueOf(-1000000l));// negative

        postObjectAsJson("/transactions", trx)
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createTransaction_Failed_WrongTrxType() throws Exception {

        Map trx = objectMapper.convertValue(constructNewTrx(), Map.class);
        trx.put("transactionType", "ASDF");// wrong trx type

        postObjectAsJson("/transactions", trx)
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createTransaction_Failed_IdMustNull() throws Exception {

        TransactionDomain trx = constructNewTrx();
        trx.setId(100000l); // id must null

        postObjectAsJson("/transactions", trx)
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createTransaction_Failed_DateFormat() throws Exception {

        Map trx = objectMapper.convertValue(constructNewTrx(), Map.class);
        trx.put("date", "20-12-2009");// wrong date format

        postObjectAsJson("/transactions", trx)
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createTransaction_Failed_FutureDate() throws Exception {

        TransactionDomain trx = constructNewTrx();
        trx.setDate(LocalDate.now().plusDays(1));// future date

        postObjectAsJson("/transactions", trx)
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createTransaction_Failed_NullMemberId() throws Exception {

        TransactionDomain trx = constructNewTrx();
        trx.setMemberId(null); // null memberId

        postObjectAsJson("/transactions", trx)
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createTransaction_Failed_MemberIdNotExist() throws Exception {

        TransactionDomain trx = constructNewTrx();
        trx.setMemberId(Long.MAX_VALUE); // memberId not exist

        postObjectAsJson("/transactions", trx)
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getAllTransactionsByDateRange_Failed_NoParam() throws Exception {

        mockMvc.perform(
                get("/transactions/by-date-range")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getAllTransactionsByDateRange_Failed_NoParamDateFrom() throws Exception {

        mockMvc.perform(
                get("/transactions/by-date-range")
                        .param("to", LocalDate.now().toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getAllTransactionsByDateRange_Failed_NoParamDateTo() throws Exception {

        mockMvc.perform(
                get("/transactions/by-date-range")
                        .param("from", LocalDate.now().toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getAllTransactionsByDateRange_Failed_DateFromAfterDateTo() throws Exception {

        mockMvc.perform(
                get("/transactions/by-date-range")
                        .param("from", LocalDate.now().toString())
                        .param("to", LocalDate.now().minusDays(1).toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

}
