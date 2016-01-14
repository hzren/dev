package zhaocai.http;

import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpRequest;

import java.lang.reflect.Method;
import java.util.HashMap;

public class DynamicRequestHandler {
	
	public static final String BASE = "/dynamic";
	
	private final HashMap<String, Method> mapping = new HashMap<String, Method>();
	
	public DynamicRequestHandler() {
	}
	
	public DefaultFullHttpResponse process(HttpRequest msg){
		String uri = msg.getUri();
		return null;
	}
	
}
