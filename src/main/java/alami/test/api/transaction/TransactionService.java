package alami.test.api.transaction;

import alami.test.api.page.PageRequestDto;
import alami.test.api.page.PageResponseDto;
import java.util.Collection;

/**
 *
 * @author Muhammad Rais Rahim
 */
public interface TransactionService {

    TransactionDomain createTransaction(TransactionDomain req);

    Collection<TransactionDomain> getAllTransactionsByExample(TransactionDomain example);

    Collection<TransactionDomain> getAllTransactionsFromMongodbByExample(TransactionDomain example);

    Collection<TransactionDomain> getAllTransactionsByDateRange(DateRangeDto param);

    PageResponseDto<TransactionDomain> getAllTransactionsByPage(PageRequestDto req);
}
