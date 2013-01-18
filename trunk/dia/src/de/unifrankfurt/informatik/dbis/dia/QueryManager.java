package de.unifrankfurt.informatik.dbis.dia;

import java.util.Collection;
import java.util.Map;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.sparql.core.Var;

import de.unifrankfurt.informatik.dbis.dia.builder.QueryBuilder;
import de.unifrankfurt.informatik.dbis.dia.config.Config;
import de.unifrankfurt.informatik.dbis.dia.config.EntityConfigElement;
import de.unifrankfurt.informatik.dbis.dia.config.FilterConfigElement;
import de.unifrankfurt.informatik.dbis.dia.config.NodeType;
import de.unifrankfurt.informatik.dbis.dia.util.InstanceUtil;
import de.unifrankfurt.informatik.dbis.dia.util.NodeUtil;

public class QueryManager<F> {
	
	InstanceUtil<F> instance;

	private Query baseQuery;

	private Map<String, EntityConfigElement> helpVarMapping;
	private Map<String, EntityConfigElement> varMapping;
	
	private Map<String, FilterConfigElement> filterMapping;
	
	private String srcCondition;
	
	QueryManager(Config config, InstanceUtil<F> instance) {
		this.varMapping = config.getVarMapping();
		this.helpVarMapping = config.getHelpVarMapping();
		this.filterMapping = config.getFilterMapping();
		this.instance = instance;
		
		this.srcCondition = config.getSrcConditionField();
		// create base query with variables and initial triples
		
		
		String[] vars = varMapping.keySet()
				.toArray(new String[varMapping.size()]);
		
		
		baseQuery = QueryBuilder.create(vars).getQuery();
		
	}
	
	private QueryBuilder initQuery() {
		QueryBuilder builder = QueryBuilder.create(baseQuery);
		
		for(String var : helpVarMapping.keySet()) {
			builder.addTriple(
					NodeUtil.solveTriple(
							helpVarMapping.get(var).getTriple(),
					Var.alloc(var)));
		}
		
		// extend with first optional blocks for every ressource-variable
		
		return builder;
	}
	
	
	public Query use(F filter) {
		QueryBuilder builder = initQuery();
		
		System.out.println("Using Filter: " + filter);
		
		// create optional block for the simple-type (string, int) vars
		
		instance.setInstance(filter);
		
		for (String field : filterMapping.keySet()) {
			FilterConfigElement fElem = filterMapping.get(field);
			EntityConfigElement eElem = varMapping.get(fElem.getVar()); 
			
			Object value = instance.getValue(field);
			

			// if its no ressource so its an simple-type
			if (eElem.isOptional()) 
				builder.openOptional();
			
			builder.addTriple(
					NodeUtil.solveTriple(
							eElem.getTriple(), Var.alloc(fElem.getVar())));
			
			if (value != null) {
				builder.addFilter(NodeUtil.createEqualsNode(fElem.getVar(), value, eElem.getType()));
			}
			
			if (eElem.isOptional())
				builder.closeOptional();
		}
		
		Query query = builder.getQuery();
		System.out.println("query prepared:");
		System.out.println(query.serialize());
		
		return query;
	}

	@SuppressWarnings("unchecked")
	public Collection<Object> getSourceCondition(F filter) {
		instance.setInstance(filter);
	
		return (Collection<Object>) instance.getValue(srcCondition);
	
	}

	public Query findAll(String var) {
		EntityConfigElement eelem = varMapping.get(var);
		
		if (eelem.getType() == NodeType.RESOURCE) {
			Query q = QueryBuilder.create(new String[]{var})
				.addTriple(NodeUtil.solveTriple(eelem.getTriple(), Var.alloc(var)))
				.getQuery();
			
			System.out.println(q.serialize());
			
			return q;
		
		} else throw new RuntimeException(var + " is type " + eelem.getType() + 
				". Options are only available for resources");
		
	}


}


