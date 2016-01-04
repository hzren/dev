package zhaocai;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import zhaocai.repositories.ZcbProduct_0_3_Dao;
import zhaocai.repositories.ZcbProduct_12_24_Dao;
import zhaocai.repositories.ZcbProduct_24_9999_Dao;
import zhaocai.repositories.ZcbProduct_3_6_Dao;
import zhaocai.repositories.ZcbProduct_6_12_Dao;

public class Main
{
	private static ApplicationContext	CONTEXT;

	public static void main(String[] args)
	{
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("zhaocai_db.xml");
		CONTEXT = context;
		
		while(true){
			ZcbProduct_0_3_Dao _0_3_Dao = context.getBean(ZcbProduct_0_3_Dao.class);
			System.out.println("0-3 yue : " + _0_3_Dao.count());
			
			ZcbProduct_3_6_Dao _3_6_Dao = context.getBean(ZcbProduct_3_6_Dao.class);
			System.out.println("3-6 yue : " + _3_6_Dao.count());
			
			ZcbProduct_6_12_Dao _6_12_Dao = context.getBean(ZcbProduct_6_12_Dao.class);
			System.out.println("6-12 yue : " + _6_12_Dao.count());
			
			ZcbProduct_12_24_Dao _12_24_Dao = context.getBean(ZcbProduct_12_24_Dao.class);
			System.out.println("12_24 yue : " + _12_24_Dao.count());
			
			ZcbProduct_24_9999_Dao _24_9999_Dao = context.getBean(ZcbProduct_24_9999_Dao.class);
			System.out.println("0-3 yue : " + _24_9999_Dao.count());
			
			try {
				Thread.sleep(10l * 60 * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		// TODO
	}
}
