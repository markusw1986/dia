package de.unifrankfurt.informatik.dbis.dia;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;

import de.fuberlin.wiwiss.d2rq.jena.ModelD2RQ;
import de.unifrankfurt.informatik.dbis.dia.config.AnnotationConfigFactory;
import de.unifrankfurt.informatik.dbis.dia.config.Config;
import de.unifrankfurt.informatik.dbis.dia.model.Coin;
import de.unifrankfurt.informatik.dbis.dia.model.CoinFilter;
import de.unifrankfurt.informatik.dbis.dia.model.ModelFactory;
import de.unifrankfurt.informatik.dbis.dia.model.ModelWrapper;
import de.unifrankfurt.informatik.dbis.dia.util.InstanceUtil;


public class TestQuery {
	
	
	public static void main(String[] args) {
		ModelWrapper model = ModelFactory.createD2RQModel("dfmroe", "mappings/dfmroe-mapping.ttl");
		
		
		
		Config config = AnnotationConfigFactory.createConfig(Coin.class, CoinFilter.class);
		QueryManager<CoinFilter> mngr = new QueryManager<>(config , new InstanceUtil<>(CoinFilter.class));
		
		CoinFilter filter = new CoinFilter();
//		filter.setFindtype("coin:Single");
		filter.setStart_date("138");
		Query q = mngr.use(filter);
		
		System.out.println(q.serialize());
		
		@SuppressWarnings("unused")
		Model m = new ModelD2RQ("mappings/dfmroe-mapping.ttl");
		
//		ResultSet rs_wo = QueryExecutionFactory.create(q, m).execSelect();
		ResultSet rs = model.execute(q);
		
//		System.out.println(rs_wo.getRowNumber());
		System.out.println(rs.getRowNumber());
		
		
		
	}
}
