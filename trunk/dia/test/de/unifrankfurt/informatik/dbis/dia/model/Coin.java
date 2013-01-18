package de.unifrankfurt.informatik.dbis.dia.model;

import de.unifrankfurt.informatik.dbis.dia.annotation.Entity;
import de.unifrankfurt.informatik.dbis.dia.annotation.Variable;
import de.unifrankfurt.informatik.dbis.dia.annotation.Prefix;
import de.unifrankfurt.informatik.dbis.dia.annotation.Prefixes;
import de.unifrankfurt.informatik.dbis.dia.annotation.Source;
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
	
	@Variable(name = "start_date", type = NodeType.STRING, triple = 
			@TriplePattern(subject ="coin", predicate = "nm:start_date"))
	private String start_date;
	
	@Variable(name = "end_date", type = NodeType.STRING, triple = 
			@TriplePattern(subject ="coin", predicate = "nm:end_date"))
	private String end_date;
	
	@Variable(name = "findtype", type = NodeType.RESOURCE, triple = 
			@TriplePattern(subject ="coin", predicate = "coin:hasFindtype"))
	private String findtype;
	
	@Variable(name = "period", type = NodeType.RESOURCE, triple =
			@TriplePattern(subject="coin", predicate="coin:hasPeriod"))
	private String period;
	
	@Variable(name = "authority", type = NodeType.RESOURCE, triple =
			@TriplePattern(subject="coin", predicate="coin:hasAuthority"))
	private String authority;
	@Variable(name = "mint", type = NodeType.RESOURCE, triple =
			@TriplePattern(subject="coin", predicate="coin:hasMint"))
	private String mint;
	@Variable(name = "denomination", type = NodeType.RESOURCE, triple =
			@TriplePattern(subject="coin", predicate="coin:hasDenomination"))
	private String denomination;
	
	@Variable(name = "material", type = NodeType.RESOURCE, triple =
			@TriplePattern(subject="coin", predicate="coin:hasMaterial"))
	private String material;
	
//	@Variable(name = "coinedFor", type = NodeType.STRING, triple =
//			@TriplePattern(subject="coin", predicate="coin:coinedFor"))
	private String coinedFor;
	
	@Source
	private String source;
	
	public String getStart_date() {
		return start_date;
	}

	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}

	public String getEnd_date() {
		return end_date;
	}

	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getFindtype() {
		return findtype;
	}

	public void setFindtype(String findtype) {
		this.findtype = findtype;
	}
	
	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}
	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public String getMint() {
		return mint;
	}

	public void setMint(String mint) {
		this.mint = mint;
	}
	public String getCoinedFor() {
		return coinedFor;
	}

	public void setCoinedFor(String coinedFor) {
		this.coinedFor = coinedFor;
	}

	public String getDenomination() {
		return denomination;
	}

	public void setDenomination(String denomination) {
		this.denomination = denomination;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	@Override
	public String toString() {
		return "Coin [start_date=" + start_date + ", end_date=" + end_date
				+ ", findtype=" + findtype + ", period=" + period
				+ ", authority=" + authority + ", mint=" + mint
				+ ", hasDenomination=" + denomination + ", material="
				+ material + ", coinedFor=" + coinedFor + ", source=" + source
				+ "]";
	}

}
