package com.kjwon.cloud.security;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface WithMockJwtAuthentication {

    long id() default 1L;

    String name() default "tester";

    String role() default "ROLE_USER";

}