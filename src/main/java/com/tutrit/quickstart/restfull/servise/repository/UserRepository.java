package com.tutrit.quickstart.restfull.servise.repository;

import com.tutrit.quickstart.restfull.servise.bean.GhbUser;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Optional;
import java.util.UUID;

@CrossOrigin
@RepositoryRestResource
public interface UserRepository extends PagingAndSortingRepository<GhbUser, UUID> {
    Optional<GhbUser> findByTelegramChatId(String id);
    Optional<GhbUser> findByEmail(String email);
}
