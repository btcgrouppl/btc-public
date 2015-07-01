package pl.btcgrouppl.btc.backend.commons.ddd.models.commons;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.UUID;

/**
 * Created by Sebastian Mekal <sebitg@gmail.com> on 24.06.15.
 * <p>
 *     Base aggregate root
 * </p>
 */
@Getter
@Setter
@EqualsAndHashCode(of = {"aggregateId"})
public class BaseAggregateRoot {

    public enum STATUS {
        ACTIVE,
        ARCHIVED
    }

    @Id
    private UUID aggregateId;
    private STATUS status;

    public BaseAggregateRoot() {
        status = STATUS.ACTIVE;
    }


    /**
     * Marking base aggregate as removed
     */
    public void markAsRemoved() {
        this.status = STATUS.ARCHIVED;
    }

}