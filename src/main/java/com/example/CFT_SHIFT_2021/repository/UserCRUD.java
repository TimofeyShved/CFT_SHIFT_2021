package com.example.CFT_SHIFT_2021.repository;

import com.example.CFT_SHIFT_2021.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserCRUD extends CrudRepository<UserEntity, Long> { // наследуемый интерфейс для изменения данных в бд
    UserEntity findUserEntityByFirstName(String firstName);
    UserEntity findUserEntityBylastName(String lastName);
}
