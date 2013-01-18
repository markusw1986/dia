package de.unifrankfurt.informatik.dbis.dia.builder;

import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.sparql.expr.Expr;
import com.hp.hpl.jena.sparql.syntax.ElementFilter;
import com.hp.hpl.jena.sparql.syntax.ElementGroup;
import com.hp.hpl.jena.sparql.syntax.ElementOptional;
import com.hp.hpl.jena.sparql.syntax.ElementVisitor;
import com.hp.hpl.jena.sparql.syntax.ElementVisitorBase;

public class ExtendQueryBuilder extends QueryBuilder {
	Query query;
	
	ElementGroup optionalPart;
	
	public ExtendQueryBuilder(Query base) {
		query = QueryFactory.create(base);
		
	}
	
	@Override
	public QueryBuilder addTriple(Node s, Node p, Node o) {
		Triple triple = Triple.create(s, p, o);
		
		return addTriple(triple);
	}



	@Override
	public QueryBuilder addTriple(Triple triple) {
		if (optionalPart != null) {
			optionalPart.addTriplePattern(triple);
		} else {
			query.getQueryPattern().visit(new TripleAdder(triple));
		}
		
		return this;
	}

	
	@Override
	public QueryBuilder addFilter(Expr expr) {
		ElementFilter filter = new ElementFilter(expr);
		if (optionalPart != null) {
			optionalPart.addElementFilter(filter);
		} else {
			
			query.getQueryPattern().visit(new FilterAdder(filter));
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


	@Override
	public QueryBuilder closeOptional() {
		if (optionalPart == null) {
			throw new RuntimeException(
					"You want to close an Optional, " +
					"but there is none opened");
		}
		
		OptionalAdder optional = new OptionalAdder( new ElementOptional(optionalPart));
		query.getQueryPattern().visit(optional);
		
		optionalPart = null;
		
		return this;
	}

	@Override
	public Query getQuery() {
		Query ret = query;
		query = null;
		return ret;
	}
	
//	private void addVisitor(ElementVisitor visitor) {
//		query.getQueryPattern().visit(visitor);
//	}
	
	public static class FilterAdder extends ElementVisitorBase implements
			ElementVisitor {

		private ElementFilter filter;

		public FilterAdder(ElementFilter elFilter) {
			filter = elFilter;
		}

		@Override
		public void visit(ElementGroup el) {
			el.addElementFilter(filter);
		}
	}

	public static class TripleAdder extends ElementVisitorBase implements
			ElementVisitor {
		// Filter to be added to query
		private Triple triple;

		// Public constructor
		public TripleAdder(Triple triple) {
			this.triple = triple;
		}

		// Override this function so that it adds the desired filter
		@Override
		public void visit(ElementGroup el) {
			el.addTriplePattern(triple);
		}
	}
	
	public static class OptionalAdder extends ElementVisitorBase implements
			ElementVisitor {
		private ElementOptional optional;

		// Public constructor
		public OptionalAdder(ElementOptional optional) {
			this.optional = optional;
		}

		// Override this function so that it adds the desired filter
		@Override
		public void visit(ElementGroup el) {
			el.addElement(optional);
		}
	}


}
