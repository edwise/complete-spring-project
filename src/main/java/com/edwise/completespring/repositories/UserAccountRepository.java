package com.edwise.completespring.repositories;

import com.edwise.completespring.entities.UserAccount;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserAccountRepository extends MongoRepository<UserAccount, Long> {

    UserAccount findByUsername(String username);
}
