import java.io.IOException;
import java.math.BigDecimal;
import java.util.TreeMap;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;


public class TopTenReducer extends Reducer<NullWritable, Text, NullWritable, Text> {
	private TreeMap<BigDecimal, Text> outTotalDue = new TreeMap<>();

	@Override
	protected void reduce(NullWritable arg0, Iterable<Text> arg1,
			Reducer<NullWritable, Text, NullWritable, Text>.Context arg2)
			throws IOException, InterruptedException {
		
		for(Text t : arg1){
			String[] tokens = t.toString().split(",");
			outTotalDue.put(new BigDecimal(tokens[22]), new Text(t));
			
			if(outTotalDue.size() > 10){
				outTotalDue.remove(outTotalDue.firstKey());
			}
		}
		
		for(Text t: outTotalDue.descendingMap().values()){
			arg2.write(NullWritable.get(), t);
		}
	}
}
