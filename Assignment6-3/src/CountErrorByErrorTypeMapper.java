import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;


public class CountErrorByErrorTypeMapper extends Mapper<Object, Text, NullWritable, NullWritable> {
	public static final String ERRORTYPE_COUNTER_GROUP = "errorType";
	public static final String UNKNOWN_COUNTER = "Unknown";
	
	private String[] errorTypeArray = new String[]{"File does not exist:", "script", 
			"client sent HTTP/1.1 request without hostname (see RFC2616 section 14.23):",
			"(20024)The given path is misformatted or contained invalid characters:"
	};
	private HashSet<String> ErrorType = new HashSet<>(Arrays.asList(errorTypeArray));
	
	@Override
	protected void map(Object key, Text value,
			Mapper<Object, Text, NullWritable, NullWritable>.Context context)
			throws IOException, InterruptedException {
				
		String temp = value.toString();
		boolean unknown = true;
		if(temp.contains("[error]")){
			for(String et : ErrorType){
				if(temp.contains(et)){
					context.getCounter(ERRORTYPE_COUNTER_GROUP, et).increment(1);
					unknown = false;
				}
				if(unknown){
					context.getCounter(ERRORTYPE_COUNTER_GROUP, UNKNOWN_COUNTER).increment(1);
				}
			}
		}
	}
}
