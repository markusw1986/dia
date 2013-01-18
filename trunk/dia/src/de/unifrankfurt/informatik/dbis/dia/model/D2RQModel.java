package de.unifrankfurt.informatik.dbis.dia.model;

import de.fuberlin.wiwiss.d2rq.jena.ModelD2RQ;

/**
 * Wrapper class for the {@link ModelD2RQ}.
 *
 */
public class D2RQModel extends ModelWrapper {
	
	
	public D2RQModel(String src, String filename) {
		super( src, new ModelD2RQ(filename));
	}

}
