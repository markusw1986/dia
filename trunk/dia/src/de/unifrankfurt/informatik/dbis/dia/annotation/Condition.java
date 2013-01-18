package de.unifrankfurt.informatik.dbis.dia.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

import de.unifrankfurt.informatik.dbis.dia.config.ConditionType;

/**
 * Indicatates a {@link Field} in the {@link Filter}-class that this field holds the filter value for a 
 * certain {@link Variable} ({@link #name()})
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Condition {

	/**
	 * The name of the variable.
	 */
	String name();

	/**
	 * The conditiontype that should be used. The default is equal.
	 */
	ConditionType type() default ConditionType.EQ;
}
