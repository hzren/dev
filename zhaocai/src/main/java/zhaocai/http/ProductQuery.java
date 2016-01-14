package zhaocai.http;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zhaocai.base.DataUtils;
import zhaocai.entity.ZcbProduct_0_3;
import zhaocai.entity.ZcbProduct_12_24;
import zhaocai.entity.ZcbProduct_24_9999;
import zhaocai.entity.ZcbProduct_3_6;
import zhaocai.entity.ZcbProduct_6_12;
import zhaocai.repositories.ZcbProduct_0_3_Dao;
import zhaocai.repositories.ZcbProduct_12_24_Dao;
import zhaocai.repositories.ZcbProduct_24_9999_Dao;
import zhaocai.repositories.ZcbProduct_3_6_Dao;
import zhaocai.repositories.ZcbProduct_6_12_Dao;
import zhaocai.repositories.ZcbSubscribeDao;

@Service
public class ProductQuery {

	@Autowired ZcbProduct_0_3_Dao _0_3_Dao;
	@Autowired ZcbProduct_3_6_Dao _3_6_Dao;
	@Autowired ZcbProduct_6_12_Dao _6_12_Dao;
	@Autowired ZcbProduct_12_24_Dao _12_24_Dao;
	@Autowired ZcbProduct_24_9999_Dao _24_9999_Dao;
	@Autowired ZcbSubscribeDao zcbSubscribeDao;
	
	public Map<String, List<Object[]>> queryYieldsAfter(long start, int index){
		HashMap<String, List<Object[]>> res = new HashMap<>();		
		
		List<ZcbProduct_0_3> _0_3Page = _0_3_Dao.findByIndexAndTimeGreaterThan(index, start);
		res.put("_0_3", DataUtils.getList(_0_3Page));
		
		List<ZcbProduct_3_6> _3_6Page = _3_6_Dao.findByIndexAndTimeGreaterThan(index, start);
		res.put("_3_6", DataUtils.getList(_3_6Page));
		
		List<ZcbProduct_6_12> _6_12Page = _6_12_Dao.findByIndexAndTimeGreaterThan(index, start);
		res.put("_6_12", DataUtils.getList(_6_12Page));
		
		List<ZcbProduct_12_24> _12_24Page = _12_24_Dao.findByIndexAndTimeGreaterThan(index, start);
		res.put("_12_24", DataUtils.getList(_12_24Page));
		
		List<ZcbProduct_24_9999> _24_9999Page = _24_9999_Dao.findByIndexAndTimeGreaterThan(index, start);
		res.put("_24_9999", DataUtils.getList(_24_9999Page));
		
		return res;
	}
}
