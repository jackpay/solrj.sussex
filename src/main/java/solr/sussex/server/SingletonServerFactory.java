package solr.sussex.server;

import java.lang.reflect.InvocationTargetException;

public class SingletonServerFactory {
	
	private static AbstractServerWrapper server;
	
	private SingletonServerFactory(){}
	
	public static SingletonServerFactory getInstance(){
		return FactoryHolder.INSTANCE;
	}
	
	/**
	 * Used to create server with predefined local address.
	 * @param cl
	 * @return
	 */
	public static <E extends AbstractServerWrapper> AbstractServerWrapper getServer(Class<E> cl){
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
	
	/**
	 * Used to create server with user specified address.
	 * @param cl
	 * @param address
	 * @return
	 */
	public static <E extends AbstractServerWrapper> AbstractServerWrapper getServer(Class<E> cl, String address){
		if(server == null){
			try {
				try {
					server = cl.getDeclaredConstructor(cl).newInstance(address);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
