package alami.test.api.transaction;

import alami.test.api.transaction.mongo.TransactionMongoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 *
 * @author Muhammad Rais Rahim
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class TransactionKafkaListener {

    private final TransactionMongoRepository mongoRepo;

    @KafkaListener(topics = "transaction")
    public void listen(TransactionDomain trx) {

        log.info("[listen]-trx={}", trx);

        mongoRepo.save(trx);
    }

}
