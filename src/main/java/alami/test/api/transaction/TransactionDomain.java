package alami.test.api.transaction;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author Muhammad Rais Rahim
 */
@Document
@Entity
@Table(name = "trx")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionDomain implements Serializable {

    @MongoId
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @PastOrPresent
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "trx_date")
    private LocalDate date;
    public static final String PROP_DATE = "date";

    @NotNull
    @Positive
    private BigInteger amount;

    @NotNull
    private TransactionType transactionType;

    @NotNull
    private Long memberId;
}
