package alami.test.api.transaction;

import alami.test.api.member.jpa.MemberRepository;
import alami.test.api.page.PageRequestDto;
import alami.test.api.page.PageResponseDto;
import alami.test.api.transaction.jpa.TransactionJpaRepository;
import alami.test.api.transaction.mongo.TransactionMongoRepository;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
public class TransactionServiceImpl implements TransactionService {

    private final TransactionJpaRepository jpaRepo;

    private final TransactionMongoRepository mongoRepo;

    private final KafkaTemplate<String, TransactionDomain> kafkaTemplate;

    private final MemberRepository memberRepo;

    @Transactional
    @Override
    public TransactionDomain createTransaction(TransactionDomain req) {

        if (req.getId() != null) {
            // prevent update existing record
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "id must be null");
        }

        if (!memberRepo.existsById(req.getMemberId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "memberId not found");
        }

        // to postgresql
        req = jpaRepo.save(req);

        // to kafka then mongodb
        kafkaTemplate.send("transaction", req);

        return req;
    }

    @Override
    public Collection<TransactionDomain> getAllTransactionsByExample(TransactionDomain example) {

        log.debug("[getAllTransactionsByExample]-example={}", example);

        return jpaRepo.findAll(Example.of(example));
    }

    @Override
    public Collection<TransactionDomain> getAllTransactionsFromMongodbByExample(TransactionDomain example) {

        log.debug("[getAllTransactionsFromMongodbByExample]-example={}", example);

        return mongoRepo.findAll(Example.of(example));
    }

    @Override
    public Collection<TransactionDomain> getAllTransactionsByDateRange(DateRangeDto param) {

        log.debug("[getAllTransactionsByDateRange]-param={}", param);

        if (param.getFrom().isAfter(param.getTo())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "date from cannot after date to");
        }

        Sort sort = Sort.by(TransactionDomain.PROP_DATE);
        return jpaRepo.findByDateBetween(param.getFrom(), param.getTo(), sort);
    }

    @Override
    public PageResponseDto<TransactionDomain> getAllTransactionsByPage(PageRequestDto req) {

        Pageable p = PageRequest.of(req.getPageNumber(), req.getPageSize());
        return PageResponseDto.of(jpaRepo.findAll(p));
    }

}
