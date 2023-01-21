package com.tutrit.quickstart.restfull.servise.bean;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.UUID;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class AccessPeriod {
    @Id
    @GeneratedValue
    UUID accessPeriodId;
    LocalDateTime fromDate;
    LocalDateTime toDate;
    @ManyToOne
    @JoinTable(
            name = "master_access_period",
            joinColumns = @JoinColumn(name = "access_period_id"),
            inverseJoinColumns = @JoinColumn(name = "master_id")
    )
    Master master;

}
