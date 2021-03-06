import java.io.IOException;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;


public class DistinctIpAddressMapper extends Mapper<Object, Text, Text, NullWritable> {
	
	private Text outIpAddress = new Text();

	@Override
	protected void map(Object key, Text value,
			Mapper<Object, Text, Text, NullWritable>.Context context)
			throws IOException, InterruptedException {
		String[] token = value.toString().split(" ");
		outIpAddress.set(token[0]);
		context.write(outIpAddress, NullWritable.get());

	}
	

	
	

}
