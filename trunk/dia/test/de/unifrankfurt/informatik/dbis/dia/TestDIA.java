package de.unifrankfurt.informatik.dbis.dia;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import de.unifrankfurt.informatik.dbis.dia.model.Coin;
import de.unifrankfurt.informatik.dbis.dia.model.CoinFilter;


public class TestDIA {
	
	static DIAService<Coin, CoinFilter> service;
	
	public static void main(String[] args) {
		long before = System.currentTimeMillis();
		
		Map<String, String> mappings = new HashMap<>();
		
		mappings.put("NUMIS", "mappings/numis-mapping.ttl");
		mappings.put("dFMRÖ", "mappings/dfmroe-mapping.ttl");
		
		service = DIAFactory.createService(Coin.class, CoinFilter.class, mappings);
		
		CoinFilter filter = new CoinFilter();
		
//		filter.setFindtype("coin:Burial");
//		filter.setFindtype("coin:Single");
//		filter.setPeriod("coin:Celtic");
		
		filter.setSources(new LinkedList<>(mappings.keySet()));
//		filter.setStart_date("244");
		listAllCoins(filter);
		
//		listAll("period");


		System.out.println("took time " + (System.currentTimeMillis() - before) / 1000 +  " s");
		
	}
	
	public static void listAllCoins(CoinFilter filter) {
		List<Coin> coins = service.query(filter);
		
		for (Coin coin : coins) {
			System.out.println(coin);
		}
		
		System.out.println("found " + coins.size() + " coins");
	}
	
	public static void listAll(String prop) {
		
		for (String str : service.findAll(prop)) {
			System.out.println(str);
		}
	}

}
