package Question2;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.hadoop.io.Writable;

public class minMaxTuple implements Writable {
	private Date minStock_volumeDate = new Date();
	private Date maxStock_voulumeDate = new Date();
	private long max_stock_price_adg_close = 0;
	
	private final static SimpleDateFormat frmt = new SimpleDateFormat("yyyy-MM-dd");
	
	public Date getMin(){
		return minStock_volumeDate;
	}
	
	public void setMin(Date min){
		this.minStock_volumeDate = min;
	}
	
	public Date getMax(){
		return maxStock_voulumeDate;
	}
	
	public void setMax(Date max){
		this.maxStock_voulumeDate = max;
	}
	
	public long getMax_stock_price_adg_close(){
		return max_stock_price_adg_close;
	}
	
	public void setMax_stock_price_adg_close(long max_stock_price_adg_close){
		this.max_stock_price_adg_close = max_stock_price_adg_close;
	}
	

	@Override
	public void readFields(DataInput in) throws IOException {
		minStock_volumeDate = new Date(in.readLong());
		maxStock_voulumeDate = new Date(in.readLong());
		max_stock_price_adg_close = in.readLong();		
	}

	@Override
	public void write(DataOutput out) throws IOException {
		out.writeLong(minStock_volumeDate.getTime());
		out.writeLong(maxStock_voulumeDate.getTime());
		out.writeLong(max_stock_price_adg_close);		
	}
		
	public String toString(){
		return frmt.format(minStock_volumeDate)+ " " + frmt.format(maxStock_voulumeDate) + max_stock_price_adg_close;
	}

}
