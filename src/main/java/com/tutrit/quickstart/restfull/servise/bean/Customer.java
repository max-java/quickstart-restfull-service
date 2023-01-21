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
public class Customer {
    @Id
    @GeneratedValue
    UUID customerId;
    String name;
    String phone;
    String ownerName;
    @OneToOne
    GhbUser ghbUser;
    @OneToMany
    @JoinTable(
            name = "customer_issues",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "issue_id")
    )
    List<Issue> issues;
}
