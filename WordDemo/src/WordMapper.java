import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class WordMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

	@Override
	protected void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		
//		StringTokenizer st = new StringTokenizer(value.toString());
//		final IntWritable one = new IntWritable(1);
//		Text word = new Text();
//		
//		while(st.hasMoreTokens()){
//			word.set(st.nextToken());
//			context.write(word, one); 
//		}
		
		String[] temp = value.toString().split(" ");
		context.write(new Text(temp[0]), new IntWritable(1));
	}
	
	
	


}
