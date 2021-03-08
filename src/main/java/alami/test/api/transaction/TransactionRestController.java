package alami.test.api.transaction;

import alami.test.api.page.PageRequestDto;
import alami.test.api.page.PageResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import java.util.Collection;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Muhammad Rais Rahim
 */
@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
@Slf4j
public class TransactionRestController {

    private final TransactionService service;

    @Operation(description = "Create a new transaction")
    @PostMapping
    public TransactionDomain createTransaction(
            @Valid @RequestBody TransactionDomain req) {

        return service.createTransaction(req);
    }

    @Operation(description = "Get all transactions (optionally filtered by example)")
    @GetMapping
    public Collection<TransactionDomain> getAllTransactions(
            TransactionDomain example) {

        return service.getAllTransactionsByExample(example);
    }

    @Operation(description = "Get all transactions by date range")
    @GetMapping("/by-date-range")
    public Collection<TransactionDomain> getAllTransactionsByDateRange(
            @Valid DateRangeDto param) {

        return service.getAllTransactionsByDateRange(param);
    }

    @Operation(description = "Get all transactions from mongodb (optionally filtered by example)")
    @GetMapping("/from-mongodb")
    public Collection<TransactionDomain> getAllTransactionsFromMongodb(
            TransactionDomain example) {

        return service.getAllTransactionsFromMongodbByExample(example);
    }

    @Operation(description = "Get all transactions by page")
    @GetMapping("/by-page")
    public PageResponseDto<TransactionDomain> getAllTransactionsByPage(
            @Valid PageRequestDto req) {

        return service.getAllTransactionsByPage(req);
    }
}
