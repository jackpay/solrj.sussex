package solr.sussex.core.abstracts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;

import org.apache.solr.client.solrj.SolrServer;

import solr.sussex.server.AbstractServerWrapper;
import solr.sussex.server.SingletonEmbeddedServer;
import solr.sussex.server.SingletonLocalServer;

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
	private static final String[] serverOpts = {LOCAL , EMBED};
	private static final HashMap<String, Class<? extends AbstractServerWrapper>> servers = 
			new HashMap<String, Class<? extends AbstractServerWrapper>>()
	{
		private static final long serialVersionUID = -2077191722979366826L;

		/**
		 * Allows instantiation with default values.
		 */
		{
			put(LOCAL, SingletonLocalServer.class);
			put(EMBED, SingletonEmbeddedServer.class);
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
	
	// Basic class level variables
	private static String currReqHandler = requestHandOps.get(DISMAX);
	private static AbstractServerWrapper server;
	
	
	/**
	 * Called by the main method of the calling class to start the server.
	 * @param args String representing the server required.
	 */
	public static void startServer(String serv){
		try {
			server = AbstractServerWrapper.getInstance(servers.get(serv));
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Called by the main method of the calling class to set the RequestHandler of the Server.
	 * @param reqHand
	 */
	public static void setRequestHandler(String reqHand){
		currReqHandler = requestHandOps.get(reqHand);
	}
	
	/**
	 * @return The current initialised instance containing the SolrServer.
	 */
	public static AbstractServerWrapper serverContainer(){
		return server;
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
	public abstract void setFields(String[] fields);
	
	/**
	 * Allows a query to be specified and built and validated.
	 * @param query
	 */
	public abstract void buildQuery(String[] query);
	
	/**
	 * Executes a built query.
	 */
	public abstract void executeQuery();

	/**
	 * An abstract specifying the basic input parameters of any new SolrController instance
	 * @author jp242
	 *
	 */
	public class SolrJCommander {
		
		@Parameter
	    private List<String> parameters = new ArrayList<String>();
		
		@Parameter
		(names = {"-a", "--address"},
		description = "Server address if not using a local or embedded server.")
		private String servAdd = null;

		@Parameter
		(names = {REQ_OP, "--request"}, 
		description = "RequestHandler to use. Options include: ['MLT']. Default: DISMAX",
		required = true,
		validateWith = ValidStringOption.class)
		private String reqH = DISMAX;
		
		@Parameter
		(names = {SERV_OP, "--server"},
		description = "Server type. Options include: ['LOCAL' , 'EMBED']",
		required = true,
		validateWith = ValidStringOption.class)
		private String serT = LOCAL;
		
		@Parameter
		(names= {"-f", "--fields"},
		description = "Valid fields in Solr schema.xml. Call once for each field, e.g. -f arg1 -f arg2",
		required = true)
		private List<String> fields = new ArrayList<String>();
		
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
		public class ValidStringOption implements IParameterValidator{

			public void validate(String option, String value)
					throws ParameterException {
				boolean valid = false;
				if(Opts.containsKey(option)){
					int i = 0;
					while(!valid && i < Opts.get(option).length){
						valid = (Opts.get(option)[i].equals(value));
						i++;
					}
					if(!valid){
						StringBuilder st = new StringBuilder("Parameter " + option + " must be one of options: ");
						throw new ParameterException(buildMessage(st, option));
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
	}
}
