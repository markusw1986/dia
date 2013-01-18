package de.unifrankfurt.informatik.dbis.dia.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import de.unifrankfurt.informatik.dbis.dia.config.NodeType;

/**
 * Defines a variable in the context of the generated SPARQL-Statement.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Variable {
	/**
	 * The name of the variable.
	 */
	String name();
	/**
	 * The type of the containing value. If the type is of {@link NodeType#INT}, the 
	 * underlying field has to be of type {@link Integer}, too.
	 */
	NodeType type();
	/**
	 * The {@link TriplePattern} defining in which context this variable stands to the
	 * entire graph.
	 */
	TriplePattern triple();
	/**
	 * SHOULD NOT BE USED! 
	 * 
	 * @deprecated see bachelor thesis for further details. 
	 */
	@Deprecated
	boolean optional() default false;

}
