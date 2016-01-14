package zhaocai.http;

import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpRequest;

import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = StaticRequestHandler.BASE)
public class StaticRequestHandler implements RequestHandler {
	
	public static final String BASE = "/static";
	
	public StaticRequestHandler() {
		super();
	}

	@Override
	public FullHttpResponse process(HttpRequest request) {
		String uri = request.getUri();
		if (!uri.startsWith(BASE)) {
			throw new IllegalArgumentException("only accept request start with : " + BASE);
		}
		//TODO use spring static request handler
		return null;
	}
}
