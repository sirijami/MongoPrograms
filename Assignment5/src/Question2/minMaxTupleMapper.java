package Question2;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class minMaxTupleMapper extends Mapper<Object, Text, Text, minMaxTuple> {
	
	private static String STOCK_SYMBOL = "stock_symbol";
	private Text stockSymbol = new Text();
	private minMaxTuple outTuple = new minMaxTuple();
	private static final SimpleDateFormat frmt = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	protected void map(Object key, Text value,
			Mapper<Object, Text, Text, minMaxTuple>.Context context)
			throws IOException, InterruptedException {
		
		String[] temp = value.toString().split(",");
		String symbol = new String(temp[1]);
		String date = new String(temp[2]);
		Date dDate = null;
		try {
			dDate = frmt.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if(symbol.equals(STOCK_SYMBOL)) {
			// Don't do anything - No mapping
		} else {
            
			outTuple.setMin(dDate);
			outTuple.setMax(dDate);
			outTuple.setMax_stock_price_adg_close(Long.parseLong(temp[8]));
		}
		
		context.write(stockSymbol,outTuple);
	}
	

}
