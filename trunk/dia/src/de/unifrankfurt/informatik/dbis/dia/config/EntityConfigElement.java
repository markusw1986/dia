package de.unifrankfurt.informatik.dbis.dia.config;

import java.lang.reflect.Field;

import de.unifrankfurt.informatik.dbis.dia.annotation.TriplePattern;
import de.unifrankfurt.informatik.dbis.dia.annotation.Variable;

/**
 * Holds the configuration for a {@link Variable}.
 * <p>
 * These are:
 * <ul>
 * <li>{@code triple}: The {@link TripleTemplate} (see also: {@link TriplePattern})</li>
 * <li>{@code type}: The {@link NodeType} of the value</li>
 * <li>{@code fieldName}: The name of the {@link Field} annotated with the {@link Variable}. 
 * Is null if the {@code Variable} is non-mapped</li>
 * </ul>
 *<p>
 * This class is immutable.
 */
public class EntityConfigElement {

	private TripleTemplate triple;
	
	private NodeType type;
	
	private String fieldName;

	private boolean optional;

	public EntityConfigElement(TripleTemplate triple, NodeType type) {
		super();
		this.triple = triple;
		this.type = type;
	}

	public EntityConfigElement(TripleTemplate triple, NodeType type,
			String fieldName, boolean optional) {
		super();
		this.triple = triple;
		this.type = type;
		this.fieldName = fieldName;
		this.setOptional(optional);
	}

	public TripleTemplate getTriple() {
		return triple;
	}

	public NodeType getType() {
		return type;
	}

	public String getFieldName() {
		return fieldName;
	}

	public boolean isOptional() {
		return optional;
	}

	public void setOptional(boolean optional) {
		this.optional = optional;
	}


}
