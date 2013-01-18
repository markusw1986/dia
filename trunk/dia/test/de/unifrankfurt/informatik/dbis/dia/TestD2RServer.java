package de.unifrankfurt.informatik.dbis.dia;

import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.sparql.core.Var;
import com.hp.hpl.jena.sparql.expr.E_Equals;
import com.hp.hpl.jena.sparql.expr.Expr;
import com.hp.hpl.jena.sparql.expr.ExprVar;
import com.hp.hpl.jena.sparql.expr.nodevalue.NodeValueNode;
import com.hp.hpl.jena.sparql.syntax.ElementFilter;
import com.hp.hpl.jena.sparql.syntax.ElementGroup;
import com.hp.hpl.jena.sparql.syntax.ElementVisitor;
import com.hp.hpl.jena.sparql.syntax.ElementVisitorBase;
import com.hp.hpl.jena.sparql.util.NodeFactory;

import de.fuberlin.wiwiss.d2rq.jena.ModelD2RQ;

public class TestD2RServer {
	public static void main(String[] args) {
		// Set up the ModelD2RQ using a mapping file
		Model model = new ModelD2RQ("mappings/test-mapping.ttl");

		String queryStr = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
				+ "PREFIX db: <http://localhost:2020/resource/>"
				+ "PREFIX coin: <http://www.dbis.informatik.uni-frankfurt.de/ontologies/2012/08/Coin#>"
				+ "PREFIX owl: <http://www.w3.org/2002/07/owl#>"
				+ "PREFIX map: <http://localhost:2020/resource/#>"
				+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"
				+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
				+ "PREFIX vocab: <http://localhost:2020/resource/vocab/>"
				+ "PREFIX patt: <http://www.dbis.informatik.uni-frankfurt.de/d2r-extension/2012/08/PatternTranslator#>"
				+ "PREFIX nm: <http://nomisma.org/id/>"
				+ "PREFIX skos: <http://www.w3.org/2004/02/skos/core#>"
				+ "SELECT ?material ?date_found ?issuer ?coined_for ?date_from ?date_until "
				+ "WHERE {"
				+ "    ?coin rdf:type nm:coin ."
				+ "    ?coin nm:material ?material ."
				+ "    ?coin nm:date_found ?date_found ."
				+ "    ?coin nm:issuer ?issuer ."
				+ "    OPTIONAL {"
				+ "     ?issuer coin:coined_for ?coined_for ."
				+ "    }"
				+ "    OPTIONAL {"
				+ "     ?coin coin:hasDateFrom ?date_from ."
				+ "     ?coin coin:hasDateUntil ?date_until  " + "    }" + "}";

		Query query = QueryFactory.create(queryStr);

		;

//		addElement(query);
		addTriple(query);
		// Execute the query and obtain results

		QueryExecution qe = QueryExecutionFactory.create(query, model);
		ResultSet results = qe.execSelect();

		// Output query results
		ResultSetFormatter.out(System.out, results, query);

		System.out.println(query.serialize());

		// Important - free up resources used running the query
		qe.close();
		
		
	}

	private static void addTriple(Query query) {

		Node exprVar = Var.alloc("coin");
		Node p = NodeFactory.parseNode("<http://nomisma.org/id/material>");
		Node o = NodeFactory.parseNode("<http://nomisma.org/id/ar>");

		Triple triple = Triple.create(exprVar, p, o);
		
		
		TripleAdder tripleVisitable = new TripleAdder(triple);
		
		query.getQueryPattern().visit(tripleVisitable);
	}

	public static void addElement(Query query) {

		// Create variable expression
		Expr exprVar = new ExprVar("material");

		// Create dateTime expression
		;

		Expr exprDate = new NodeValueNode(
				NodeFactory.parseNode("<http://nomisma.org/id/ar>"));
		// PrefixMap map = new PrefixMap();
		//
		//
		// for (Entry<String, String> entry :
		// query.getPrefixMapping().getNsPrefixMap().entrySet()) {
		// map.add("", "");
		// }
		//
		// Node node = NodeFactory.parseNode("nm:ar", map);

		// Expr exprDate = new NodeValueNode(node);
		// Create is equal expression
		Expr exprEqual = new E_Equals(exprVar, exprDate);
		// Create FILTER
		final ElementFilter filter = new ElementFilter(exprEqual);
		// Define an element visitor to add FILTER element
		FilterAdder visitor = new FilterAdder(filter);
		// Visit query pattern so visitor will add the element filter
		query.getQueryPattern().visit(visitor);
	}

	public static class FilterAdder extends ElementVisitorBase implements
			ElementVisitor {
		// Filter to be added to query
		private ElementFilter filter;

		// Public constructor
		public FilterAdder(ElementFilter elFilter) {
			filter = elFilter;
		}

		// Override this function so that it adds the desired filter
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
}
