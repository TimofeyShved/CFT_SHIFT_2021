package com.example.CFT_SHIFT_2021.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class UserEntity { // ----------------------------------------------- наш с вами пользователь

    // ----------------------------------------------- переменные
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // автогенерация значений ключа
    private long userId;
    private String firstName, lastName;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    private List<MessageEntity> message;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    private List<MessageEntity> participants;

    public UserEntity(){ // конструктор
    }

    // ----------------------------------------------- гетеры и сетеры
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
