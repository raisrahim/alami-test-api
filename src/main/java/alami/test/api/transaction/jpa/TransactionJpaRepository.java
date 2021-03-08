package alami.test.api.transaction.jpa;

import alami.test.api.transaction.TransactionDomain;
import java.time.LocalDate;
import java.util.Collection;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Muhammad Rais Rahim
 */
@Repository
public interface TransactionJpaRepository extends JpaRepository<TransactionDomain, Long> {

    Collection<TransactionDomain> findByDateBetween(LocalDate from, LocalDate to, Sort sort);
}
