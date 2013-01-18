package de.unifrankfurt.informatik.dbis.dia;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.RDFNode;

import de.unifrankfurt.informatik.dbis.dia.config.Config;
import de.unifrankfurt.informatik.dbis.dia.config.EntityConfigElement;
import de.unifrankfurt.informatik.dbis.dia.config.FilterConfigElement;
import de.unifrankfurt.informatik.dbis.dia.config.NodeType;
import de.unifrankfurt.informatik.dbis.dia.util.InstanceUtil;
import de.unifrankfurt.informatik.dbis.dia.util.NodeUtil;

public class ResultManager<E, F> {
	InstanceUtil<E> eInstance;
	InstanceUtil<F> fInstance; 
	String sourceField;
	private Map<String, EntityConfigElement> varMapping;
	private Map<String, FilterConfigElement> filterMapping;
	
	ResultManager(InstanceUtil<E> entity, InstanceUtil<F> filter, Config config) {
		fInstance = filter;
		eInstance = entity;
		
		sourceField = config.getSourceField();
		varMapping =  config.getVarMapping();
		filterMapping = config.getFilterMapping();
		
	}
	
	public List<E> create(ResultSet results, F filter, String src) {
		fInstance.setInstance(filter);
		
		List<E> es = new LinkedList<>();
		
		while (results.hasNext()) {
			boolean skip = false;
			
			eInstance.createNewInstance();
			eInstance.setValue(sourceField, src);
			QuerySolution sol = results.nextSolution();
			
			for (String var : varMapping.keySet()) {
				
				EntityConfigElement eelem = varMapping.get(var);
				FilterConfigElement felem = filterMapping.get(eelem.getFieldName());
				
				RDFNode sValue = sol.get(var);
				
				if (sValue != null) {
				
					Object value = NodeUtil.createObject(sValue, 
							eelem.getType());
					
					Object fvalue;
					if (eelem.getType() != NodeType.RESOURCE && 
							(fvalue = fInstance.getValue(eelem.getFieldName())) != null) {
						
						if (skip = checkSkip(felem, value, fvalue)) break;
					}
					
					eInstance.setValue(eelem.getFieldName(), value);
					
				}
//				System.out.println(var + ": " + sol.get(var));
			}
			
			if (!skip)
				es.add(eInstance.getInstance());
		}
		
		return es;
		
	}

	public List<String> toStringList(ResultSet results) {
		List<String> options = new LinkedList<>();
		String var = results.getResultVars().get(0);
		
		while (results.hasNext()) {
			QuerySolution sol = (QuerySolution) results.next();
			options.add(sol.get(var).toString());
		}
		
		return options;
	}

	
	private boolean checkSkip(FilterConfigElement felem, Object value, Object fvalue) {
		switch (felem.getType()) {
		case EQ:
			return !value.equals(fvalue);
		case GT:
			if (value instanceof Integer)
				return (Integer)value <=  (Integer)fvalue;
			if (value instanceof String)
				return ((String) value).compareToIgnoreCase((String) fvalue) <= 0;
			throw new RuntimeException("ConditionType.GT is only available for String and Int types");
		case LT:
			if (value instanceof Integer)
				return (Integer)value >=  (Integer)fvalue;
			if (value instanceof String)
				return ((String) value).compareToIgnoreCase((String) fvalue) >= 0;
			throw new RuntimeException("ConditionType.LT is only available for String and Int types");
		default:
			throw new RuntimeException(felem.getType() + " is not supported yet");
		}
	}

}
