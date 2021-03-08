package alami.test.api.transaction.mongo;

import alami.test.api.transaction.TransactionDomain;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Muhammad Rais Rahim
 */
@Repository
public interface TransactionMongoRepository extends MongoRepository<TransactionDomain, Long> {

}
