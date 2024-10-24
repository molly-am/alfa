package com.alfaframe.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface FindBy {

    String xpath() default "";

    String buttonText() default "";

    String buttonContainsText() default "";

    String linkText() default "";

    String linkContainsText() default "";

    String id() default "";

    String className() default "";

    String name() default "";

    String type() default "";
}
