import java.io.IOException;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;




	public class Avg_StockHighMapper extends Mapper<LongWritable, Text, Text, FloatWritable> {
		private static String STOCK_SYMBOL = "stock_symbol";
		
		@Override
		protected void map(LongWritable key, Text value, Context context)
				throws IOException, InterruptedException {
			String[] temp = value.toString().split(",");
			String symbol = new String(temp[1]);
			//printArray(temp);
			if(symbol.equals(STOCK_SYMBOL)) {
				// Dont do anything - No mapping
			} else {
				Float stockHigh = new Float(Float.parseFloat(temp[4]));
				context.write(new Text(symbol), new FloatWritable(stockHigh));
			}
			
			
		}

		private void printArray(String[] temp) {
			for(int i = 0; i < temp.length; i++){
				System.out.println(temp[i]);			
			}

			System.out.println();
		}
	}
