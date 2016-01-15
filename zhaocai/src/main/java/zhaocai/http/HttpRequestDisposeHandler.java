package zhaocai.http;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Sharable
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Service
public class HttpRequestDisposeHandler extends SimpleChannelInboundHandler<HttpRequest> {
	
	private static final Logger	LOGGER	= LoggerFactory.getLogger(HttpRequestDisposeHandler.class);
	
	@Autowired DefaultRequestHandler defaultRequestHandler;
	@Autowired StaticRequestHandler staticRequestHandler;
	@Autowired DynamicRequestHandler dynamicRequestHandler;

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, HttpRequest msg)
			throws Exception {
		msg.setUri(QueryStringDecoder.decodeComponent(msg.getUri()));
		RequestHandler handler = getRequestHandler(msg);
		ctx.writeAndFlush(handler.process(msg));
	}
	
	private RequestHandler getRequestHandler(HttpRequest req){
		String path = req.getUri();
		LOGGER.debug(path);
		if (path.startsWith("/static")) {
			return staticRequestHandler;
		}else if (path.startsWith("/dynamic")) {
			return dynamicRequestHandler;
		}else {
			return defaultRequestHandler;
		}
	}

}
