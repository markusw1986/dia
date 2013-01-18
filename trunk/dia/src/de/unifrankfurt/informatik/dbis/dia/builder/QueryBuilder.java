package de.unifrankfurt.informatik.dbis.dia.builder;

import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.sparql.expr.Expr;

/**
 * Abstract class to serve a common interface for the both builder-types.
 * The types differ as one is used to extend a given {@link Query} ({@link ExtendQueryBuilder})
 * and the other one to build a new query from scratch ({@link CreateQueryBuilder})
 * <p>
 * The {@code QueryBuilder} only produces <code>SELECT DISTINCT</code> queries.
 * <p>
 * The {@code QueryBuilder} implements the fluent-interface so evers add*-method returns the same 
 * instance of the {@code QueryBuilder} (like the append-method of the {@link StringBuilder#append(Object)}) .
 * 
 * @see #create(Query)
 * @see #create(String[])
 */
public abstract class QueryBuilder {
	
	QueryBuilder() {
		// avoid extending this class from outside the package
	}
	
	/**
	 * creates a {@code QueryBuilder} that creates a new query.
	 * 
	 * @param results - a array of the variables that should be selected.
	 * @return a instance of a {@code QueryBuilder}
	 */
	public static QueryBuilder create(String[] results) {
		return new CreateQueryBuilder(results);
	}
	/**
	 * creates a {@code QueryBuilder} that extends a given stub-query.
	 * 
	 * @param base - the {@link Query}-stub that shall be extended.
	 * @return a instance of a {@code QueryBuilder}
	 */
	public static QueryBuilder create(Query base) {
		return new ExtendQueryBuilder(base);
	}
	
	/**
	 * adds a {@link Triple} containing the given subject, predicate and object to the
	 * building block.
	 *  
	 * @param s the subject {@link Node}
	 * @param p the predicate {@link Node}
	 * @param o the object {@link Node}
	 */
	public abstract QueryBuilder addTriple(Node s, Node p, Node o);

	/**
	 * adds the {@link Triple} to the building block
	 * @param triple the triple to be added.
	 */
	public abstract QueryBuilder addTriple(Triple triple);
	
	/**
	 * opens an OPTIONAL-Block. Every statement added after this call is placed 
	 * into the OPTIONAL-Block, until {@link #closeOptional()} is called.
	 */
	public abstract QueryBuilder openOptional();
	
	/**
	 * Adds a FILTER with the given Expression to the block.
	 * 
	 * @param expr the FILTER-Expression.
	 */
	public abstract QueryBuilder addFilter(Expr expr);
	
	/**
	 * closes the OPTIONAL-Block.
	 */
	public abstract QueryBuilder closeOptional();
	
	/**
	 * Returns the built {@link Query} and clears the builder. For using the builder again,
	 * a new {@code QueryBuilder} should be instantiated.
	 * @return the built {@code Query}
	 */
	public abstract Query getQuery();


}
