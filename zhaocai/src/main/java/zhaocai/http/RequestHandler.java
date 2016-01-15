package zhaocai.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

public abstract class RequestHandler {
	
	abstract FullHttpResponse process(HttpRequest request);
	
	public static final FullHttpResponse pack(byte[] bytes, String contentType){
		ByteBuf body = Unpooled.buffer(bytes.length);
		DefaultFullHttpResponse resp = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, body.writeBytes(bytes));
		HttpHeaders.setContentLength(resp, bytes.length);
		if (contentType != null) {
			HttpHeaders.setHeader(resp, HttpHeaders.Names.CONTENT_TYPE, contentType);
		}
		return resp;
	}
	
}
