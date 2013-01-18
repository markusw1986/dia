package de.unifrankfurt.informatik.dbis.dia.model_test;

import de.unifrankfurt.informatik.dbis.dia.annotation.Entity;
import de.unifrankfurt.informatik.dbis.dia.annotation.Variable;
import de.unifrankfurt.informatik.dbis.dia.annotation.Prefix;
import de.unifrankfurt.informatik.dbis.dia.annotation.Prefixes;
import de.unifrankfurt.informatik.dbis.dia.annotation.TriplePattern;
import de.unifrankfurt.informatik.dbis.dia.config.NodeType;

@Entity({
	@Variable(name = "coin", type = NodeType.RESOURCE, triple = 
		@TriplePattern(predicate =  "rdf:type", object = "nm:coin"))
})
@Prefixes({
	@Prefix(prefix="nm", base="http://nomisma.org/id/"),
	@Prefix(prefix="rdf", base="http://www.w3.org/1999/02/22-rdf-syntax-ns#"),
	@Prefix(prefix="coin", base="http://www.dbis.informatik.uni-frankfurt.de/ontologies/2012/08/Coin#")
})
public class Coin {
	
	@Variable(name = "material", type = NodeType.RESOURCE, triple = 
			@TriplePattern(subject ="coin", predicate = "nm:material"))
	private String material;
	
	@Variable(name = "issuer", type = NodeType.RESOURCE, triple = 
			@TriplePattern(subject ="coin", predicate = "nm:issuer"))
	private String issuer;
	
	@Variable(name = "date_from", type = NodeType.INT, triple = 
			@TriplePattern(subject = "coin", predicate = "coin:hasDateFrom"))
	private int date_from;
	
	// getter und setter ...

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public int getDate_from() {
		return date_from;
	}

	public void setDate_from(int date_from) {
		this.date_from = date_from;
	}
	
	public String getIssuer() {
		return issuer;
	}

	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}

	@Override
	public String toString() {
		return "Coin [material=" + material + ", issuer=" + issuer
				+ ", date_from=" + date_from + "]";
	}
	
	
}
