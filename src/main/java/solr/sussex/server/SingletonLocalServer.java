package solr.sussex.server;

import java.net.MalformedURLException;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;

/**
 * Class for an Solr LocalSever. Follows Singleton pattern of Bill Pugh, Uni of Maryland.
 * @author jackpay
 *
 */
public class SingletonLocalServer extends AbstractServerWrapper{
	
	private static final String SERV_ADD = "http://localhost:8983/solr/";
	private static SolrServer server;
	
	/**
	 * Private constructor ensures Singleton design patter is adhered to.
	 */
	private static void SingletonLocalServer(){
		try {
			server = new CommonsHttpSolrServer(SERV_ADD);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return New or current instance of this class
	 */
	public static SingletonLocalServer getInstance(){
		return LocalServerHolder.INSTANCE;
	}
	
	/**
	 * The SolrServer instance held in the wrapper.
	 */
	@Override
	public SolrServer server(){
		return server;
	}

	/**
	 * Class which holds and instantiates the parent class: SingletonLocalServer.
	 * @author jackpay
	 */
	private static class LocalServerHolder{
		private static final SingletonLocalServer INSTANCE = new SingletonLocalServer();
	}
}
