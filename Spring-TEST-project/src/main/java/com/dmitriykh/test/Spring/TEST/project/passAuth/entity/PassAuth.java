package com.dmitriykh.test.Spring.TEST.project.passAuth.entity;

import com.dmitriykh.test.Spring.TEST.project.authorization.entity.User;
import com.opencsv.bean.CsvBindByName;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "passAuth")
public class PassAuth {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @CsvBindByName
    private Long id;
    @CsvBindByName
    private String name;
    @CsvBindByName
    private String url;
    @CsvBindByName
    private String username;
    @CsvBindByName
    private String password;

    @ManyToOne
    @JoinColumn(name="user_id")
    @NonNull
    private User user;

    public PassAuth(String name, String url, String username, String password, User user) {
        this.name = name;
        this.url = url;
        this.username = username;
        this.password = password;
        this.user = user;
    }
}
