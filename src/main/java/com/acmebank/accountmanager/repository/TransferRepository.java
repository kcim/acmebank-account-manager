package com.acmebank.accountmanager.repository;

import com.acmebank.accountmanager.repository.entity.TransferEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferRepository extends CrudRepository<TransferEntity, Long> {
}
