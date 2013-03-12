package basic.local;

import java.util.List;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.JCommander;

import solr.sussex.core.abstracts.AbstractSolrController;
import solr.sussex.server.AbstractServerWrapper;


public abstract class AbstractLocalServerController extends AbstractSolrController{
	
	private static AbstractServerWrapper server;
	
	@Override
	public void initialise(String[] arg0) {
		SolrJCommander parser = new SolrJCommander();
		JCommander jc = new JCommander(parser, arg0);
		server = AbstractSolrController.getServer(parser.getServerType());
	}
	
	public static AbstractServerWrapper getServerWrapper(){
		return server;
	}
//	public static void main(String[] args) {
//		AbstractLocalServerController bls = new AbstractLocalServerController();
//		bls.initialise(args);
//	}
//
//	@Override
//	public void executeQuery() {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void setFields(List<String> arg0) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void buildQuery() {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void parseQuery(String query) {
//		// TODO Auto-generated method stub
//		
//	}
}