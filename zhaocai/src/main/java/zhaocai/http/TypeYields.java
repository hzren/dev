package zhaocai.http;

import java.util.LinkedList;
import java.util.List;


public class TypeYields {
	
	public final String type;
	public final List<List<Object>> yileds = new LinkedList<List<Object>>();
	
	public TypeYields(String type) {
		this.type = type;
	}
}
