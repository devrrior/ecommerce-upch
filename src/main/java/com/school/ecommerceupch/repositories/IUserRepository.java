package com.school.ecommerceupch.repositories;

import com.school.ecommerceupch.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {

    Boolean existsUserByEmail(String email);
    Boolean existsUserById(Long id);
    User getUserById(Long id);


}
