package zhaocai.service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import zhaocai.config.Configuration;
import zhaocai.config.model.SpiderConfig;

@Component
public class ZcbSpiderComponent implements Runnable
{
	private static final ScheduledExecutorService	EXECUTOR	= Executors.newScheduledThreadPool(2);

	@Autowired ZcbDealDataTask dealDataTask;	
	@Autowired ZcbProductQueryTask productQueryTask;
	@Autowired ZcbAlertTask alaertTask;

	@PostConstruct
	public void init()
	{
		long initialDelay = 10l;
		int alertInterval = Configuration.getConfiguration(SpiderConfig.class).alertInterval;
		EXECUTOR.schedule(this, initialDelay, TimeUnit.SECONDS);
		EXECUTOR.scheduleAtFixedRate(alaertTask, initialDelay, alertInterval, TimeUnit.SECONDS);
	}

	@Override
	public void run() {
		long start = System.currentTimeMillis();
		try {
			dealDataTask.run();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			productQueryTask.run();
		} catch (Exception e) {
			e.printStackTrace();
		}
		long cost = System.currentTimeMillis() - start;
		System.out.println("cost : " + cost);
		
		//schedule next
		int seconds = Configuration.getConfiguration(SpiderConfig.class).queryInterval;
		long interval = seconds * 1000;
		ZcbSpiderComponent.EXECUTOR.schedule(this, interval - cost , TimeUnit.MILLISECONDS);
	}
	
	
}
