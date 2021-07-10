package com.example.CFT_SHIFT_2021.repository;

import com.example.CFT_SHIFT_2021.entity.UnReadEntity;
import org.springframework.data.repository.CrudRepository;

public interface UnReadCRUD extends CrudRepository<UnReadEntity, Long> { // наследуемый интерфейс для изменения данных в бд
}
