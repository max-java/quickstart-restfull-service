package com.tutrit.quickstart.restfull.servise.repository;

import com.tutrit.quickstart.restfull.servise.bean.Master;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;
import java.util.UUID;

@RepositoryRestResource
public interface MasterRepository extends PagingAndSortingRepository<Master, UUID> {
    Optional<Master> findByGhbUser_TelegramChatId(String id);
}
