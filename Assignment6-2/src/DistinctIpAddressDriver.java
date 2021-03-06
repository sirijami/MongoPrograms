import java.io.IOException;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;


public class DistinctIpAddressDriver {
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		/* Create a Job */
		Job job = Job.getInstance();
		job.setJarByClass(DistinctIpAddressDriver.class);
		
		/* Create map and reduce class */
		job.setMapperClass(DistinctIpAddressMapper.class);
		
        /* Set Combiner */
		job.setCombinerClass(DistinctIpAddressReducer.class);
		job.setReducerClass(DistinctIpAddressReducer.class);
		
		/* Set output key and value class */
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(NullWritable.class);
		
		/* Set Input and output format classes */
		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);
	
		
		/* Set input & output format */
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
	

}
