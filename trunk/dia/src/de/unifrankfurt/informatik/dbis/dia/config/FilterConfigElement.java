package de.unifrankfurt.informatik.dbis.dia.config;

import de.unifrankfurt.informatik.dbis.dia.annotation.Condition;
import de.unifrankfurt.informatik.dbis.dia.annotation.Variable;

/**
 * Holds the configurtaion for a {@link Condition}. These are the 
 * {@link Variable} name and the {@link ConditionType}
 * <p>
 * This class is immutable.
 */
public class FilterConfigElement {
	private String var;
	private ConditionType type;
	
	public FilterConfigElement(String var, ConditionType type) {
		super();
		this.var = var;
		this.type = type;
	}
	public String getVar() {
		return var;
	}
	public ConditionType getType() {
		return type;
	}
	
	
}
