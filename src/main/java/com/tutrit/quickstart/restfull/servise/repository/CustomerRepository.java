package com.tutrit.quickstart.restfull.servise.repository;

import com.tutrit.quickstart.restfull.servise.bean.Customer;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;
import java.util.UUID;

@RepositoryRestResource
public interface CustomerRepository extends PagingAndSortingRepository<Customer, UUID> {
    Optional<Customer> findByGhbUser_TelegramChatId(String id);
}
