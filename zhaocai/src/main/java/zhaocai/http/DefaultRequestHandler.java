package zhaocai.http;

import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpRequest;

import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

@Service
public class DefaultRequestHandler extends RequestHandler {
	
	private static final Logger	LOGGER	= LoggerFactory.getLogger(DefaultRequestHandler.class);

	@Override
	public FullHttpResponse process(HttpRequest request) {
		ClassPathResource index = new ClassPathResource("html/index.htm");
		if (index.exists()) {
			byte[] data;
			try {
				data = FileUtils.readFileToByteArray(index.getFile());
				return pack(data, "text/html");
			} catch (IOException e) {
				LOGGER.error("read file error", e);
				throw new RuntimeException(e);
			}		
		}else {
			LOGGER.error("Not found html/index.htm in class path !!");
			throw new IllegalStateException("html/index.htm not found");
		}
	}

}
