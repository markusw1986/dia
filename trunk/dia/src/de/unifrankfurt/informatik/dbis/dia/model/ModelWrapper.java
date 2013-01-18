package de.unifrankfurt.informatik.dbis.dia.model;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;

/**
 * Abstract class for wrapping a Jena-{@link Model}. A source-ID must be injected
 * for identifying the source of certain objects found.
 *
 */
public abstract class ModelWrapper {
	
	protected Model model;
	
	private String src;
	
	private QueryExecution exec;
	
	public ModelWrapper(String src, Model model) {
		this.model = model;
		this.src = src;
	}

	/**
	 * executes the given {@link Query} to the containing {@link Model} via 
	 * a {@link QueryExecution}. Before the execution is created, {@link #close()} 
	 * is called, that closes the last used {@code QueryExecution}.
	 * 
	 * @param query the {@code Query}
	 * @return the {@link ResultSet} of the {@code Query}
	 */
	public ResultSet execute(Query query) {
		close();
		
		exec = QueryExecutionFactory.create(query, model);
		
		return exec.execSelect();
		
	}
	
	/**
	 * calls {@link QueryExecution#close()}, if there is an existing {@link QueryExecution}-object
	 * of a past {@link #execute(Query)}-call.
	 * <p>
	 * should always be called in a {@code finally}-block
	 */
	public void close() {
		if (exec != null) {
			exec.close();
			exec = null;
		}
	}

	/**
	 * @return the source-ID of the containing {@link Model}.
	 */
	public String getSrc() {
		return src;
	}

}
