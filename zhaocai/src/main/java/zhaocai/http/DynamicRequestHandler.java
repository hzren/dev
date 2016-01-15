package zhaocai.http;

import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.QueryStringDecoder;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import zhaocai.Constants;

import com.google.gson.Gson;

@Service
@RequestMapping(value = DynamicRequestHandler.BASE)
public class DynamicRequestHandler extends RequestHandler {
	
	public static final String BASE = "/dynamic";

	private final HashMap<String, Method> mapping = new HashMap<String, Method>();
	
	@Autowired ProductQuery productQuery;
	
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
		String sub = new QueryStringDecoder(uri).path();
		Method m = mapping.get(sub);
		if (m == null) {
			return new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.NOT_FOUND);
		}
		try {
			byte[] data = (byte[]) m.invoke(this, request);
			return pack(data, "application/json");
		} catch (Exception e) {
			return new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.INTERNAL_SERVER_ERROR);
		}
	}
	/**
	 * time unit mark as DAY
	 * */
	@RequestMapping(value = "/yields/last")
	public byte[] periodData(HttpRequest request){
		String uri = request.getUri();
		QueryStringDecoder decoder = new QueryStringDecoder(uri);
		int days = Integer.parseInt(decoder.parameters().get("days").get(0));
		long startMils = System.currentTimeMillis() - Constants.DAY_MILS * days;
		Map<String, List<Object[]>> res = productQuery.queryYieldsAfter(startMils, 0);	
		return new Gson().toJson(res).getBytes();
	}
	
}
