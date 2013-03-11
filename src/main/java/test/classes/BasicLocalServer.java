package test.classes;

import java.util.List;

import com.beust.jcommander.JCommander;

import solr.sussex.core.abstracts.AbstractSolrController;
import solr.sussex.server.AbstractServerWrapper;


public class BasicLocalServer extends AbstractSolrController{
	
	private static AbstractServerWrapper server;
	
	public static void main(String[] args) {
		System.err.println("here");
		BasicLocalServer bls = new BasicLocalServer();
		bls.initialise(args);
	}

	@Override
	public void buildQuery(String[] arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void executeQuery() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initialise(String[] arg0) {
		BasicSolrJCommander parser = new BasicSolrJCommander();
		JCommander jc = new JCommander(parser, arg0);
		server = AbstractSolrController.getServer(parser.getServerType());
	}

	@Override
	public AbstractServerWrapper serverContainer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setFields(List<String> arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public class BasicSolrJCommander extends SolrJCommander{}

}