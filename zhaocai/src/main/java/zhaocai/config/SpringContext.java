package zhaocai.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@ComponentScan("zhaocai.config.model")
@Configuration
@PropertySource(value = {"classpath:/application.properties", "file:C://application.properties"}, ignoreResourceNotFound = true)
class SpringContext
{
	@Bean
	public static PropertySourcesPlaceholderConfigurer placeHolderConfigurer()
	{
		return new PropertySourcesPlaceholderConfigurer();
	}
}