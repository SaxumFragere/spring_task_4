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
public class AV_Logins {
    @Id
    @SequenceGenerator(name = "sequence", sequenceName = "av_logins_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
    int id;
    Date access_date;
    int user_id;
    String application;

    @Override
    public String toString() {
        return "AV_Logins{" +
                "id=" + id +
                ", access_date=" + access_date +
                ", user_id=" + user_id +
                ", application='" + application + '\'' +
                '}';
    }
}
