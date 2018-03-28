package Question2;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class minMaxTupleReducer extends Reducer<Text, minMaxTuple, Text, minMaxTuple> {
	private minMaxTuple result = new minMaxTuple();

	@Override
	protected void reduce(Text arg0, Iterable<minMaxTuple> arg1,
			Reducer<Text, minMaxTuple, Text, minMaxTuple>.Context context)
			throws IOException, InterruptedException {
		
		/* Initialize the result */
		result.setMin(null);
		result.setMax(null);
		result.setMax_stock_price_adg_close(0);
		
		/* Setting min date for stock_volume */
		for(minMaxTuple v: arg1){
			if(result.getMin() == null || v.getMin().compareTo(result.getMin()) < 0){
				result.setMin(v.getMin());
			}
		}
		
		/* Setting max date for stock_volume per stock */		
		for(minMaxTuple v : arg1){
			if(result.getMax() == null || v.getMax().compareTo(result.getMax()) > 0){
				result.setMax(v.getMax());
			}
		}
		
		for(minMaxTuple v : arg1){
			if(result.getMax_stock_price_adg_close() == 0 || v.getMax_stock_price_adg_close() > result.getMax_stock_price_adg_close()){
				result.setMax_stock_price_adg_close(v.getMax_stock_price_adg_close());
			}
		}
		
		context.write(arg0, result);
	}


	

}
