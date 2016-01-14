package zhaocai.service;

import java.net.URI;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import zhaocai.entity.ZcbDeal;
import zhaocai.repositories.ZcbDealDao;

import com.jayway.jsonpath.JsonPath;

@Service
public class ZcbDealDataTask implements Runnable
{
	static final Logger LOGGER = LoggerFactory.getLogger(ZcbDealDataTask.class);
	
	static final String DEAL_DATA_URL = "https://zcbprod.alipay.com/zcbDealData.json";
	
	@Autowired ZcbDealDao	zcbDealDataDao;
	@Autowired RestTemplate	restTemplate;

	@Override
	public void run()
	{
		ResponseEntity<String> resp = restTemplate.getForEntity(URI.create(DEAL_DATA_URL), String.class);
		int code = resp.getStatusCode().value();
		if (code > 199 && code < 300)
		{
			Map<String, Object> dataMap = JsonPath.read(resp.getBody(), "$.result");
			ZcbDeal data = new ZcbDeal();
			data.fetchTime = new java.sql.Date(System.currentTimeMillis());
			data.allEverAmtSum = Float.parseFloat((String) dataMap.get("allEverAmtSum"));
			data.appt7dAmtSum = Float.parseFloat((String) dataMap.get("appt7dAmtSum"));
			data.appt7dRate = (int) dataMap.get("appt7dRate");
			data.reportDate = (String) dataMap.get("reportDate");
			zcbDealDataDao.saveAndFlush(data);
		}

	}

	public static void main(String[] args)
	{
		RestTemplate restTemplate = new RestTemplate();
		String res = restTemplate.getForObject(URI.create("https://zcbprod.alipay.com/zcbDealData.json"), String.class);
		System.out.println(res);
	}
}
