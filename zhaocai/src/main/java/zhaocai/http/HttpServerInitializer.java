package zhaocai.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Service
public class HttpServerInitializer extends ChannelInitializer<SocketChannel> {
	
	private HttpRequestDisposeHandler disposeHandler;

	@Autowired
    public HttpServerInitializer(HttpRequestDisposeHandler disposeHandler) {
		this.disposeHandler = disposeHandler;
    }

    @Override
    public void initChannel(SocketChannel ch) {
        ChannelPipeline p = ch.pipeline();
        p.addLast(new HttpServerCodec());
        //we only handle get request, so only need handle http request header
        p.addLast(disposeHandler);
    }
}
