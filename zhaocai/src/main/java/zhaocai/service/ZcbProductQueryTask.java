package zhaocai.service;

import java.net.URL;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zhaocai.base.Utils;
import zhaocai.base.ZcbPeriod;
import zhaocai.base.ZcbProduct;
import zhaocai.entity.ZcbProduct_0_3;
import zhaocai.entity.ZcbProduct_12_24;
import zhaocai.entity.ZcbProduct_24_9999;
import zhaocai.entity.ZcbProduct_3_6;
import zhaocai.entity.ZcbProduct_6_12;
import zhaocai.entity.ZcbSubscribe;
import zhaocai.repositories.ZcbProductDao;
import zhaocai.repositories.ZcbProduct_0_3_Dao;
import zhaocai.repositories.ZcbProduct_12_24_Dao;
import zhaocai.repositories.ZcbProduct_24_9999_Dao;
import zhaocai.repositories.ZcbProduct_3_6_Dao;
import zhaocai.repositories.ZcbProduct_6_12_Dao;
import zhaocai.repositories.ZcbSubscribeDao;

@Service
public class ZcbProductQueryTask implements Runnable{
	
	static final Logger LOGGER = LoggerFactory.getLogger(ZcbProductQueryTask.class);
	
	ZcbPeriod[] periods = ZcbPeriod.ZcbPeriods;
		
	@Autowired ZcbProduct_0_3_Dao _0_3_Dao;	
	@Autowired ZcbProduct_3_6_Dao _3_6_Dao;	
	@Autowired ZcbProduct_6_12_Dao _6_12_Dao;	
	@Autowired ZcbProduct_12_24_Dao _12_24_Dao;	
	@Autowired ZcbProduct_24_9999_Dao _24_9999_Dao;	
	@Autowired ZcbSubscribeDao zcbSubscribeDao;
	
	@Override
	public void run() {
		long now = System.currentTimeMillis();
		for (int i = 0; i < periods.length; i++) {
			ZcbPeriod period = periods[i];
			try {
				String address = period.address;
				Document document = Jsoup.parse(new URL(address), 10000);
				Elements elements = document.select(".icontent-ul");
				if (elements.size() != 0) {
					Element first = elements.get(0);
					if (first.hasClass("sold")) {
						ZcbSubscribe subscribe = new ZcbSubscribe();
						subscribe.period = period.max;
						subscribe.yields = Utils.getNumber(first.select(".w180 .book-li-2").first().text());
						String total = first.select(".w160 .total-amount").first().child(0).text().trim();
						subscribe.totalDeal = Utils.getNumber(total);
						elements.remove(0);
						zcbSubscribeDao.saveAndFlush(subscribe);
						
						System.out.println("预约， 收益：" + subscribe.yields + ",周期 ：" + subscribe.period + ", 总成交额：" + subscribe.totalDeal);
					}
					Iterator<Element> iterator = elements.iterator();
					int index = 0;
					while (iterator.hasNext()) {
						Element element = iterator.next();
						ZcbProductEntityDao<?> entityDao = getZcbProductEntityAndDao(period);
						ZcbProduct product = entityDao.zcbProduct;
						product.yields = Utils.getNumber(element.select(".w180 .f-18").first().text());
						product.cashable = element.select(".w160 .period-cashing").first().child(0).text().trim().equals("可变现");
						product.debtType = element.select(".w170").first().text().trim();
						product.minNumber = (int)(Utils.getNumber(element.select(".w154").first().text()));
						product.dealedNum = (int)(Utils.getNumber(element.select(".w123").first().text()));
						product.index = index++;
						product.time = now;
						entityDao.save();
						
						System.out.println("shouyi:" + product.yields + ",bianxian:" + product.cashable + ",leixing:" 
								+ product.debtType + ",zuishao:" + product.minNumber + ",yichengjiao:" + product.dealedNum);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				LOGGER.error("unexpected", e);
			} 
		}
	}
	
	static class ZcbProductEntityDao<T extends ZcbProduct>{
		final T zcbProduct;
		final ZcbProductDao<T> zcbProductDao;
		
		public ZcbProductEntityDao(T zcbProduct, ZcbProductDao<T> zcbProductDao) {
			this.zcbProduct = zcbProduct;
			this.zcbProductDao = zcbProductDao;
		}
		
		void save(){
			zcbProductDao.save(zcbProduct);
		}
	}
	
	ZcbProductEntityDao<?> getZcbProductEntityAndDao(ZcbPeriod period){
		switch (period.min) {
		case 0:
			return new ZcbProductEntityDao<ZcbProduct_0_3>(new ZcbProduct_0_3(), _0_3_Dao);
			
		case 3:
			return new ZcbProductEntityDao<ZcbProduct_3_6>(new ZcbProduct_3_6(), _3_6_Dao);
			
		case 6:
			return new ZcbProductEntityDao<ZcbProduct_6_12>(new ZcbProduct_6_12(), _6_12_Dao);
			
		case 12:
			return new ZcbProductEntityDao<ZcbProduct_12_24>(new ZcbProduct_12_24(), _12_24_Dao);
			
		case 24:
			return new ZcbProductEntityDao<ZcbProduct_24_9999>(new ZcbProduct_24_9999(), _24_9999_Dao);

		default:
			throw new IllegalArgumentException("unknow period min value : " + period.max);
		}
	}

}
