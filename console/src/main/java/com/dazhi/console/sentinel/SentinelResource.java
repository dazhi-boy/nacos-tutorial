package com.dazhi.console.sentinel;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface SentinelResource {
    String value() default "";
}
