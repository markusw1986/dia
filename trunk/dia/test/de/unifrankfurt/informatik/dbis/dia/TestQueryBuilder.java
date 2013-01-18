package de.unifrankfurt.informatik.dbis.dia;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.sparql.core.Var;
import com.hp.hpl.jena.sparql.util.NodeFactory;

import de.unifrankfurt.informatik.dbis.dia.builder.QueryBuilder;
import de.unifrankfurt.informatik.dbis.dia.config.NodeType;
import de.unifrankfurt.informatik.dbis.dia.util.NodeUtil;

public class TestQueryBuilder {
	public static void main(String[] args) {
		
		Query query = QueryBuilder.create(new String[]{"s", "o", "p"})
				.addTriple(Var.alloc("s"), NodeFactory.parseNode("<http://nomisma.org/id/end_date>"), Var.alloc("o"))
				.openOptional()
				.addTriple(Var.alloc("s"), Var.alloc("p"), Var.alloc("o"))
				.addFilter(NodeUtil.createEqualsNode("o", 15, NodeType.INT))
				.closeOptional()
				.getQuery();
		
		System.out.println(query.serialize());
		
	}
	
	
}
