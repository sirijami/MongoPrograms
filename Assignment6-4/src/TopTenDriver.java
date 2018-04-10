import java.io.IOException;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;


public class TopTenDriver {

	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		/* Create a Job */
		Job job = Job.getInstance();
		job.setJarByClass(TopTenDriver.class);
		
		/* Create map and reduce class */
		job.setMapperClass(TopTenMapper.class);
		
        /* Set Reducer */
		job.setCombinerClass(TopTenReducer.class);
		
		/* Set output key and value class */
		job.setOutputKeyClass(NullWritable.class);
		job.setOutputValueClass(Text.class);
		
		/* Set Input and output format classes */
		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);
	
		
		/* Set input & output format */
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		
		System.exit(job.waitForCompletion(true) ? 0 : 1);

	}

}
