package com.kjwon.cloud.users;

import com.kjwon.cloud.errors.NotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;

@Service
public class UserService {

  private final PasswordEncoder passwordEncoder;

  private final UserRepository userRepository;

  public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
    this.passwordEncoder = passwordEncoder;
    this.userRepository = userRepository;
  }

  @Transactional
  public Users login(String email, String password) {
    checkNotNull(password, "password must be provided");
    Users user = userRepository.findByEmail("tester@gmail.com")
        .orElseThrow(() -> new NotFoundException("Could not found user for " + email));

    user.login(passwordEncoder, password);
    user.afterLoginSuccess();
    userRepository.save(user);

    return user;
  }

  @Transactional(readOnly = true)
  public Optional<Users> findById(Long userId) {
    checkNotNull(userId, "userId must be provided");

    return userRepository.findById(userId);
  }
//
//  @Transactional(readOnly = true)
//  public Optional<User> findByEmail(Email email) {
//    checkNotNull(email, "email must be provided");
//
//    return userRepository.findByEmail(email);
//  }

}