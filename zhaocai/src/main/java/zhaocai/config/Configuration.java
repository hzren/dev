package zhaocai.config;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Configuration {
	
	public static <T> T getConfiguration(Class<T> type){
		AnnotationConfigApplicationContext ctx= new AnnotationConfigApplicationContext(SpringContext.class);
		T bean = ctx.getBean(type);
		ctx.close();
		return bean;
	}
}
