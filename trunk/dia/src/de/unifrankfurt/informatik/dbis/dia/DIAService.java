package de.unifrankfurt.informatik.dbis.dia;

import java.util.List;
import java.util.Set;

import com.hp.hpl.jena.rdf.model.Model;

import de.unifrankfurt.informatik.dbis.dia.annotation.Entity;
import de.unifrankfurt.informatik.dbis.dia.annotation.Filter;
import de.unifrankfurt.informatik.dbis.dia.annotation.Variable;

/**
 * The service-interface for using the DIA-infrastructure.
 * <p> 
 * The generic {@code DIAService} is used for mapping of POJOs (plain old java objects) to
 * a RDF-graph using a {@link Model} of the Jena-API. This API aims to solve the problem to
 * receive RDF-data from different sources and summarize that into a simple represantation
 * (a Java-Bean). Mapping of entities is done via the {@link Entity}-annotation.
 * <p>
 * For further information check the Bachelor-Thesis "Integration von relationalen Datenbanken 
 * mit Hilfe von Mitteln des semantischen Webs" of Trang Huynh, Christian Grunert and Markus Wondrak.
 *
 * @param <E> - the {@link Entity} class mapped to the "thing" that is being queried.
 * @param <F> - the {@link Filter} class that is used to set the filter criteria.
 * 
 * @see {@link DIAFactory#createService(Class, Class, java.util.Map)} - for instantiating a {@code DIAService}
 * 
 * @see Entity
 * @see Filter
 * 
 */
public interface DIAService<E, F> {
	
	/**
	 * Queries the underlying sources for the given {@link Filter}-object.
	 * 
	 * @param filter the {@code Filter}-object for that the result should be filtered.
	 * 
	 * @return a {@link List} of {@link Entity}-objects matching the given {@code Filter}-object. 
	 */
	public List<E> query(F filter);
	
	/**
	 * Returns all instances of the given {@link Variable}, that are provided by the sources. 
	 * <p>
	 * 
	 * @param var - the variable of the {@link Entity}-class that possible values should
	 * be found.
	 * @return a {@link List} of Strings containing all values of this variable.
	 */
	public Set<String> findAll(String var);
	
}
