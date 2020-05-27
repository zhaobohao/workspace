package org.dbchain.blockchain.core.repository;

import org.dbchain.blockchain.core.model.SyncEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author wuweifeng wrote on 2017/10/25.
 */
public interface SyncRepository extends JpaRepository<SyncEntity, Long> {
    SyncEntity findTopByOrderByIdDesc();
}
