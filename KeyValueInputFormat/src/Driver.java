import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Mapper.Context;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


public class Driver {

	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		Configuration conf = new Configuration();	
		conf.set("key.value.separator.in.input.line", ",");
		Job job = Job.getInstance(conf, "emp details");
		job.setJarByClass(Driver.class);
		
		job.setMapperClass(empMapper.class);
		job.setReducerClass(empReducer.class);
		job.setInputFormatClass(KeyValueTextInputFormat.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
				
		System.exit(job.waitForCompletion(true) ? 0 : 1);

		

	}
	
	public static class empMapper extends Mapper<Text, Text, Text, Text> {

		@Override
		protected void map(Text key, Text value, Context context)
				throws IOException, InterruptedException {
			System.out.println("emp Name" + key);

			context.write(new Text(key), new Text(value));
		}
	}
	
	public static class empReducer extends Reducer<Text,IntWritable,Text,IntWritable> {

		@Override
		protected void reduce(Text key, Iterable<IntWritable> values,Context context)
				throws IOException, InterruptedException {
			int sum = 0;
			for(IntWritable val : values){
				sum = sum + val.get();
			}
	        context.write(key, new IntWritable(sum));
		}
		

	}
	

}
