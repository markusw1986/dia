package de.unifrankfurt.informatik.dbis.dia.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

import com.hp.hpl.jena.graph.Node;

import de.unifrankfurt.informatik.dbis.dia.util.DEFAULT;

/**
 * Stands for the Triplepattern that should be used in the SPARQL-Statement to 
 * connect the {@link Variable} to the graph. The {@link #predicate()} must be 
 * a parseable {@link Node}, the enclosing "<" and ">" can be omitted. The 
 * given object or subject is interpreted as a variable for the SPARQL statement. 
 * Either the subject or the object must be excluded, when using the {@code TriplePattern}
 * in a {@link Variable} that is used on a {@link Field}.
 * 
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface TriplePattern {
	/**
	 * The subject of the pattern. Has to be a variable.
	 */
	String subject() default DEFAULT.NOT_ASSIGNED;
	/**
	 * The predicate of the pattern. Must be a parsable {@link Node}.
	 */
	String predicate();
	/**
	 * The object of the pattern. Has to be a variable.
	 */
	String object() default DEFAULT.NOT_ASSIGNED;
	
}
