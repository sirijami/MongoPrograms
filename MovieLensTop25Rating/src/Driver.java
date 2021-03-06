import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


public class Driver {

	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		Job job = Job.getInstance();
		job.setMapperClass(ratingsMapper.class);
		job.setReducerClass(ratingsReducer.class);
		
		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputKeyClass(IntWritable.class);
		job.setOutputValueClass(FloatWritable.class);
		
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		
		System.exit(job.waitForCompletion(true) ? 0 : 1);

	}
	
	public static class ratingsMapper extends Mapper<Object, Text, IntWritable, FloatWritable>{

		@Override
		protected void map(Object key, Text value,Context context)
				throws IOException, InterruptedException {
			String[] temp = value.toString().split("::");
			context.write(new IntWritable(Integer.parseInt(temp[1])), new FloatWritable(Float.parseFloat(temp[2])));
		}
		
	}
	
	public static class ratingsReducer extends Reducer<IntWritable, FloatWritable, IntWritable, FloatWritable>{

		@Override
		protected void reduce(IntWritable key,Iterable<FloatWritable> values,Context context)
				throws IOException, InterruptedException {
			int movie_id = key.get();
			Float sum = 0f; Float count = 0f;
			for(FloatWritable val : values){
				sum = sum + val.get();
				count = count + 1;
			}
			Float average = sum/count;
			context.write(new IntWritable(movie_id), new FloatWritable(average) );
		}
		
		
	}
	
	

}
