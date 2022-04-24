package quantitative_analysis01;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class BasicTools {
	
	// read CSV file that every line is separated by ","
	// parse each line and store in Object: ItemData
	public static List<ItemData> read_csv(String path, Set<String> stock_names) {
		try {
			int counter_all = 0; 
			List<ItemData> id_list = new ArrayList<ItemData>();
			
			@SuppressWarnings("resource")
			BufferedReader in = new BufferedReader(new FileReader(path));
			String str;
			while ((str = in.readLine()) != null) {
				String str_update = str.strip();
				ItemData id = new ItemData(str_update);
				id_list.add(id);
				
				// record all stock-names
				stock_names.add(id.getStock_name());
				
				counter_all ++;
				
				// the file contains 10+ million records/rows, 
				// while for testing, only read 1 million records
				/*
				if(counter_all>1000*1000) {
					break;
				}
				 */
            }
			System.out.println("total records read:\t" + counter_all);
			
			return id_list;
		}
		catch(Exception e) {
			System.out.print("cannot read csv file: ");
			System.out.println(path);
			e.printStackTrace();
		}
		return null;
	}
	
	// filter by stock-name, namely, stock by stock
	public static List<ItemData> filter_by_stock(List<ItemData> id_list, String name) {
		List<ItemData> tmp_list = new ArrayList<ItemData>();
		int i = 0;
		while(i<id_list.size()) {
			ItemData it = id_list.get(i);
			if(it!=null) {
				if(it.getStock_name().equals(name)) {
					tmp_list.add(it);
				}
			}
			i++;
		}
		return tmp_list;
	}
	
	// filter by trade, namely, update-type == 1
	public static List<ItemData> filter_by_trade(List<ItemData> id_list){
		List<ItemData> tmp_list = new ArrayList<ItemData>();
		int i = 0;
		while(i<id_list.size()) {
			ItemData it = id_list.get(i);
			if(it!=null) {
				if(it.getUpdate_type()==1) {
					tmp_list.add(it);
				}
			}
			i++;
		}
		return tmp_list;
	}
	
	// filter by tick change - bid/ask price different from the previous ones
	public static List<ItemData> filter_by_tickchange(List<ItemData> id_list){
		List<ItemData> tmp_list = new ArrayList<ItemData>();
		float previous_bid_price = id_list.get(0).getBid_price();
		float previous_ask_price = id_list.get(0).getAsk_price();
		int i = 1;
		while(i<id_list.size()) {
			ItemData it = id_list.get(i);
			if(it!=null) {
				float current_bid_price = id_list.get(i).getBid_price();
				float current_ask_price = id_list.get(i).getAsk_price();
				if( previous_bid_price != current_bid_price || previous_ask_price != current_ask_price ) {
					tmp_list.add(it);
				}
				previous_bid_price = current_bid_price;
				previous_ask_price = current_ask_price;
			}
			i++;
		}
		return tmp_list;
	}
	
	// Date & Time in seconds past midnight -> time stamp 
	public static long timestamp_process(String date_str, float time_second) {
		int second_num = (int)time_second;
		int h = second_num / (60*60);
		int m = (second_num-60*60*h) / 60;
		int s = second_num-60*60*h-60*m;
		
		String time_str = date_str.substring(0, 4) + "-" + date_str.substring(4, 6) + "-" + date_str.substring(6, 8);
		time_str = time_str + " ";
		if(h<10)
		    time_str = time_str + "0" + h + ":";
		else
		    time_str = time_str + h + ":";
		if(m<10)
			time_str = time_str + "0" + m + ":";
		else
		    time_str = time_str + m + ":";
		if(s<10)
		    time_str = time_str + "0" + s;
		else
		    time_str = time_str + s;
		
		
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = simpleDateFormat.parse(time_str);
			long timeStamp = date.getTime();
			//String sd = simpleDateFormat.format(  new Date(Long.parseLong(String.valueOf(timeStamp))));
			//System.out.println("\t" + sd);
			return timeStamp;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	// calculate mean time (time stamp -> date)
	public static String cal_time_mean(List<Long> time_list) {
		if(time_list.size()==0) {
			return "0000-00-00 00:00:00";
		}
		long sum = 0;
		int i = 0;
		while(i<time_list.size()) {
			sum = sum + time_list.get(i);
			i++;
		}
		long avg = (long)sum/time_list.size();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sd = simpleDateFormat.format(new Date(Long.parseLong(String.valueOf(avg))));
		return sd;
	}
	
	// calculate median time (time stamp -> date)
	public static String cal_time_median(List<Long> time_list) {
		if(time_list.size()==0) {
			return "0000-00-00 00:00:00";
		}
		Collections.sort(time_list);
		int L = time_list.size();
		long ts = time_list.get(L/2);
		if(L%2==0) {
			ts = (time_list.get(L/2 - 1) + time_list.get(L/2))/2;
		}
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sd = simpleDateFormat.format(new Date(Long.parseLong(String.valueOf(ts))));
		return sd;
	}
	
	// get time column: Date & Time in seconds past midnight
	public static List<Long> get_time_list(List<ItemData> id_list) {
		//Set<String> tmp_set = new HashSet<String>();
		List<Long> tmp_list = new ArrayList<Long>();
		int i = 0;
		while(i<id_list.size()) {
			ItemData it = id_list.get(i);
			if(it!=null) {
				String date_str = it.getDate_str(); // date, such as: 20150422
				float time_second = it.getTime_second(); // Time in seconds past midnight, such as: 28264.0
				long timeStamp = timestamp_process(date_str, time_second);
				tmp_list.add(timeStamp); // here the time-stamp list is ascending order by time
			}
			i++;
		}
		
		return tmp_list;
	}
	
	// get time interval 
	public static long get_longest_time(List<Long> time_col){
		if(time_col.size()==0) {
			return 0;
		}
		
		long Longest = 0;
		long previous_time_stamp = time_col.get(0);
		int i = 1;
		while(i<time_col.size()) {
			long current_time_stamp = time_col.get(i);
			long interval = current_time_stamp - previous_time_stamp; // unit: Microsecond
			if(Longest < interval) {
				Longest = interval;
			}
			previous_time_stamp = current_time_stamp;
			i++;
		}
		return Longest/1000; // microsecond -> second
	}
	
	// get bid/ask spread
	public static List<Float> get_spread_list(List<ItemData> id_list){
		List<Float> tmp_list = new ArrayList<Float>();
		int i = 0;
		while(i<id_list.size()) {
			ItemData it = id_list.get(i);
			if(it!=null) {
				// absolute value
				float spread = it.getBid_price() - it.getAsk_price();
				if(spread<0) {
					spread = -spread;
				}
				tmp_list.add(spread);
			}
			i++;
		}
		return tmp_list;
	}
	
	// get mean spread
	public static float get_spread_mean(List<Float> spread_list) {
		if(spread_list.size()==0) {
			return 0;
		}
		
		float m = 0;
		int i = 0;
		while(i<spread_list.size()) {
			m = m + spread_list.get(i);
			i++;
		}
		return m/spread_list.size();
	}
	
	// get median spread
	public static float get_spread_median(List<Float> spread_list) {
		if(spread_list.size()==0) {
			return 0;
		}
		
		Collections.sort(spread_list);
		float m = 0;
		int L = spread_list.size();
		m = spread_list.get(L/2);
		if(L%2==0) {
			m = (spread_list.get(L/2-1) + spread_list.get(L/2))/2;
		}
		return m;
	}
}
