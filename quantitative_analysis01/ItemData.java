package quantitative_analysis01;

public class ItemData {
	
	private String stock_name; // Bloomberg Code/Stock identifier
	private float bid_price; // Bid Price
	private float ask_price; // Ask Price
	private float trade_price; // Trade Price
	private int bid_volume; // Bid Volume
	private int ask_volume; // Ask Volume
	private int trade_volume; // Trade Volume
	private int update_type; // Update type: 1=Trade, 2=Change to Bid (price or volume), 3=Change to Ask (price or volume)
	private String date_str; // Date
	private float time_second; // Time in seconds past midnight
	
	// constructor, parse string
	public ItemData(String str) {
		String[] items = str.split(",");
		this.stock_name = items[0];
		this.bid_price = Float.parseFloat(items[2]);
		this.ask_price = Float.parseFloat(items[3]);
		this.trade_price = Float.parseFloat(items[4]);
		this.bid_volume = Integer.parseInt(items[5]);
		this.ask_volume = Integer.parseInt(items[6]);
		this.trade_volume = Integer.parseInt(items[7]);
		this.update_type = Integer.parseInt(items[8]);
		this.date_str = items[10];
		this.time_second = Float.parseFloat(items[11]);
		//
	}
	
	// constructor
	public ItemData(String stock_name, float bid_price, float ask_price, float trade_price, int bid_volume,
			int ask_volume, int trade_volume, int update_type, String date_str, float time_second) {
		this.stock_name = stock_name;
		this.bid_price = bid_price;
		this.ask_price = ask_price;
		this.trade_price = trade_price;
		this.bid_volume = bid_volume;
		this.ask_volume = ask_volume;
		this.trade_volume = trade_volume;
		this.update_type = update_type;
		this.date_str = date_str;
		this.time_second = time_second;
	}
	
	// simple check for testing
	public boolean check() {
		try {
			if(update_type<1 || update_type>3) {
				return false;
			}
			if(date_str.length()!=8) {
				return false;
			}
		}catch(Exception e) {
			
		}
		return true;
	}
	
	// -------------- getter & setter
	
	public String getStock_name() {
		return stock_name;
	}
	public void setStock_name(String stock_name) {
		this.stock_name = stock_name;
	}
	public float getBid_price() {
		return bid_price;
	}
	public void setBid_price(float bid_price) {
		this.bid_price = bid_price;
	}
	public float getAsk_price() {
		return ask_price;
	}
	public void setAsk_price(float ask_price) {
		this.ask_price = ask_price;
	}
	public float getTrade_price() {
		return trade_price;
	}
	public void setTrade_price(float trade_price) {
		this.trade_price = trade_price;
	}
	public float getBid_volume() {
		return bid_volume;
	}
	public void setBid_volume(int bid_volume) {
		this.bid_volume = bid_volume;
	}
	public float getAsk_volume() {
		return ask_volume;
	}
	public void setAsk_volume(int ask_volume) {
		this.ask_volume = ask_volume;
	}
	public float getTrade_volume() {
		return trade_volume;
	}
	public void setTrade_volume(int trade_volume) {
		this.trade_volume = trade_volume;
	}
	public int getUpdate_type() {
		return update_type;
	}
	public void setUpdate_type(int update_type) {
		this.update_type = update_type;
	}
	public String getDate_str() {
		return date_str;
	}
	public void setDate_str(String date_str) {
		this.date_str = date_str;
	}
	public float getTime_second() {
		return time_second;
	}
	public void setTime_second(float time_second) {
		this.time_second = time_second;
	}

	@Override
	public String toString() {
		return "ItemData [stock_name=" + stock_name + ", bid_price=" + bid_price + ", ask_price=" + ask_price
				+ ", trade_price=" + trade_price + ", bid_volume=" + bid_volume + ", ask_volume=" + ask_volume
				+ ", trade_volume=" + trade_volume + ", update_type=" + update_type + ", date_str=" + date_str
				+ ", time_second=" + time_second + "]";
	}
	
}
