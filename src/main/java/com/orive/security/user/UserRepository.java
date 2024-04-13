package com.orive.security.user;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Integer> {

  Optional<User> findByEmail(String email);
  Optional<User> findByUsername(String username);
  List<User> findAll();

  @Query("SELECT new com.orive.security.user.UserDto(u.id, u.firstname, u.lastname, u.email, u.password, u.role, u.mobilenumber, u.username) FROM User u")
  List<UserDto> findUsersWithSelectedFields();
}
