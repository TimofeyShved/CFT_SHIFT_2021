package com.example.CFT_SHIFT_2021.service;

import com.example.CFT_SHIFT_2021.entity.UserEntity;
import com.example.CFT_SHIFT_2021.exception.ChatExistsException;
import com.example.CFT_SHIFT_2021.exception.UserExistsException;
import com.example.CFT_SHIFT_2021.exception.UserNotFoundException;
import com.example.CFT_SHIFT_2021.repository.UserCRUD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class UserService {

    @Autowired
    private UserCRUD userCRUD; // создаём интерфейс для взаимодействия с бд

    public UserEntity registration(UserEntity user) throws ChatExistsException {
        if ((userCRUD.findUserEntityByFirstName(user.getFirstName())!=null)&&(userCRUD.findUserEntityBylastName(user.getLastName())!=null)){
            throw new ChatExistsException("code: CHAT_EXISTS");
        }
        return userCRUD.save(user);
    }

    public UserEntity getOne(Long id) throws UserNotFoundException {
        UserEntity user = userCRUD.findById(id).get();
        if (user==null){
            throw new UserNotFoundException(
                    "{"+System.lineSeparator()+
                    "code: USER_NOT_FOUND"+System.lineSeparator()+
                    "}"
            );
        }
        return user;
    }

    public Long deleteOne(Long id) throws UserNotFoundException {
        if (userCRUD.findById(id).get()==null){
            throw new UserNotFoundException(
                    "{"+System.lineSeparator()+
                    "code: USER_NOT_FOUND"+System.lineSeparator()+
                    "}"
            );
        }
        userCRUD.deleteById(id);
        return id;
    }

    public UserEntity updateOne(Long id, UserEntity userNew) throws Exception {
        UserEntity user = userCRUD.findById(id).get();
        if (userCRUD.findById(id).get()==null){
            throw new UserNotFoundException(
                    "{"+System.lineSeparator()+
                            "code: USER_NOT_FOUND"+System.lineSeparator()+
                            "}"
            );
        }
        if ((userCRUD.findUserEntityByFirstName(userNew.getFirstName())!=null)&&(userCRUD.findUserEntityBylastName(userNew.getLastName())!=null)){
            throw new UserExistsException("code: USER_EXISTS");
        }
        user.setFirstName(userNew.getFirstName());
        user.setLastName(userNew.getLastName());
        return userCRUD.save(user);
    }
}
