package de.unifrankfurt.informatik.dbis.dia.model_test;

import de.unifrankfurt.informatik.dbis.dia.annotation.Condition;
import de.unifrankfurt.informatik.dbis.dia.annotation.Filter;
import de.unifrankfurt.informatik.dbis.dia.config.ConditionType;

@Filter
public class CoinFilter {
	
	@Condition(name="issuer")
	private String issuer;
	
	@Condition(name="material")
	private String material;
	
	@Condition(name="date_from", type=ConditionType.GT)
	private Integer date_from;
	
	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public Integer getDate_from() {
		return date_from;
	}

	public void setDate_from(Integer date_from) {
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
		return "CoinFilter [material=" + material + ", date_from=" + date_from
				+ "]";
	}
	
	
	
}
