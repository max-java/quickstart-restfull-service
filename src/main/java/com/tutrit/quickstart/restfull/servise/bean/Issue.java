package com.tutrit.quickstart.restfull.servise.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
public class Issue {
    @Id
    @GeneratedValue
    UUID issueId;
    String description;
    String photo;
    String city;
    @Enumerated(value = EnumType.STRING)
    IssueStatus issueStatus;
    @CreationTimestamp
//    @Column(nullable = false, updatable = false, insertable = false)
    LocalDateTime timestamp;
    @ManyToOne
    @JoinTable(
            name = "customer_issues",
            joinColumns = @JoinColumn(name = "issue_id"),
            inverseJoinColumns = @JoinColumn(name = "customer_id")
    )
    Customer customer;
}
