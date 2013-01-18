import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.NodeIterator;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;


public class TestTurtle {
	
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		Model m = ModelFactory.createDefaultModel();
		
		String url = "http://www.w3.org/2012/pyRdfa/extract?uri=" + URLEncoder.encode("http://nomisma.org/id/augustus", "UTF-8");
		
		m = m.read(url, "TTL");
		
		Property p = m.getProperty("http://www.w3.org/2004/02/skos/core#prefLabel");
		
		NodeIterator iter = m.listObjectsOfProperty(p);
		
		while (iter.hasNext()) {
			RDFNode n = iter.next();;
			
			System.out.println(n.asLiteral().getString());
		}
		
		
	}
}
