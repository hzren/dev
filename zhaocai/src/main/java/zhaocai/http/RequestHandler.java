package zhaocai.http;

import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpRequest;

public interface RequestHandler {
	
	FullHttpResponse process(HttpRequest request);
	
}
