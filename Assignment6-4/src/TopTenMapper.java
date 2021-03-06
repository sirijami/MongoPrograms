import java.io.IOException;
import java.math.BigDecimal;
import java.util.TreeMap;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;


public class TopTenMapper extends Mapper<Object, Text, NullWritable, Text> {
	private static final String TotalDue = "TotalDue";
	
	//stores a map of Total amount due to the record
	private TreeMap<BigDecimal, Text> outTotalDue = new TreeMap<>();

	@Override
	protected void cleanup(
			Mapper<Object, Text, NullWritable, Text>.Context context)
			throws IOException, InterruptedException {
		for(Text t : outTotalDue.values()){
			context.write(NullWritable.get(), t);
		}
	}

	@Override
	protected void map(Object key, Text value,
			Mapper<Object, Text, NullWritable, Text>.Context context)
			throws IOException, InterruptedException {
		
		String[] tokens = value.toString().split(",");
//		System.out.println("token 22 is "+ tokens[22]);
		if(!tokens[22].equals(TotalDue)){			
			outTotalDue.put(new BigDecimal(tokens[22]), new Text(value));
		}
		if(outTotalDue.size() > 10){
			outTotalDue.remove(outTotalDue.firstKey());
		}		
	}
}
