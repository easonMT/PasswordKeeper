package com.usermodel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.usermodel.model.UserModel;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long>{

    
}
