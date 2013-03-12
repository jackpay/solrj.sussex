package solr.sussex.core.abstracts;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import basic.local.QuerySolr;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;

//import org.apache.solr.client.solrj.SolrServer;

import solr.sussex.core.interfaces.IQuery;
import solr.sussex.server.AbstractServerWrapper;
//import solr.sussex.server.EmbeddedServer;
import solr.sussex.server.LocalServer;

/**
 * Abstract specifying the required basic structure for any implementation of a SolrController.
 * @author jp242
 *
 */
public abstract class AbstractSolrController {
	
	// Static strings and hashmap defining input options and subsequent classes/actions
	// Server options details.
	private static final String SERV_OP = "-s";
	private static final String LOCAL = "LOCAL";
	private static final String EMBED = "EMBED";
	private static final String REMOTE = "REMOTE";
	private static final String[] serverOpts = {LOCAL , EMBED, REMOTE};
	private static final HashMap<String, Class<? extends AbstractServerWrapper>> servers = 
			new HashMap<String, Class<? extends AbstractServerWrapper>>()
	{
		private static final long serialVersionUID = -2077191722979366826L;

		/**
		 * Allows instantiation with default values.
		 */
		{
			put(LOCAL, LocalServer.class);
			//put(EMBED, EmbeddedServer.class);
		}
	};
	private static final HashMap<String,String[]> Opts = new HashMap<String,String[]>()
	{
		private static final long serialVersionUID = -58898620105604514L;

		/**
		 * Allows instantiation with default values.
		 */
		{
			put(SERV_OP, serverOpts);
			put(REQ_OP, requestHandler);
		}
	};
	
	// Request handler details
	private static final String REQ_OP = "-r";
	private static final String MLT = "MLT";
	private static final String DISMAX = "DISMAX";
	private static final String[] requestHandler = {MLT};
	private static final HashMap<String, String> requestHandOps = new HashMap<String,String>()
	{
		private static final long serialVersionUID = -6518001874714814891L;

		{
			put(MLT, "mlt");
			put(DISMAX,"dismax");
		}
	};
	
	// Set request type i.e. add, query, delete etc...
	private static final String REQ_TYPE = "-q";
	private static final String QUERY = "QUERY";
	private static final String ADD = "ADD";
	private static final String DELETE = "DELETE";
	private static final String DELETE_ALL = "DELETE_ALL";
	private static final HashMap<String, Class<? extends IQuery>> queryTypes =
			new HashMap<String, Class<? extends IQuery>>()
	{
		private static final long serialVersionUID = 2207549085875431383L;

		{
			put(QUERY, QuerySolr.class);
		}
		
	};
	
	// Basic class level variables
	private static String currReqHandler = requestHandOps.get(DISMAX);
	
	
	/**
	 * Called by the main method of the calling class to start the server.
	 * @param args String representing the server required.
	 */
	public static AbstractServerWrapper getServer(String serv){
		try {
			return AbstractServerWrapper.getInstance(servers.get(serv));
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Called by the main method of the calling class to start the server. Allows inclusion of server address.
	 * @param serv Server type
	 * @param address Server address
	 * @return AbstractServerWrapper containing SolrServer
	 */
	public static AbstractServerWrapper getServer(String serv, String address){
		try {
			return AbstractServerWrapper.getInstance(servers.get(serv), address);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Called by the main method of the calling class to set the RequestHandler of the Server.
	 * @param reqHand
	 */
	public void setRequestHandler(String reqHand){
		currReqHandler = requestHandOps.get(reqHand);
	}
	
	/**
	 * Used by the main method to initialise the SolrController instance using input arguments
	 * @param args
	 */
	public abstract void initialise(String[] args);
	
	/**
	 * Sets the fields considered valid in the schema to the program.
	 * @param fields
	 */
	public abstract void setFields(List<String> fields);
	
	/**
	 * Allows a query to be specified and built and validated.
	 * @param query
	 */
	public abstract void buildQuery();
	
	/**
	 * Allows a raw query String to be parsed and correctly handled by the server.
	 */
	public abstract void parseQuery(String query);
	
	/**
	 * Executes a built query.
	 */
	public abstract void executeQuery();

	/**
	 * An class specifying the basic input parameters of any new SolrController instance
	 * @author jp242
	 *
	 */
	public static class SolrJCommander {
		
		@Parameter
	    private List<String> parameters = new ArrayList<String>();

		@Parameter
		(names = {REQ_OP, "--request"}, 
		description = "RequestHandler to use. Options include: ['MLT','DISMAX']. Default: 'DISMAX'",
		validateWith = ValidStringOption.class)
		private String reqH = DISMAX;
		
		@Parameter
		(names = {SERV_OP, "--server"},
		description = "Server type. Options include: ['LOCAL' , 'EMBED']. Default: 'LOCAL'",
		validateWith = ValidStringOption.class)
		private String serT = LOCAL;
		
		@Parameter
		(names = {REQ_TYPE, "--queryType"},
		description = "The query type of the server access request. Options include: ['QUERY', 'ADD', 'DELETE', 'DELETE_ALL']",
		validateWith = ValidStringOption.class,
		required = true)
		private String queryType = null;
		
		@Parameter
		(names = {"-i","--docLocation"},
		description = "Location of the document(s) wishing to be indexed.",
		validateWith = ValidFilePath.class)
		private String docPath;
		
		@Parameter
		(names = {"-a", "--address"},
		description = "Server address if not using a local or embedded server.")
		private String servAdd = null;
		
		@Parameter
		(names= {"-f", "--fields"},
		description = "Valid fields in Solr schema.xml. Call once for each field, e.g. -f arg1 -f arg2",
		required = true)
		private List<String> fields = new ArrayList<String>();
		
		public String getQueryType(){
			return queryType;
		}
		
		public String getServerType(){
			return serT;
		}
		
		public String getReqHandlerType(){
			return reqH;
		}
		
		public String getServerAddress(){
			return servAdd;
		}
		
		public ArrayList<String> getFields(){
			return (ArrayList<String>) fields;
		}

		/**
		 * Class for validating the input strings specified in the input parameters.
		 * @author jp242
		 *
		 */
		public static class ValidStringOption implements IParameterValidator{
			
			public ValidStringOption(){}
			
			public void validate(String name, String value)
					throws ParameterException {
				boolean valid = false;
				if(Opts.containsKey(name)){
					int i = 0;
					while(!valid && i < Opts.get(name).length){
						valid = (Opts.get(name)[i].equals(value));
						i++;
					}
					if(!valid){
						StringBuilder st = new StringBuilder("Parameter " + name + " must be one of options: ");
						throw new ParameterException(buildMessage(st, name));
					}
				}
				else{
					throw new ParameterException("Option specified does not exist as an option for validation.");
				}
			}
			
			public String buildMessage(StringBuilder st, String option){
				for(int i = 0; i < Opts.get(option).length; i++){
					st.append(Opts.get(option)[i]);
					if(i < Opts.get(option).length-1){
						st.append(" ");
					}
				}
				st.append(".");
				return st.toString();
			}
		}
		
		public static class ValidFilePath implements IParameterValidator{
			
			public ValidFilePath(){};

			public void validate(String name, String value)
					throws ParameterException {
				if(! new File(value).exists()){
					throw new ParameterException("The file path given as document(s) location does not exist.");
				}
			}
		}
	}
}
