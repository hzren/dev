package zhaocai.service;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import zhaocai.http.HttpServerInitializer;


@Component
public final class HttpServer {

	private static final Logger	LOGGER		= LoggerFactory.getLogger(HttpServer.class);

	private final EventLoopGroup	group		= new NioEventLoopGroup(1);
	private NioServerSocketChannel	ch;
	private int port = 8080;
	
	@Autowired HttpServerInitializer initializer;
	
	@PostConstruct
	public void init(){
		try
		{
			ServerBootstrap b = new ServerBootstrap();
			b.option(ChannelOption.SO_BACKLOG, 1024).option(ChannelOption.SO_TIMEOUT, 1000);
			b.group(group, group).channel(NioServerSocketChannel.class).handler(new LoggingHandler(LogLevel.INFO))
					.childHandler(initializer);
			ch = (NioServerSocketChannel) b.bind(port).sync().channel();
			LOGGER.info("Open your web browser and navigate to http://127.0.0.1:" + port + '/');
		} catch (Exception e)
		{
			LOGGER.error("start http server fail", e);
			shutDown();
			throw new RuntimeException(e);
		}
	}

	public NioServerSocketChannel getServerSocketChannel()
	{
		return ch;
	}

	public void shutDown()
	{
		group.shutdownGracefully();
	}
}
