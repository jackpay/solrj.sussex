package solr.sussex.core.abstracts;

import java.util.HashMap;

import org.apache.solr.common.SolrInputDocument;

/**
 * Class to add extra functionality to the Solr class SolrInputDocument
 * @author jackpay
 *
 */
public abstract class AbstractSussexSolrDocument extends SolrInputDocument{
	
	private static final long serialVersionUID = 2017958183423603441L;
	private HashMap<String, String> fields; //
	private SolrInputDocument document;
	
	
	public SolrInputDocument getDocument(){
		return document;
	}
	
}
