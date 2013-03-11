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
	
	private static final String SERV_ADD = "http://localhost:8983/solr/";
	private static HttpSolrServer server;
	
	/**
	 * Private constructor ensures Singleton design patter is adhered to.
	 * @throws IOException 
	 * @throws SolrServerException 
	 */
	public LocalServer() throws SolrServerException, IOException{
		//System.err.println("goose");
		try{
			server = new HttpSolrServer(SERV_ADD);
			server.setParser(new XMLResponseParser());
			server.setConnectionTimeout(5000);
			server.setSoTimeout(1000);
		}
		catch (Exception exp){
			exp.printStackTrace();
		}
		ModifiableSolrParams params = new ModifiableSolrParams();
		params.set("q", "*:*");
		QueryResponse qr = server.query(params);
		//server.ping();
		//System.err.println(qr.getResults().get(0).getFieldValue("id"));

		//server.getBaseURL();
		//server.ping();
//		server.add(new SolrInputDocument());
//		server.commit();
//		HelloRunnable ng = new HelloRunnable();
//		ng.run();
		//server.ping();
		//server.shutdown();
	}
	
	public class HelloRunnable extends Thread{

		public void run() {
			try {
				Thread.sleep(20000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.err.println("Waited 10 secs");
		}
		
		public void main(String args[]) {
	        (new HelloRunnable()).start();
	    }
	}

//	/**
//	 * @return New or current instance of this class
//	 */
//	public static SingletonLocalServer getInstance(){
//		return LocalServerHolder.INSTANCE;
//	}
	
	/**
	 * The SolrServer instance held in the wrapper.
	 */
	@Override
	public SolrServer server(){
		return server;
	}

//	/**
//	 * Class which holds and instantiates the parent class: SingletonLocalServer.
//	 * @author jackpay
//	 */
//	private static class LocalServerHolder{
//		private static final SingletonLocalServer INSTANCE = new SingletonLocalServer();
//	}
}
