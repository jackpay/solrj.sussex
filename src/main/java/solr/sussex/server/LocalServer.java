package solr.sussex.server;

import java.io.IOException;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.ModifiableSolrParams;

/**
 * Class for an Solr LocalSever. Follows Singleton pattern of Bill Pugh, Uni of Maryland.
 * @author jackpay
 *
 */
public class LocalServer extends AbstractServerWrapper{
	
	private static HttpSolrServer server;
	
	/**
	 * @throws IOException 
	 * @throws SolrServerException 
	 */
	public LocalServer() throws SolrServerException, IOException{
		super();
		LocalServer.setServer();
	}
	
	public LocalServer(String address){
		super(address);
		LocalServer.setServer();
	}
	
	/**
	 * The SolrServer instance held in the wrapper.
	 */
	@Override
	public SolrServer server(){
		return server;
	}
	
	private static void setServer(){
		try{
			if(server == null){
				server = new HttpSolrServer(getAddress());
				server.setConnectionTimeout(5000);
				server.setSoTimeout(1000);
				System.err.println(server.ping());
			}
		}
		catch (Exception exc){
			exc.printStackTrace();
		}
	}
}
