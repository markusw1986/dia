package de.unifrankfurt.informatik.dbis.dia.model;

/**
 * The factory for creating {@link ModelWrapper} instances.
 *
 */
public class ModelFactory {
	
	/**
	 * creates {@link ModelWrapper} wrapping a virtual D2RQ-Graph for the given 
	 * mapping file.
	 * 
	 * @param src the source-ID
	 * @param filename the fileurl to the D2R-Mapping file
	 * @return a {@link ModelWrapper} containing the D2R-RDF-Graph.
	 */
	public static ModelWrapper createD2RQModel(String src, String filename) {
		return new D2RQModel(src, filename);
	}
	
}
