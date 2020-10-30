package com.company.cinema.repositories;


import com.company.cinema.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<User, Long> {

    User findByUsername(String userName);

}
