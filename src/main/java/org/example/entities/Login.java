package org.example.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "av_logins")
public class Login {
    @Id
    @SequenceGenerator(name = "sequence", sequenceName = "av_logins_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
    int id;
    Date accessDate;
    int userId;
    String application;

    @Override
    public String toString() {
        return "Login{" +
                "id=" + id +
                ", access_date=" + accessDate +
                ", user_id=" + userId +
                ", application='" + application + '\'' +
                '}';
    }
}
