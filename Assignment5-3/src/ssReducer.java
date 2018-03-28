

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class ssReducer extends Reducer<IpAccessDateKey, IntWritable, Text, Text> {

	@Override
	protected void reduce(IpAccessDateKey arg0, Iterable<IntWritable> arg1,
			Reducer<IpAccessDateKey, IntWritable, Text, Text>.Context context)
			throws IOException, InterruptedException {
		Text key = new Text(arg0.toString());
		Text countIp = new Text();
		
		System.out.println("IpAccessDateKey " + key);
		int count = 0;
		for(IntWritable v : arg1){
			count = count + v.get();
		}
		countIp.set(Integer.toString(count));
         context.write(key, countIp);
	}
	

}
