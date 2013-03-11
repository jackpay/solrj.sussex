package solr.sussex.server;

public class SingletonServerFactory {
	
	private static AbstractServerWrapper server;
	private static SingletonServerFactory factory;
	
	
	private SingletonServerFactory(){}
	
	public static SingletonServerFactory getInstance(){
		return FactoryHolder.INSTANCE;
	}
	
	public <E extends AbstractServerWrapper> AbstractServerWrapper createServer(Class<E> cl){
		if(server == null){
			try {
				server = cl.newInstance();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return server;
	}
	
	public AbstractServerWrapper getServer(){
		return server;
	}
	
	private static class FactoryHolder{
		private static final SingletonServerFactory INSTANCE = new SingletonServerFactory();
	}

}
