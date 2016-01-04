package zhaocai.config;

import org.springframework.beans.factory.annotation.Value;

public class AlertConfig {
	@Value("${alert._0_3}") public float _0_3;	
	@Value("${alert._3_6}") public float _3_6;	
	@Value("${alert._6_12}") public float _6_12;	
	@Value("${alert._12_24}") public float _12_24;
	@Value("${alert._24_9999}") public float _24_9999;
	
	public float[] getExpected(){
		return new float[]{_0_3, _3_6, _6_12, _12_24, _24_9999};
	}
}
