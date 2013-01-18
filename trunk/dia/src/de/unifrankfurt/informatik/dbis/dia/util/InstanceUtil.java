package de.unifrankfurt.informatik.dbis.dia.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Helper-class for all reflecting uses of a class.
 * <p>
 * Espacially {@link Constructor}-, getter- and setter-calls are handled by this class. 
 * One class can also handle more than one instance but only one {@link Class}. 
 *<p>
 *The class used within this util needs to implement a default c'tor.
 *<p>
 *All getter and setter methods are stored within a {@link HashMap} while instatianting,
 *aswell as the constructor. This means all {@link Method}s do not have to be loaded before calling
 *and result to a more performant using of the reflecting method-calls. 
 *<p>
 *There can be either used objects via {@link #setInstance(Object)} or new instances via 
 *{@link #createNewInstance()}. New instances are created, too, if {@link #setValue(String, Object)} 
 *or {@link #getValue(String)} is called, and the {@link #instance} is null.
 *
 * @param <E> the {@link Class} that is encapsulated by this class.
 */
public class InstanceUtil<E> {
	
	E instance;
	
	Constructor<E> ctor;
	
	Map<String, AccessMethods> accessMap;
	
	/**
	 * creates a {@link InstanceUtil} for the given {@link Class}.
	 * 
	 * @param clazz the {@link Class} that is handled by this util-instance.
	 */
	public InstanceUtil(Class<E> clazz) {
		accessMap = new HashMap<>();
		
		try {
			ctor = clazz.getConstructor();
		} catch (NoSuchMethodException | SecurityException e) {
			throw new RuntimeException("class " + clazz + 
					" needs to implement a default c'tor", e);
		}
		
		for (Field field : clazz.getDeclaredFields()) {
			Method getter = null;
			try {
				getter = clazz.getMethod(StringUtil.createGetter(field.getName()));
			} catch (NoSuchMethodException | SecurityException e) {
				// there is maybe no getter
			}
			Method setter = null;
			try {
				Class<?> type = field.getType();
				
				setter = clazz.getMethod(StringUtil.createSetter(field.getName()), type);
			} catch (NoSuchMethodException | SecurityException e) {
				// there is maybe no getter
			}
			
			accessMap.put(field.getName(), new AccessMethods(getter, setter));
		}
	}


	/**
	 * creates a new instance of the handled class via the default c'tor.
	 */
	public void createNewInstance() {
		try {
			instance = ctor.newInstance();
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			throw new RuntimeException("Exception while calling default c'tor", e);
		}
	}
	
	/**
	 * sets the given value to the given field via a suitable setter-method.
	 * 
	 * @param field the name of the field thats valueshould be set 
	 * @param value the value that should be set
	 * 
	 * @throws NullPointerException if no suitable the setter is not found
	 */
	public void setValue(String field, Object value) {
		try {
			accessMap.get(field).setter.invoke(getInstance(), value);
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			throw new RuntimeException("Error while calling getter", e);
		} catch (NullPointerException e) {
			throw new RuntimeException("Make sure "+ field + " has a public setter", e);
		}
	}
	
	/**
	 * retrieves the value of the given field via a suitable getter-method.
	 * @param field the name of field which should be read 
	 * @return the value of the field.
	 * 
	 * @throws NullPointerException if no suitable the getter is not found
	 * 
	 */
	public Object getValue(String field) {
		try {
			return accessMap.get(field).getter.invoke(getInstance());
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			throw new RuntimeException("Error while calling getter", e);
		} catch (NullPointerException e) {
			throw new RuntimeException("Make sure "+ field + " has a public getter", e);
		}
	}
	
	/**
	 * sets a new instance that should be used by now.
	 * @param e the new instance.
	 */
	public void setInstance(E e) {
		instance = e;
	}
	
	/**
	 * returns the currently used instance or a new one if {@link #instance} is null.
	 * 
	 * @return a instance
	 */
	public E getInstance() {
		if (instance == null) createNewInstance();
		return instance;
	}
	
	class AccessMethods {
		Method getter;
		Method setter;
		public AccessMethods(Method getter, Method setter) {
			this.getter = getter;
			this.setter = setter;
		}
	}
}
