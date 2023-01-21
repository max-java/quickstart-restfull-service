package com.tutrit.quickstart.restfull.servise.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class GhbUser {
    @Id
    @GeneratedValue
    UUID userId;
    String telegramChatId;
    String userName;
    String locale;
    String email;
    String password;
}
