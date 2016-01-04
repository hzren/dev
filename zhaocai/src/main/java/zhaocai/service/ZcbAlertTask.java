package zhaocai.service;

import java.awt.Desktop;
import java.net.URI;
import java.net.URL;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import zhaocai.base.Utils;
import zhaocai.base.ZcbPeriod;
import zhaocai.config.Configuration;
import zhaocai.config.model.AlertConfig;
import zhaocai.entity.ZcbSubscribe;

@Service
public class ZcbAlertTask implements Runnable {
	
	static final Logger LOGGER = LoggerFactory.getLogger(ZcbDealDataTask.class);
    ZcbPeriod[] periods = ZcbPeriod.ZcbPeriods;

	@Override
	public void run() {
		float[] expected = Configuration.getConfiguration(AlertConfig.class).getExpected();
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
						String msg = "预约， 收益：" + subscribe.yields + ",周期 ：" + subscribe.period + ", 总成交额：" + subscribe.totalDeal;
						System.out.println(msg);
						LOGGER.info(msg);
					}
					
					Iterator<Element> iterator = elements.iterator();
					int index = 0;
					while (iterator.hasNext()) {
						Element element = iterator.next();
						float yields = Utils.getNumber(element.select(".w180 .f-18").first().text());
						boolean cashable = element.select(".w160 .period-cashing").first().child(0).text().trim().equals("可变现");
						String debtType = element.select(".w170").first().text().trim();
						int minNumber = (int)(Utils.getNumber(element.select(".w154").first().text()));
						int dealedNum = (int)(Utils.getNumber(element.select(".w123").first().text()));
						if (yields >= expected[i]) {
							openBrower(period.address);
							break;
						}
						index++;
						String msg = "index : " + index + "shouyi:" + yields + ",bianxian:" + cashable + ",leixing:" 
								+ debtType + ",zuishao:" + minNumber + ",yichengjiao:" + dealedNum;
						System.out.println(msg);
						LOGGER.info(msg);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				LOGGER.error("unexpected", e);
			} 
		}
	}
	
	void openBrower(String address){
        try {
            URI uri = new URI(address);
            Desktop.getDesktop().browse(uri);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("open " + address + "fail", e);
        }
	}

}
