package com.tutrit.quickstart.restfull.servise.repository;

import com.tutrit.quickstart.restfull.servise.bean.Issue;
import com.tutrit.quickstart.restfull.servise.bean.IssueStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@RepositoryRestResource
public interface IssueRepository extends PagingAndSortingRepository<Issue, UUID> {
    Page<Issue> findAllByIssueStatusInOrderByTimestampDesc(IssueStatus[] status, Pageable p);
    Page<Issue> findAllByCity(String city, Pageable p);
    Page<Issue> findAllByCustomer_GhbUser_TelegramChatId(@NotNull String chatId, Pageable p);
}
