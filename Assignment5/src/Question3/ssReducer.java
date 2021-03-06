package Question3;

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
		int count = 0;
		
		Iterator<IntWritable> it = arg1.iterator();
		while(it.hasNext()){
			count++;
		}
		 Text countVal = new Text(Integer.toString(count));
         context.write(key, countVal);
	}
	

}
