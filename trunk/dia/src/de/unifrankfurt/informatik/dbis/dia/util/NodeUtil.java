package de.unifrankfurt.informatik.dbis.dia.util;

import java.util.HashMap;
import java.util.Map;

import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.sparql.core.Var;
import com.hp.hpl.jena.sparql.expr.E_Equals;
import com.hp.hpl.jena.sparql.expr.Expr;
import com.hp.hpl.jena.sparql.expr.ExprVar;
import com.hp.hpl.jena.sparql.expr.nodevalue.NodeValueInteger;
import com.hp.hpl.jena.sparql.expr.nodevalue.NodeValueNode;
import com.hp.hpl.jena.sparql.expr.nodevalue.NodeValueString;
import com.hp.hpl.jena.sparql.util.NodeFactory;

import de.unifrankfurt.informatik.dbis.dia.config.NodeType;
import de.unifrankfurt.informatik.dbis.dia.config.TripleTemplate;

public final class NodeUtil {

	private static final String RES_PATTERN = "<(.*?)>";
	private static final String RES_NONS_PATTERN = "<http:(.*?)>";
	private static final String NOBLANK_PATTERN = "^[\\S]*$";
	private static final String HTTP_PREFIX = "http:";
	
	private static Map<String, String> prefixMap = new HashMap<>();

	private NodeUtil() {
		// avoid instantiating this class
	}
	
	public static synchronized void registerNS(String prefix, String baseUri) {
		if (HTTP_PREFIX.equals(prefix)) 
			throw new RuntimeException(prefix + " is not allowed as prefix");
		
		prefixMap.put(prefix, baseUri);
	}
	
	public static Expr createExpressionNode(Object o, NodeType type) {
		switch (type) {
		case RESOURCE:
			if (o instanceof String)
				return new NodeValueNode(createNode((String) o));
			break;
		case INT:
			if (o instanceof Integer) 
				return new NodeValueInteger((Integer) o);
			break;
		case STRING:
			if (o instanceof String)
				return new NodeValueString((String) o);
			break;
		}
		
		throw new RuntimeException(o + " has no suitable type for NodeType " + type);		
	}
	

	public static Object createObject(RDFNode rdfNode, NodeType type) {
		switch (type) {
		case RESOURCE:
			if (rdfNode.isURIResource()) 
				return rdfNode.toString();
			
		case INT:
			if (rdfNode.isLiteral())
				return  rdfNode.asLiteral().getInt();
		case STRING:
			if (rdfNode.isLiteral())
				return  rdfNode.asLiteral().getString();
		}
		
		throw new RuntimeException(rdfNode + " has no suitable type for NodeType " + type);	
	}
	
	public static Node createNode(String value) {
		if (value.contains(":")) {
			return createRessourceNode(value);
		} else if (value.matches(NOBLANK_PATTERN)) {
			return Var.alloc(value);
		} else {
			throw new RuntimeException("Only Variables or Ressources are available as a Node");
		}
	}
	
	public static Node createRessourceNode(String value) {
		if (!value.matches(RES_NONS_PATTERN)) {
			if (value.matches(RES_PATTERN)) {
				value = value.substring(1, value.length() - 1);
			}
			if (!value.startsWith(HTTP_PREFIX)) {
				value = checkNS(value);
			}
			
			value = "<" + value + ">";
		}
		return NodeFactory.parseNode(value);
	}
	
	private static String checkNS(String value) {

		return (!value.startsWith(HTTP_PREFIX)) ?
			prefixMap.get(value.split(":")[0]) + value.split(":")[1]:
			value;
	}
	
	public static Triple solveTriple(TripleTemplate triplePatt, Node value) {
		Node s = (triplePatt.subject().equals(DEFAULT.NOT_ASSIGNED)) ?
				value :
				createNode(triplePatt.subject()) ;
		Node p = createNode(triplePatt.predicate());
		Node o = (triplePatt.object().equals(DEFAULT.NOT_ASSIGNED)) ?
				value :
				createNode(triplePatt.object()) ;
		
		return new Triple(s, p, o);
	}
	

	public static Expr createEqualsNode(String var, Object value, NodeType type) {
		
		return new E_Equals(new ExprVar(var), createExpressionNode(value, type));
	}

	
	
}
