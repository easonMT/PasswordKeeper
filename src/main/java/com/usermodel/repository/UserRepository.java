package com.usermodel.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.usermodel.model.UserModel;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long>{

    boolean existsByEmail(String email);

    Optional<UserModel> findByEmail(String email);

    
}
