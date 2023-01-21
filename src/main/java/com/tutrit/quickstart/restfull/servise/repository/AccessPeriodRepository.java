package com.tutrit.quickstart.restfull.servise.repository;

import com.tutrit.quickstart.restfull.servise.bean.AccessPeriod;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@RepositoryRestResource
public interface AccessPeriodRepository extends PagingAndSortingRepository<AccessPeriod, UUID> {
    AccessPeriod findFirstByMaster_GhbUser_TelegramChatIdAndFromDateIsAfterAndToDateIsBeforeOrderByToDateDesc(
            @NotNull String chatId,
            LocalDateTime fromDate,
            LocalDateTime toDate);

    AccessPeriod findFirstByMaster_MasterId_OrderByToDateDesc(@NotNull UUID masterId);

    Optional<AccessPeriod> findByMaster_masterIdAndFromDateIsNullAndToDateIsNull(@NotNull UUID masterId);
    Iterable<AccessPeriod> findAllByFromDateIsNullAndToDateIsNull();
}
