package de.unifrankfurt.informatik.dbis.dia.builder;

import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.sparql.expr.Expr;
import com.hp.hpl.jena.sparql.syntax.ElementFilter;
import com.hp.hpl.jena.sparql.syntax.ElementGroup;
import com.hp.hpl.jena.sparql.syntax.ElementOptional;

public class CreateQueryBuilder extends QueryBuilder {
	Query query;
	
	ElementGroup body = new ElementGroup();
	
	ElementGroup optionalPart;
	
	public CreateQueryBuilder(String[] results) {
		query = QueryFactory.create();
		
		for (String name : results) {
			query.addResultVar(name);
		}
		
	}
	public QueryBuilder addTriple(Node s, Node p, Node o) {
		Triple triple = Triple.create(s, p, o);
		
		return addTriple(triple);
	}
	
	@Override
	public QueryBuilder addTriple(Triple triple) {
		if (optionalPart != null) {
			optionalPart.addTriplePattern(triple);
		} else {
			body.addTriplePattern(triple);

		}
		
		return this;
	}
	
	public QueryBuilder addFilter(Expr expr) {
		ElementFilter filter = new ElementFilter(expr);
		if (optionalPart != null) {
			optionalPart.addElementFilter(filter);
		} else {
			body.addElementFilter(filter);
		}
		
		return this;
	}
	
	public QueryBuilder openOptional() {
		if (optionalPart != null) {
			throw new RuntimeException(
					"You want to open an Optional, " +
					"although there is still one open");
		}
		
		optionalPart = new ElementGroup();
		
		return this;
	}
	
	public QueryBuilder closeOptional() {
		if (optionalPart == null) {
			throw new RuntimeException(
					"You want to close an Optional, " +
					"but there is none opened");
		}
		
		body.addElement(new ElementOptional(optionalPart));
		
		optionalPart = null;
		
		return this;
	}
	
	public Query getQuery() {
		Query ret = query;
		
		ret.setQueryPattern(body);
		ret.setQuerySelectType(); 
		ret.setDistinct(true);
		
		query = null;
		body = null;
		
		return ret;
		
		
	}

}
