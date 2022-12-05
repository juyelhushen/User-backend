package com.jskool.springcrudreact.repository;

import com.jskool.springcrudreact.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface UserRepo extends JpaRepository<User, Long> {
}
