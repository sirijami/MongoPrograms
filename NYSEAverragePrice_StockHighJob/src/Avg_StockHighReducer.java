import java.io.IOException;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;



	public class Avg_StockHighReducer extends Reducer<Text, FloatWritable, Text, FloatWritable> {		
		@Override
		protected void reduce(Text key, Iterable<FloatWritable> values, Context context) throws IOException, InterruptedException {
			String symbol = key.toString();
			Float sum = 0f;
			Float count = 0f;
			Float average = 0f;
			for(FloatWritable val : values){
				sum = sum + val.get();
				count = count + 1;
			}
			average = sum/count;
			context.write(new Text(symbol), new FloatWritable(average));
		} 

	}