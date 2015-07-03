package pl.btcgrouppl.btc.backend.commons.ddd.models.commons;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.UUID;

/**
 * Created by Sebastian Mekal <sebitg@gmail.com> on 24.06.15.
 */
@Data
public class BaseEntity {

    @Id
    private UUID aggregateId;

}
