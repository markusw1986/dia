package de.unifrankfurt.informatik.dbis.dia;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.hp.hpl.jena.query.Query;

import de.unifrankfurt.informatik.dbis.dia.model.ModelWrapper;

public class SimpleDIAService<E, F> implements DIAService<E, F>{
	
	private List<ModelWrapper> models;
	private QueryManager<F> queryMngr;
	private ResultManager<E, F> resultMngr;
	
	SimpleDIAService(List<ModelWrapper> models, 
			QueryManager<F> queryMngr, 
			ResultManager<E, F> resultMngr) {
		
		this.resultMngr = resultMngr;
		this.queryMngr = queryMngr;
		this.models = models;
	}
	
	@Override
	public List<E> query(F filter) {
		List<E> results = new LinkedList<>();
		
		Query query = queryMngr.use(filter);
		
		Collection<Object> srcs = queryMngr.getSourceCondition(filter);
		
		if (srcs != null)
			for (ModelWrapper model : models) {
				
				if (srcs.contains(model.getSrc())) 
					results.addAll(
						resultMngr.create(
							model.execute(query), filter, model.getSrc()
								));
				
				
				model.close();
			}
				
		return results;
	}

	@Override
	public Set<String> findAll(String var) {
		
		Set<String> options = new HashSet<>();
		for (ModelWrapper model : models) {
		
			options.addAll(
				resultMngr.toStringList(
					model.execute(queryMngr.findAll(var))));
					
			model.close();
		}
		return options;
	}

}