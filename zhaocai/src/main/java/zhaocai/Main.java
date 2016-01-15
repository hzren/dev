package zhaocai;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import zhaocai.repositories.ZcbProduct_0_3_Dao;
import zhaocai.repositories.ZcbProduct_12_24_Dao;
import zhaocai.repositories.ZcbProduct_24_9999_Dao;
import zhaocai.repositories.ZcbProduct_3_6_Dao;
import zhaocai.repositories.ZcbProduct_6_12_Dao;

public class Main
{

	public static void main(String[] args)
	{
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("zhaocai_db.xml");
		context.start();
		
		while(true){
			ZcbProduct_0_3_Dao _0_3_Dao = context.getBean(ZcbProduct_0_3_Dao.class);
			ZcbProduct_3_6_Dao _3_6_Dao = context.getBean(ZcbProduct_3_6_Dao.class);
			ZcbProduct_6_12_Dao _6_12_Dao = context.getBean(ZcbProduct_6_12_Dao.class);
			ZcbProduct_12_24_Dao _12_24_Dao = context.getBean(ZcbProduct_12_24_Dao.class);
			ZcbProduct_24_9999_Dao _24_9999_Dao = context.getBean(ZcbProduct_24_9999_Dao.class);
			String msg = "COUNT, 0-3:" + _0_3_Dao.count() + ",3-6:" + _3_6_Dao.count() + ",6-12:" + _6_12_Dao.count();
			msg += ",12-24:" + _12_24_Dao.count() + ",24-9999:" +_24_9999_Dao.count();
			System.out.println(msg);
			
			try {
				Thread.sleep(10l * 60 * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
				context.close();
				return;
			}
		}
		// TODO
	}
}
