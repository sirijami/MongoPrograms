import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
public class WordReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

	@Override
	protected void reduce(Text key, Iterable<IntWritable> values,
			Reducer<Text, IntWritable, Text, IntWritable>.Context context)
			throws IOException, InterruptedException {
		IntWritable result = new IntWritable();
		int sum = 0;
		for(IntWritable val : values){
			sum = sum + val.get();
		}
        result.set(sum);
		context.write(key, result);

	}

      

}
