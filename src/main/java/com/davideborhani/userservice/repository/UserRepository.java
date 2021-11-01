package com.davideborhani.userservice.repository;

import com.davideborhani.userservice.model.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
    Optional<User> findByIdAndUsername(Long userId, String username);
}
