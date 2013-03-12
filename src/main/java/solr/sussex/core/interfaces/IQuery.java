package solr.sussex.core.interfaces;

import java.util.List;

public interface IQuery {
	
	public void setFields(List<String> fields);

	public void buildQuery();

	public void parseQuery(String query);

	public void executeQuery();
}
