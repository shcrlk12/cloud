package com.kjwon.cloud.security;

import com.kjwon.cloud.errors.NotFoundException;
import com.kjwon.cloud.users.Role;
import com.kjwon.cloud.users.UserService;
import com.kjwon.cloud.users.Users;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.apache.commons.lang3.ClassUtils.isAssignable;
import static org.springframework.security.core.authority.AuthorityUtils.createAuthorityList;

public class JwtAuthenticationProvider implements AuthenticationProvider {

  private final UserService userService;

  public JwtAuthenticationProvider(UserService userService) {
    this.userService = userService;
  }

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    JwtAuthenticationToken authenticationToken = (JwtAuthenticationToken) authentication;
    return processUserAuthentication(
            (String)authenticationToken.getPrincipal(),
            authenticationToken.getCredentials()
    );
  }

  private Authentication processUserAuthentication(String email, String password) {
    try {
      Users user = userService.login(email, password);
      System.out.println(user.toString());
      JwtAuthenticationToken authenticated =
        new JwtAuthenticationToken(
          new JwtAuthentication(user.getSeq(), user.getName()),
          null,
          createAuthorityList(Role.USER.value())
        );
      authenticated.setDetails(user);
      return authenticated;
    } catch (NotFoundException e) {
      throw new UsernameNotFoundException(e.getMessage());
    } catch (IllegalArgumentException e) {
      throw new BadCredentialsException(e.getMessage());
    } catch (DataAccessException e) {
      throw new AuthenticationServiceException(e.getMessage(), e);
    }
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return isAssignable(JwtAuthenticationToken.class, authentication);
  }

}