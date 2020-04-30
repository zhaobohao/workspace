package org.springclouddev.integral.aviator.util;

public class MyDayOfYear {

	private final static int[] monthIsCommon = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
	private final static int[] monthIsIntercalary = { 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
	
	public static int getDayOfYear(int year, int month, int day) {
		int dayOfYear = 0;
		if((year%100 != 0&&year%4==0)||(year%100==0&&year%400==0)) {
			//闰月
			for (int i = 0; i < month-1; i++) {
				dayOfYear += monthIsIntercalary[i];
			}
		}else {
			//非闰月
			for (int i = 0; i < month-1; i++) {
				dayOfYear += monthIsCommon[i];
			}
		}
		dayOfYear += day;
		return dayOfYear;
	}
//	public static void main(String[] args) {
//		Calendar d = Calendar.getInstance();
//		d.set(2000, 9, 11);
//		System.out.println(d.get(Calendar.DAY_OF_YEAR));
//		System.out.println(getDayOfYear(2000, 10, 11));
//	}
}
