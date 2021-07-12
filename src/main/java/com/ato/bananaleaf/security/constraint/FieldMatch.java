package com.ato.bananaleaf.security.constraint;

import javax.validation.Payload;
import java.lang.annotation.*;

@Target({
        ElementType.TYPE,
        ElementType.ANNOTATION_TYPE
})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FieldMatch {
    String message() default "{constraints.field-match}";
    Class<?>[] group() default {};
    Class<? extends Payload>[] payload() default {};
    String first();
    String second();

    @Target({
            ElementType.TYPE,
            ElementType.ANNOTATION_TYPE
    })
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    public @interface List {
        FieldMatch[] value();
    }
}
