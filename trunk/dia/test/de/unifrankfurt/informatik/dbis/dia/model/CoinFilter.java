package de.unifrankfurt.informatik.dbis.dia.model;

import java.util.List;

import de.unifrankfurt.informatik.dbis.dia.annotation.Condition;
import de.unifrankfurt.informatik.dbis.dia.annotation.Filter;
import de.unifrankfurt.informatik.dbis.dia.annotation.SourceCondition;

@Filter
public class CoinFilter {
	
	@Condition(name="start_date")
	private String start_date;
	
	@Condition(name="end_date")
	private String end_date;

	@Condition(name="findtype")
	private String findtype;
	
	@Condition(name="period")
	private String period;
	
	@Condition(name="authority")
	private String authority;
	
//	@Condition(name="coinedFor")
	private String coinedFor;
	
	@Condition(name="mint")
	private String mint;
	
	@Condition(name="denomination")
	private String denomination;
	
	@Condition(name="material")
	private String material;
	
	@SourceCondition
	private List<String> sources;
	
	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

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

	public String getFindtype() {
		return findtype;
	}

	public void setFindtype(String findtype) {
		this.findtype = findtype;
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

	public List<String> getSources() {
		return sources;
	}

	public void setSources(List<String> sources) {
		this.sources = sources;
	}
	
	
	
}
