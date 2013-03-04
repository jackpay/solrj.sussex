package solr.sussex.server;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer;
import org.apache.solr.core.CoreContainer;
import org.xml.sax.SAXException;

/**
 * Class for an in memory Solr EmbeddedServer. Follows Singleton pattern of Bill Pugh, Uni of Maryland.
 * @author jackpay
 *
 */
public class SingletonEmbeddedServer extends AbstractServerWrapper{
	
	private static SolrServer server;
	
	/**
	 * Private constructor ensures that the Singleton design pattern is adhered to.
	 */
	private static void SingletonEmbeddedServer(){
		CoreContainer.Initializer initializer = new CoreContainer.Initializer();
		CoreContainer coreContainer;
		try {
			coreContainer = initializer.initialize();
			server = new EmbeddedSolrServer(coreContainer, "");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Instantiates or returns the current instance of this class
	 * @return
	 */
	public static SingletonEmbeddedServer newInstance(){
		return EmbeddedServerHolder.INSTANCE;
	}

	/**
	 * Returns the SolrServer instance held by this wrapper class.
	 */
	@Override
	public SolrServer server() {
		return server;
	}
	
	/**
	 * Class which instantiates and holds the instance of its parent class.
	 * @author jackpay
	 */
	private static class EmbeddedServerHolder {
		public static final SingletonEmbeddedServer INSTANCE = new SingletonEmbeddedServer();
	}
}
