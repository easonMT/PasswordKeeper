package com.usermodel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.usermodel.model.PasswordModel;

@Repository
public interface PasswordRepository  extends JpaRepository<PasswordModel, Long>{
    
}
