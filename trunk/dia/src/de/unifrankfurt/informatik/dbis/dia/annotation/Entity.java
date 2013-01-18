package de.unifrankfurt.informatik.dbis.dia.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.hp.hpl.jena.query.ResultSet;

/**
 * This annotation indicates an Entity-class that is used for the mapping to the RDF-datasources.
 * <p>
 * Triples of variables which are not used to come up in the {@link ResultSet} should be determined in this annotation. 
 * With the array declaration it is possible to define several non-mapped {@link Variable}. These variables can not be 
 * mapped to a field.   
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Entity {

	/**
	 * An Array of non-mapped variables.
	 */
	Variable[] value();

	
}
