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

import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import zhaocai.Constants;
import zhaocai.base.DataUtils;
import zhaocai.entity.ZcbProduct_0_3;
import zhaocai.entity.ZcbProduct_12_24;
import zhaocai.entity.ZcbProduct_24_9999;
import zhaocai.entity.ZcbProduct_3_6;
import zhaocai.entity.ZcbProduct_6_12;
import zhaocai.repositories.ZcbProduct_0_3_Dao;
import zhaocai.repositories.ZcbProduct_12_24_Dao;
import zhaocai.repositories.ZcbProduct_24_9999_Dao;
import zhaocai.repositories.ZcbProduct_3_6_Dao;
import zhaocai.repositories.ZcbProduct_6_12_Dao;
import zhaocai.repositories.ZcbSubscribeDao;

import com.google.gson.Gson;

@Sharable
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Service
public class HttpRequestDisposeHandler extends SimpleChannelInboundHandler<HttpRequest> {
	
	private static final Logger	LOGGER	= LoggerFactory.getLogger(HttpRequestDisposeHandler.class);
	
	@Autowired ZcbProduct_0_3_Dao _0_3_Dao;
	@Autowired ZcbProduct_3_6_Dao _3_6_Dao;
	@Autowired ZcbProduct_6_12_Dao _6_12_Dao;
	@Autowired ZcbProduct_12_24_Dao _12_24_Dao;
	@Autowired ZcbProduct_24_9999_Dao _24_9999_Dao;
	@Autowired ZcbSubscribeDao zcbSubscribeDao;

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, HttpRequest msg)
			throws Exception {
		String path = msg.getUri();
		if (path.startsWith("/resources")) {
			
		}else if (path.startsWith("/dynamic")) {
			long lastWeek = System.currentTimeMillis() - Constants.WEEK_MILS - Constants._10_MIN_MILS;
			HashMap<String, List<Object[]>> res = new HashMap<>();		
			
			List<ZcbProduct_0_3> _0_3Page = _0_3_Dao.findByIndexAndTimeGreaterThan(0, lastWeek);
			res.put("_0_3", DataUtils.getList(_0_3Page));
			
			List<ZcbProduct_3_6> _3_6Page = _3_6_Dao.findByIndexAndTimeGreaterThan(0, lastWeek);
			res.put("_3_6", DataUtils.getList(_3_6Page));
			
			List<ZcbProduct_6_12> _6_12Page = _6_12_Dao.findByIndexAndTimeGreaterThan(0, lastWeek);
			res.put("_6_12", DataUtils.getList(_6_12Page));
			
			List<ZcbProduct_12_24> _12_24Page = _12_24_Dao.findByIndexAndTimeGreaterThan(0, lastWeek);
			res.put("_12_24", DataUtils.getList(_12_24Page));
			
			List<ZcbProduct_24_9999> _24_9999Page = _24_9999_Dao.findByIndexAndTimeGreaterThan(0, lastWeek);
			res.put("_24_9999", DataUtils.getList(_24_9999Page));
			
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
