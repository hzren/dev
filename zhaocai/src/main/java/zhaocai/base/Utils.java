package zhaocai.base;

public class Utils {
	
	public static float getNumber(String number){
		number = number.trim();
		int start = -1, end = -1;
		for (int i = 0; i < number.length(); i++) {
			char a = number.charAt(i);
			if (start == -1) {
				if (a < '0' || a > '9') {
					continue;
				}else {
					start = i;
				}
			}else {
				if (a == '.') {
					continue;
				}
				if (a < '0' || a > '9') {
					end = i;
					break;
				}
			}
		}
		return Float.parseFloat(number.substring(start, end));
	}
	
}
