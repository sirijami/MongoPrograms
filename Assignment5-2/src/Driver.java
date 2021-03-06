

import java.io.IOException;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class Driver {

	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException{
		/* Create a Job */
		Job job = Job.getInstance();
		job.setJarByClass(Driver.class);
		
		/* Create map and reduce class */
		job.setMapperClass(minMaxTupleMapper.class);
		
		job.setReducerClass(minMaxTupleReducer.class);
		
		/* Set output key and value class */
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(minMaxTuple.class);
			
		/* Set input & output format */
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		
		System.exit(job.waitForCompletion(true) ? 0 : 1);

	}
}
