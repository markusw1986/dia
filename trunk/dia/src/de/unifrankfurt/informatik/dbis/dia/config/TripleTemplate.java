package de.unifrankfurt.informatik.dbis.dia.config;

import de.unifrankfurt.informatik.dbis.dia.annotation.TriplePattern;

/**
 * The class-pendant for the annotation {@link TriplePattern}.
 *
 */
public class TripleTemplate {

	private String subject;
	private String predicate;
	private String object;

	public TripleTemplate(TriplePattern pattern) {
		this.subject = pattern.subject();
		this.predicate = pattern.predicate();
		this.object = pattern.object();
	}
	
	public String subject() {
		return subject;
	}

	public String predicate() {
		return predicate;
	}

	public String object() {
		return object;
	}
	
	
	
}
