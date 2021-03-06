import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.SortedMapWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class MedianStdDevUsingCombinerDriver {	
	/* Key is the movie id and value is rating, one */
	public static class MedianStdDevMapper extends 
	Mapper<Object, Text, IntWritable, SortedMapWritable>{
		private static String MOVIE_ID = "MovieID";
		private IntWritable rating = new IntWritable();
		private IntWritable movie = new IntWritable();
		private static final LongWritable ONE = new LongWritable(1);
		
			
		@Override
		protected void map(Object key, Text value,
				Mapper<Object, Text, IntWritable, SortedMapWritable>.Context context)
				throws IOException, InterruptedException {
			String[] tokens = value.toString().split(",");
			String movieId = tokens[1];
            if(!movieId.equals(MOVIE_ID)){
    			String ratings = tokens[2];
    			movie.set(Integer.parseInt(movieId));
    			rating.set(Integer.parseInt(ratings));
    			SortedMapWritable mapValue = new SortedMapWritable();
    			mapValue.put(rating, ONE);
    			context.write(movie, mapValue);	
            }		
		}			
	}
	
	public static class MedianStdDevReducer extends Reducer<IntWritable, SortedMapWritable, IntWritable, MedianStdDevTuple>{
		private MedianStdDevTuple result  = new MedianStdDevTuple();
		private TreeMap<Integer,Long> ratingCounts = new TreeMap<Integer,Long>();
		@Override
		protected void reduce(
				IntWritable arg0,
				Iterable<SortedMapWritable> arg1,
				Reducer<IntWritable, SortedMapWritable, IntWritable, MedianStdDevTuple>.Context context)
				throws IOException, InterruptedException {
			float sum = 0;
			long tcount = 0;
			ratingCounts.clear();
			result.setMedian(0);
			result.setStdDev(0);
			
			for(SortedMapWritable v : arg1){
				for(Iterator i = v.entrySet().iterator(); i.hasNext();){
					Entry<WritableComparable, Writable> entry = (Entry<WritableComparable, Writable>) i.next();
					int rating = ((IntWritable) entry.getKey()).get();
					long count = ((LongWritable) entry.getValue()).get();
					tcount = tcount + count;
					sum = sum + (rating * count);
					
					Long storedCount = ratingCounts.get(rating);
					if(storedCount == null){
						ratingCounts.put(rating, count);
					}else {
						ratingCounts.put(rating, storedCount + count);
					}				
				}
			}
									
			/* Calculate the median */
			long medianIndex = tcount/2L;
			long prevRating = 0;
			long ratings = 0;
			long prevKey = 0;
			
			for(Entry<Integer,Long> entry : ratingCounts.entrySet()){
				ratings = prevRating + entry.getValue();
				if(prevRating <= medianIndex && medianIndex < ratings){
					if(tcount % 2 == 0 && prevRating == medianIndex){
						result.setMedian((float) (entry.getKey() + prevKey)/2.0f);
					}else{
						result.setMedian(entry.getKey());
					}
					break;
				}
				prevRating = ratings;
				prevKey = entry.getKey();
			}
			
			/* Calculate standard Deviation */
			float mean = sum/tcount;
			float sumOfSquares = 0.0f;
			for(Entry<Integer, Long> e : ratingCounts.entrySet()){
				sumOfSquares = sumOfSquares + (e.getKey() - mean) * (e.getKey() - mean) * e.getValue();			
			}
			result.setStdDev((float) Math.sqrt(sumOfSquares/(tcount-1)));
			context.write(arg0, result);		

		}	
	}
	
	public static class MedianStdDevCombiner extends Reducer<IntWritable, SortedMapWritable, IntWritable, SortedMapWritable>{

		@Override
		protected void reduce(
				IntWritable arg0,
				Iterable<SortedMapWritable> arg1,
				Reducer<IntWritable, SortedMapWritable, IntWritable, SortedMapWritable>.Context context)
				throws IOException, InterruptedException {
			
			SortedMapWritable outValue = new SortedMapWritable();
			
			for(SortedMapWritable v : arg1){
				for(Iterator i = v.entrySet().iterator(); i.hasNext();){
					Entry<WritableComparable, Writable> entry = (Entry<WritableComparable, Writable>) i.next();
					LongWritable count = (LongWritable) outValue.get(entry.getKey());
					if(count != null){
						count.set(count.get()+ ((LongWritable) entry.getValue()).get());
					}else{
						outValue.put(entry.getKey(), new LongWritable( ((LongWritable) entry.getValue()).get()));
					}		
				}
				v.clear();
			}
			context.write(arg0, outValue);
		}		
	}
	
	

	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
	    Configuration conf = new Configuration();
	    Job job = Job.getInstance(conf, "MedianStdDeviationUsingCustomCombiner");
	    job.setJarByClass(MedianStdDevUsingCombinerDriver.class);
	    
	    job.setInputFormatClass(TextInputFormat.class);
	    
	    job.setMapperClass(MedianStdDevMapper.class);
	    job.setCombinerClass(MedianStdDevCombiner.class);
	    job.setReducerClass(MedianStdDevReducer.class);
	    
	    job.setMapOutputKeyClass(IntWritable.class);
	    job.setMapOutputValueClass(SortedMapWritable.class);
	            
	    job.setOutputKeyClass(IntWritable.class);
	    job.setOutputValueClass(MedianStdDevTuple.class);

	    	    
	    FileInputFormat.addInputPath(job, new Path(args[0]));
	    FileOutputFormat.setOutputPath(job, new Path(args[1]));
	    System.exit(job.waitForCompletion(true) ? 0 : 1);


	}

}
