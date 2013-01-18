package de.unifrankfurt.informatik.dbis.dia;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import de.unifrankfurt.informatik.dbis.dia.config.AnnotationConfigFactory;
import de.unifrankfurt.informatik.dbis.dia.config.Config;
import de.unifrankfurt.informatik.dbis.dia.model.ModelFactory;
import de.unifrankfurt.informatik.dbis.dia.model.ModelWrapper;
import de.unifrankfurt.informatik.dbis.dia.util.InstanceUtil;

public final class DIAFactory {
	
	private DIAFactory() {
		// private c'tor
	}
	
	public static <E, F> DIAService<E, F> createService(
			Class<E> entityClass, 
			Class<F> filterClass, 
			Map<String, String> mappingFiles) {

		Config config = AnnotationConfigFactory.createConfig(entityClass, filterClass);
		
		return new SimpleDIAService<>(
				createModels(mappingFiles), 
				new QueryManager<F>(config, new InstanceUtil<>(filterClass)),
				 
				new ResultManager<>(new InstanceUtil<>(entityClass), 
						new InstanceUtil<>(filterClass), config));
	}

	private static List<ModelWrapper> createModels(Map<String, String> mappingFiles) {
		List<ModelWrapper> models = new LinkedList<>();
		
		for (String src : mappingFiles.keySet()) {
			models.add(ModelFactory.createD2RQModel(src, mappingFiles.get(src)));
		}
		
		return models;
	}
	
}
