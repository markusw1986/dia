package de.unifrankfurt.informatik.dbis.dia.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

/**
 * Indicates the {@link Field} in the {@link Entity}-class where the source-identifier, in which this object
 * was found is injected.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Source {

}
