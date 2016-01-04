package zhaocai.config.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SpiderConfig {
	@Value("${interval.alert:30}")
	public int alertInterval;
	
	@Value("${interval.query:600}")
	public int queryInterval;
}
