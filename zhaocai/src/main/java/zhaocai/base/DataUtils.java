package zhaocai.base;

import java.util.LinkedList;
import java.util.List;

import org.springframework.data.domain.Page;

public class DataUtils {
	
	public static List<Object[]> getList(Page<? extends ZcbProduct> page){
		return getList(page.getContent());
	}
	
	public static List<Object[]> getList(List<? extends ZcbProduct> list){
		List<Object[]> resList = new LinkedList<>();
		for(ZcbProduct zcb : list){
			resList.add(new Object[]{zcb.time, zcb.yields});
		}	
		return resList;
	}
}
