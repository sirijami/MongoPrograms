import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


public class avg_stock_price_adj_closeDriver {

	
	public static class avgMapper extends Mapper<LongWritable, Text, Text, countAverageTuple> {
		private static String STOCK_DATE = "date";
		private static String AVG_STOCK = "stock_price_adj_close";
		private Text outYear = new Text();
		private countAverageTuple outCountAverage = new countAverageTuple();
		
		@Override
		protected void map(
				LongWritable key,
				Text value,
				Mapper<LongWritable, Text, Text, countAverageTuple>.Context context)
				throws IOException, InterruptedException {
			
			String[] tokens = value.toString().split(",");

			String StringDate = tokens[2];
//			float avg_stock_price_adj_close = 0f;
			
			if(!StringDate.equals(STOCK_DATE) || !AVG_STOCK.equals("stock_price_adj_close") ){
				try {
					float avg_stock_price_adj_close = Float.parseFloat(tokens[8]);
					Date dateFormat = new SimpleDateFormat("yyyy-MM-dd").parse(StringDate);
					SimpleDateFormat df = new SimpleDateFormat("yyyy");
					String year = df.format(dateFormat);				
					outYear.set(year);
					outCountAverage.setCount(1);
					outCountAverage.setAverage(avg_stock_price_adj_close);
					context.write(outYear, outCountAverage);					
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}		
	}
	
	public static class avgReducer extends Reducer<Text, countAverageTuple, Text, countAverageTuple>{
		private countAverageTuple result = new countAverageTuple();

		@Override
		protected void reduce(
				Text arg0,
				Iterable<countAverageTuple> arg1,
				Reducer<Text, countAverageTuple, Text, countAverageTuple>.Context context)
				throws IOException, InterruptedException {
			
			float sum = 0;
			float count = 0;
			
			for(countAverageTuple val : arg1){
				sum += val.getCount() * val.getAverage();
				count = count + val.getCount();
			}
			
			result.setCount(count);
			result.setAverage(sum/count);
			context.write(arg0, result);
		}		
	}
	
	public static void main (String[] args) throws IOException, ClassNotFoundException, InterruptedException{
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf, "AvgOfStockPriceAdjacent");
		job.setJarByClass(avg_stock_price_adj_closeDriver.class);
	    job.setMapperClass(avgMapper.class);
	    job.setCombinerClass(avgReducer.class);
	    job.setReducerClass(avgReducer.class);
	    
	    job.setOutputKeyClass(Text.class);
	    job.setOutputValueClass(countAverageTuple.class);
	    
	    FileInputFormat.addInputPath(job, new Path(args[0]));
	    FileOutputFormat.setOutputPath(job, new Path(args[1]));
	    
	    System.exit(job.waitForCompletion(true) ? 0 : 1);
		
	}

}


