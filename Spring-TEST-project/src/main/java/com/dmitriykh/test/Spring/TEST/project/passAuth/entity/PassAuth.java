package com.dmitriykh.test.Spring.TEST.project.passAuth.entity;

import com.dmitriykh.test.Spring.TEST.project.authorization.user.User;
import com.opencsv.bean.CsvBindByName;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "passAuth")
public class PassAuth {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
