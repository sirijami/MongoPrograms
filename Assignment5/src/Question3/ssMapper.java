package Question3;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class ssMapper extends Mapper<LongWritable, Text, IpAccessDateKey, IntWritable>{

	@Override
	protected void map(
			LongWritable key,
			Text value,
			Mapper<LongWritable, Text, IpAccessDateKey, IntWritable>.Context context)
			throws IOException, InterruptedException {
		
		String[] temp = value.toString().split(",");
		String ipAddress = temp[0];
		Long accessDate = Long.parseLong(temp[1]);
		
		IpAccessDateKey ipAccessDateKey = new IpAccessDateKey(ipAddress, accessDate);
		
		context.write(ipAccessDateKey, new IntWritable(1));

	}
	

}
