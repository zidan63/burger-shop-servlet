package com.burger.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.burger.middlewares.BaseMiddleware;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Middlewares {
  Class<? extends BaseMiddleware>[] value();

}
