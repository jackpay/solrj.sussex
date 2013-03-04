package solr.sussex.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;

import solr.sussex.core.abstracts.AbstractSussexSolrDocument;

/**
 * Abstract method which defines the main methods of the wrapper classes for different SolrServer instances.
 * @author jackpay
 *
 */
public abstract class AbstractServerWrapper {
	
	
	/**
	 * Gets the current instance/new instance of the inheriting class.
	 * @param cl Specified class required
	 * @return New or current instance of the requested class
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public static <E extends AbstractServerWrapper> E getInstance(Class<E> cl) throws InstantiationException, IllegalAccessException{
		return cl.newInstance();
	}
	
	/**
	 * Deletes all items held in the index.
	 */
	public void deleteAll(){
		try {
			server().deleteByQuery( "*:*" );
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Adds a document to the index. 
	 * @param doc Document to add
	 * @param commit Commit changes to index
	 */
	public void addDocument(AbstractSussexSolrDocument doc, boolean commit) {
		try {
			server().add(doc.getDocument());
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(commit){
			try {
				server().commit();
			} catch (SolrServerException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Adds a collection of documents to the index.
	 * @param docs Documents to add
	 * @param commit Commit changes to index
	 */
	public void addDocuments(List<AbstractSussexSolrDocument> docs, boolean commit){
		ArrayList<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for(int i = 0; i < docs.size(); i++){
			documents.add(docs.get(i).getDocument());
			if(commit && i % 100 == 0){ // Purges every 100 documents by committing to Solr DB.
				try {
					server().add(documents);
				} catch (SolrServerException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				documents = new ArrayList<SolrInputDocument>();
				try {
					server().commit();
				} catch (SolrServerException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		try {
			if(documents.size() > 0){
				server().add(documents);
				if(commit){
					try {
						server().commit();
					} catch (SolrServerException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Returns the current server instance contained in the inheriting wrapper class
	 * @return
	 */
	public abstract SolrServer server();
}
