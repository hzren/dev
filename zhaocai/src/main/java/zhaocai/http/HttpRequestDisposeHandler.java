package zhaocai.http;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import zhaocai.Constants;

import com.google.gson.Gson;

@Sharable
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Service
public class HttpRequestDisposeHandler extends SimpleChannelInboundHandler<HttpRequest> {
	
	private static final Logger	LOGGER	= LoggerFactory.getLogger(HttpRequestDisposeHandler.class);
	
	@Autowired ProductQuery productQuery;

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, HttpRequest msg)
			throws Exception {
		String path = msg.getUri();
		if (path.startsWith("/static")) {
			
		}else if (path.startsWith("/dynamic")) {
			long lastWeek = System.currentTimeMillis() - Constants.WEEK_MILS - Constants._10_MIN_MILS;
			
			Map<String, List<Object[]>> res = productQuery.queryYieldsAfter(lastWeek, 0);
			Gson gson = new Gson();
			byte[] bytes = gson.toJson(res).getBytes();
			ByteBuf body = ctx.alloc().buffer(bytes.length);
			DefaultFullHttpResponse resp = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, body.writeBytes(bytes));
			HttpHeaders.setContentLength(resp, bytes.length);
			HttpHeaders.setHeader(resp, HttpHeaders.Names.CONTENT_TYPE, "application/json;");
			ctx.writeAndFlush(resp);
		}else {
			//return index.html
			ClassPathResource index = new ClassPathResource("html/index.htm");
			if (index.exists()) {
				byte[] data = FileUtils.readFileToByteArray(index.getFile());
				writeBytes(data, ctx);
			}else {
				LOGGER.error("Not found html/index.htm in class path !!");
			}
		}
	}
	
	private void writeBytes(byte[] bytes, ChannelHandlerContext ctx){
		ByteBuf body = ctx.alloc().buffer(bytes.length);
		DefaultFullHttpResponse resp = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, body.writeBytes(bytes));
		HttpHeaders.setContentLength(resp, bytes.length);
		ctx.writeAndFlush(resp);
	}

}
