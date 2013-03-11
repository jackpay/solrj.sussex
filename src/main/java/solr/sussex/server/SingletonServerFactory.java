package solr.sussex.server;

public class SingletonServerFactory {
	
	private static AbstractServerWrapper server;
	
	private SingletonServerFactory(){}
	
	public static SingletonServerFactory getInstance(){
		return FactoryHolder.INSTANCE;
	}
	
	public static <E extends AbstractServerWrapper> AbstractServerWrapper getServer(Class<E> cl){
		getInstance();
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
	
	private static class FactoryHolder{
		private static final SingletonServerFactory INSTANCE = new SingletonServerFactory();
	}

}
