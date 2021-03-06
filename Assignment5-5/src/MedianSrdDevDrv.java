import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class MedianSrdDevDrv {
    /* Finding median and standard deviation without combiner optimization 
     * The mapper will process each input record and calculate the median of ratings per movie
     * The output key is the movie and the value is ratings
     * A combiner cannot be used in this implementation */
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
	    Configuration conf = new Configuration();
	    Job job = Job.getInstance(conf, "MedianStdDeviation");
	    job.setJarByClass(MedianSrdDevDrv.class);
	    
	    job.setInputFormatClass(TextInputFormat.class);
	    
	    job.setMapperClass(MedianStdDevMapper.class);
	    job.setReducerClass(MedianStdDevReducer.class);
	    
	    job.setOutputKeyClass(IntWritable.class);
	    job.setOutputValueClass(MedianStdDevTuple.class);
	    
	    job.setMapOutputKeyClass(IntWritable.class);
	    job.setMapOutputValueClass(IntWritable.class);
	    
	    
	    FileInputFormat.addInputPath(job, new Path(args[0]));
	    FileOutputFormat.setOutputPath(job, new Path(args[1]));
	    System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
	
	public static class MedianStdDevMapper extends Mapper<Object, Text, IntWritable, IntWritable>{
		private static String MOVIE_ID = "MovieID";
		private IntWritable rating = new IntWritable();
		private IntWritable movie = new IntWritable();

		@Override
		protected void map(Object key, Text value,
				Mapper<Object, Text, IntWritable, IntWritable>.Context context)
				throws IOException, InterruptedException {
			String[] tokens = value.toString().split(",");
			String movieId = tokens[1];
            if(!movieId.equals(MOVIE_ID)){
    			String ratings = tokens[2];
    			movie.set(Integer.parseInt(movieId));
    			rating.set(Integer.parseInt(ratings));
    			context.write(movie, rating);	
            }		
		}		
	}
	
	public static class MedianStdDevReducer extends Reducer<IntWritable, IntWritable, IntWritable, MedianStdDevTuple>{
		
		private ArrayList<Float> ratings = new ArrayList<Float>();
		private MedianStdDevTuple result = new MedianStdDevTuple();
		
		@Override
		protected void reduce(
				IntWritable arg0,
				Iterable<IntWritable> arg1,
				Reducer<IntWritable, IntWritable, IntWritable, MedianStdDevTuple>.Context context)
				throws IOException, InterruptedException {
			float sum = 0;
			float count = 0;
			ratings.clear();
			result.setStdDev(0);
			
			/* Iterate through all the rating values */
			for(IntWritable val : arg1){
				ratings.add((float) val.get());
				sum =sum + val.get();
				++count;
			}
			
			/* sort all the ratings to find the median */
			Collections.sort(ratings);
			
			/*if the length of arraylist of ratings is even then find the average of 
			 * middle element else return the mid element
			 */
			if(count % 2 == 0){
				result.setMedian((ratings.get((int) count/2 -1) + ratings.get((int) count/2)) / 2.0f );
			}else{
				result.setMedian(ratings.get((int) count / 2));
			}
			
			/* Calculate the standard deviation */
			float mean = sum/count;
			float sumOfSquares = 0.0f;
			for(float f: ratings){
				sumOfSquares = sumOfSquares + (f-mean) * (f-mean);
			}
			
			result.setStdDev((float) Math.sqrt(sumOfSquares/ (count-1)));
			context.write(arg0, result);
		}
		
	}

}
