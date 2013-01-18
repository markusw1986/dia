package de.unifrankfurt.informatik.dbis.dia.config;

import java.lang.reflect.Field;
import java.util.Map;

import de.unifrankfurt.informatik.dbis.dia.QueryManager;
import de.unifrankfurt.informatik.dbis.dia.ResultManager;
import de.unifrankfurt.informatik.dbis.dia.annotation.Entity;
import de.unifrankfurt.informatik.dbis.dia.annotation.Filter;

/**
 * The config for the {@link QueryManager}, {@link ResultManager} and the the whole rest.
 * The {@code Config} holds the entire information about the {@link Entity}-, as well as about the
 * {@link Filter}-class. 
 * <p>
 * These are:
 * <ul>
 * <li>{@code sourceField}: The name of the field in that the source-id is injected.</li>
 * <li>{@code varMapping}: A {@link Map}{@code <String, }{@link EntityConfigElement}{@code >} which
 * maps the variable to a entity config.</li>
 * <li>{@code helpVarMapping}: A {@link Map}{@code <String, }{@link EntityConfigElement}{@code >} which
 * maps the non-mapped variable to a entity config.</li>
 * <li>{@code filterMapping}: A {@link Map}{@code <String, }{@link FilterConfigElement}{@code >} which
 * maps the filter {@link Field} to a filter config.</li>
 * </ul>
 */
public class Config {

	private String sourceField;
	private String srcConditionField;
	
	private Map<String, EntityConfigElement> varMapping;
	private Map<String, EntityConfigElement> helpVarMapping;
	
	private Map<String, FilterConfigElement> filterMapping;


	
	public Config(Map<String, EntityConfigElement> varMapping,
			Map<String, EntityConfigElement> helpVarMapping,
			Map<String, FilterConfigElement> filterMapping, String source, String srcCondition) {
		super();
		this.varMapping = varMapping;
		this.helpVarMapping = helpVarMapping;
		this.filterMapping = filterMapping;
		this.sourceField = source;
		this.setSrcConditionField(srcCondition);
	}


	public Map<String, EntityConfigElement> getVarMapping() {
		return varMapping;
	}

	public Map<String, EntityConfigElement> getHelpVarMapping() {
		return helpVarMapping;
	}
	public Map<String, FilterConfigElement> getFilterMapping() {
		return filterMapping;
	}

	public String getSourceField() {
		return sourceField;
	}


	public String getSrcConditionField() {
		return srcConditionField;
	}


	public void setSrcConditionField(String srcConditionField) {
		this.srcConditionField = srcConditionField;
	}


	
}
