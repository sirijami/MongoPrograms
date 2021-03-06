import java.io.IOException;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IpAddressMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
	
	Logger logger = LoggerFactory.getLogger(IpAddressMapper.class);
	Text ip = new Text();
	IntWritable count = new IntWritable(1);
	Pattern logPattern = Pattern
	         .compile("([^ ]*) ([^ ]*) ([^ ]*) \\[([^]]*)\\]"
	                 + " \"([^\"]*)\""
	                 + " ([^ ]*) ([^ ]*).*");

	@Override
	protected void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		
		logger.info(" Mapper Started");
		String line = ((Text) value).toString();
		Matcher matcher = logPattern.matcher(line);
		if(matcher.matches()){
			String ipAddress = matcher.group(1);
			ip.set(ipAddress);
			context.write(ip, count);
		}
		
		logger.info("Mapper ended");
		

	
		

	}

}

//StringTokenizer st = new StringTokenizer(value.toString());
//final IntWritable one = new IntWritable(1);
//Text word = new Text();
//
//while(st.hasMoreTokens()){
//	word.set(st.toString());
//	context.write(word, one); 
//}
