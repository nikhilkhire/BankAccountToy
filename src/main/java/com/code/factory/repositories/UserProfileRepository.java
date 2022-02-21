package com.code.factory.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.code.factory.domain.UserProfileEntity;

public interface UserProfileRepository extends JpaRepository<UserProfileEntity, String>{

    @Query(value = "SELECT up from Profile up WHERE up.userName = ?1")
    UserProfileEntity findByUserName(String userName);
}
