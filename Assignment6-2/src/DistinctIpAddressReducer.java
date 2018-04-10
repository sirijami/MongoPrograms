import java.io.IOException;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

/* Group based on ip address and return the distinct values */
public class DistinctIpAddressReducer extends Reducer<Text, NullWritable, Text, NullWritable> {

	@Override
	protected void reduce(Text arg0, Iterable<NullWritable> arg1,
			Reducer<Text, NullWritable, Text, NullWritable>.Context context)
			throws IOException, InterruptedException {
		
		context.write(arg0, NullWritable.get());
	}
	

}
