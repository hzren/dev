package zhaocai.base;


public class ZcbPeriod {
	
	public static final ZcbPeriod[] ZcbPeriods = {new ZcbPeriod(0, 3), new ZcbPeriod(3, 6), 
													new ZcbPeriod(6, 12), new ZcbPeriod(12, 24), new ZcbPeriod(24, 9999)};

	public final int min;
	public final int max;
	public final String address;
	
	public ZcbPeriod(int min, int max) {
		this.max = max;
		this.min = min;
		this.address = "https://zhaocaibao.alipay.com/pf/productQuery.htm?pageNum=1&minMonth=" + min + "&maxMonth=" 
				+ max + "&minAmount=&danbao=1";
	}
}
