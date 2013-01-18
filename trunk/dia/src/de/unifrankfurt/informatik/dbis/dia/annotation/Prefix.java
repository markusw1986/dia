package de.unifrankfurt.informatik.dbis.dia.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Defines a certain prefix-mapping for the DIA-context
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Prefix {
	/**
	 * The prefix that stand for the base-URI ({@link #base()}) 
	 */
	String prefix();
	/**
	 * The base-URI matching the {@link #prefix()}
	 */
	String base();
}
