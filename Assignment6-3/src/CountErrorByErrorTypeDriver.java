import java.io.IOException;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Counter;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;


public class CountErrorByErrorTypeDriver {

	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException, IllegalStateException, IllegalArgumentException {
		/* Create a Job */
		Job job = Job.getInstance();
		job.setJarByClass(CountErrorByErrorTypeDriver.class);
		
		/* Create map and reduce class */
		job.setMapperClass(CountErrorByErrorTypeMapper.class);
		
		/* Set output key and value class */
		job.setOutputKeyClass(NullWritable.class);
		job.setOutputValueClass(NullWritable.class);
		
		/* Set Input and output format classes */
		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);
	
		
		/* Set input & output format */
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		
		int code = job.waitForCompletion(true) ? 0 : 1;
		if(code == 0){
			for(Counter counter : job.getCounters().getGroup(CountErrorByErrorTypeMapper.ERRORTYPE_COUNTER_GROUP)){
				System.out.println(counter.getDisplayName() + "\t" + counter.getValue());
			}
		}
		System.exit(code);
	}

}
