import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;


public class empReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

	@Override
	protected void reduce(Text key, Iterable<IntWritable> values,Context context)
			throws IOException, InterruptedException {
		String name = key.toString();
		int sum = 0;
		for(IntWritable val : values){
			sum = sum + val.get();
		}
        context.write(new Text(name), new IntWritable(sum));
	}
	

}
