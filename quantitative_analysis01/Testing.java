package quantitative_analysis01;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Testing {
	
	// generate the report
	public static void main(String[] args) {
        
		Set<String> stock_names = new HashSet<String>();
		String csv_file_path = "/Users/guihan/Downloads/scandi.csv";
		List<ItemData> id_list = BasicTools.read_csv(csv_file_path, stock_names);
		
		//System.out.println(hashSet);
		Iterator<String> iterator = stock_names.iterator();
        while (iterator.hasNext()){
        	// current stock name, filter by stock-name
            String name = iterator.next(); 
            List<ItemData> current_stock = BasicTools.filter_by_stock(id_list, name.strip());
            //System.out.println(name+"\t" + current_stock.size());
            System.out.println(name + ": ");
            
            // further filter by trade & tick-change
            List<ItemData> sub_sotck01 = BasicTools.filter_by_trade(current_stock);
            List<ItemData> sub_sotck02 = BasicTools.filter_by_tickchange(current_stock);
            //System.out.println(sub_sotck01.size() + "\t" + sub_sotck02.size());
                       
            // filter by stock-name & trade, then get time column
            List<Long> time_col01 = BasicTools.get_time_list(sub_sotck01);
            // Mean time between trades
            // Median time between trades
            System.out.println("\tMean time between trades:         \t" + BasicTools.cal_time_mean(time_col01));
            System.out.println("\tMedian time between trades:       \t" + BasicTools.cal_time_median(time_col01));
        
            // filter by stock-name & tick-change, then get time column
            List<Long> time_col02 = BasicTools.get_time_list(sub_sotck02);
            // Mean time between tick changes
            // Median time between tick changes
            System.out.println("\tMean time between tick changes:   \t" + BasicTools.cal_time_mean(time_col02));
            System.out.println("\tMedian time between tick changes: \t" + BasicTools.cal_time_median(time_col02));
            
            // filter by stock-name & trade, then get time column, then calculate time interval
            // Longest time between trades
            System.out.println("\tLongest time between trades:      \t" + BasicTools.get_longest_time(time_col01) + " seconds");
            
            // filter by stock-name & tick-change, then get time column, then calculate time interval
            // Longest time between tick changes
            System.out.println("\tLongest time between tick changes:\t" + BasicTools.get_longest_time(time_col02) + " seconds");
            
            // filter by stock-name, then get bid/ask price, then calculate spread
            List<Float> spread_list = BasicTools.get_spread_list(current_stock);
            // Mean bid ask spread
            System.out.println("\tMean bid ask spread:              \t" + BasicTools.get_spread_mean(spread_list));
            // Median bid ask spread
            System.out.println("\tMedian bid ask spread:            \t" + BasicTools.get_spread_median(spread_list));
        }
        
    }
	
}
