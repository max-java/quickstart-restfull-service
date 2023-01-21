package com.tutrit.quickstart.restfull.servise.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Master {
    @Id
    @GeneratedValue
    UUID masterId;
    String name;
    String photo;
    String domain;
    String experience;
    String workplace;
    @OneToOne
    GhbUser ghbUser;
    @OneToMany
    @JoinTable(
            name = "master_access_period",
            joinColumns = @JoinColumn(name = "master_id"),
            inverseJoinColumns = @JoinColumn(name = "access_period_id")
    )
    List<AccessPeriod> accessPeriods;
}
