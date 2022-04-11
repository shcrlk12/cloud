package com.kjwon.cloud.users;

import com.kjwon.cloud.configures.JwtTokenConfigure;
import com.kjwon.cloud.errors.NotFoundException;
import com.kjwon.cloud.errors.UnauthorizedException;
import com.kjwon.cloud.security.Jwt;
import com.kjwon.cloud.security.JwtAuthentication;
import com.kjwon.cloud.security.JwtAuthenticationToken;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.kjwon.cloud.utils.ApiUtils.ApiResult;
import static com.kjwon.cloud.utils.ApiUtils.success;

@AllArgsConstructor
@RestController
@RequestMapping("api/users")
@CrossOrigin
public class UserRestController {

  private final Jwt jwt;

  private final AuthenticationManager authenticationManager;

  private final UserService userService;

  private JwtTokenConfigure jwtTokenConfigure;

  @PostMapping(path = "login")
  public ApiResult<LoginResult> login(
    @Valid @ModelAttribute LoginRequest request
  ) throws UnauthorizedException {
    System.out.println(request.getPrincipal());
    System.out.println(request.getCredentials());

    try {
      Authentication authentication = authenticationManager.authenticate(
        new JwtAuthenticationToken(request.getPrincipal(), request.getCredentials())
      );
      final Users user = (Users) authentication.getDetails();
      final String token = user.newJwt(
        jwt,
        authentication.getAuthorities().stream()
          .map(GrantedAuthority::getAuthority)
          .toArray(String[]::new)
      );
      return success(new LoginResult(token, user));
    } catch (AuthenticationException e) {
      throw new UnauthorizedException(e.getMessage(), e);
    }
  }

  @GetMapping(path = "me")
  public ApiResult<UserDto> me(
    // JwtAuthenticationTokenFilter 에서 JWT 값을 통해 사용자를 인증한다.
    // 사용자 인증이 정상으로 완료됐다면 @AuthenticationPrincipal 어노테이션을 사용하여 인증된 사용자 정보(JwtAuthentication)에 접근할 수 있다.
    @AuthenticationPrincipal JwtAuthentication authentication
  ) {
    return success(
      userService.findById(authentication.id)
        .map(UserDto::new)
        .orElseThrow(() -> new NotFoundException("Could nof found user for " + authentication.id))
    );
  }
}