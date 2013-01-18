package de.unifrankfurt.informatik.dbis.dia.config;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import de.unifrankfurt.informatik.dbis.dia.annotation.Condition;
import de.unifrankfurt.informatik.dbis.dia.annotation.Entity;
import de.unifrankfurt.informatik.dbis.dia.annotation.Filter;
import de.unifrankfurt.informatik.dbis.dia.annotation.Prefix;
import de.unifrankfurt.informatik.dbis.dia.annotation.Prefixes;
import de.unifrankfurt.informatik.dbis.dia.annotation.Source;
import de.unifrankfurt.informatik.dbis.dia.annotation.SourceCondition;
import de.unifrankfurt.informatik.dbis.dia.annotation.Variable;
import de.unifrankfurt.informatik.dbis.dia.util.NodeUtil;

/**
 * Config Factory for creating a {@link Config} out of annotated classes.
 */
public class AnnotationConfigFactory {
	
	/**
	 * creates a {@link Config}-object out of the {@link Entity}- and {@link Filter}-class.
	 * 
	 * @param entity the {@code Entity}-class
	 * @param filter the {@code Filter}-class
	 * @return the {@code Config} for this classes.
	 */
	public static Config createConfig(Class<?> entity, Class<?> filter) {
		if (entity.isAnnotationPresent(Prefixes.class)) {
			for (Prefix prefix : entity.getAnnotation(Prefixes.class).value()) {
				NodeUtil.registerNS(prefix.prefix(), prefix.base());
			}
		}
		
		String src = null;
		Map<String, EntityConfigElement> varMapping = new TreeMap<>();
		Map<String, EntityConfigElement> helpVarMapping = new TreeMap<>();
		
		for(Field field : entity.getDeclaredFields()) {
			if (field.isAnnotationPresent(Variable.class)) {
				Variable efield = field.getAnnotation(Variable.class);
				
				varMapping.put(efield.name(), createConfigElement(efield, field.getName()));
				
			} else {
				if (field.isAnnotationPresent(Source.class)) {
					src = field.getName();
				}
			}
			
		}
		if (entity.isAnnotationPresent(Entity.class))
			for (Variable field : entity.getAnnotation(Entity.class).value()) {
				helpVarMapping.put(field.name(), createConfigElement(field, null));
			}
		
		Map<String, FilterConfigElement> filterMapping = new TreeMap<>();
		String srcCondition = null;
		for(Field field : filter.getDeclaredFields()) {
			if (field.isAnnotationPresent(Condition.class)) {
				Condition efield = field.getAnnotation(Condition.class);
				filterMapping.put(field.getName(), createFilterConfigElement(efield));
			} else if (field.isAnnotationPresent(SourceCondition.class))  {
				if (Collection.class.isAssignableFrom(field.getType())) {
					srcCondition = field.getName();
				} else throw new RuntimeException("SourceCondition in the Filter-object is only allowed on Collection-types");
			}
			
		}
		
		return new Config(varMapping, helpVarMapping, filterMapping, src, srcCondition);
	}
	
	private static FilterConfigElement createFilterConfigElement(Condition c) {
		return new FilterConfigElement(c.name(), c.type());
	}

	@SuppressWarnings("deprecation")
	private static EntityConfigElement createConfigElement(Variable e, String name) {
		return new EntityConfigElement(new TripleTemplate(e.triple()), e.type(), name, e.optional());
	}

}
