package zhaocai.http;

import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpRequest;

import java.lang.reflect.Method;
import java.util.HashMap;

import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = DynamicRequestHandler.BASE)
public class DynamicRequestHandler implements RequestHandler {
	
	public static final String BASE = "/dynamic";
	
	private final HashMap<String, Method> mapping = new HashMap<String, Method>();
	
	public DynamicRequestHandler() {
		RequestMapping base = getClass().getAnnotation(RequestMapping.class);
		String baseUri = base.value()[0];
		Method[] methods = getClass().getDeclaredMethods();
		for (int i = 0; i < methods.length; i++) {
			Method m = methods[i];
			RequestMapping sub = m.getAnnotation(RequestMapping.class);
			if (sub != null) {
				String subUri = sub.value()[0];
				mapping.put(baseUri + subUri, m);
			}
		}
	}
	
	@Override
	public final FullHttpResponse process(HttpRequest request){
		String uri = request.getUri();
		if (!uri.startsWith(BASE)) {
			throw new IllegalArgumentException("Only accept request start with :" + BASE);
		}
		String sub = uri.substring(BASE.length());
		Method m = mapping.get(sub);
		if (m == null) {
			
		}
		return null;
	}
	
}
