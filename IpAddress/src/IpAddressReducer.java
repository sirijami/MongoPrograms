import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;


public class IpAddressReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
	private static Logger logger = LoggerFactory.getLogger(IpAddressReducer.class);
	
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
