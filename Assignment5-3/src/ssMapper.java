

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class ssMapper extends Mapper<LongWritable, Text, IpAccessDateKey, IntWritable>{
	public static String IP_FORMAT =  "\\d+\\.\\d+\\.\\d+\\.\\d+";	
	public static String DATE_FORMAT = "\\d+\\/\\w+\\/\\d+\\:\\d+\\:\\d+\\:\\d+.[-+ ]\\d+" ;
	private final static SimpleDateFormat dateFrmt = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss Z");
	
	@Override
	protected void map(
			LongWritable key,
			Text value,
			Mapper<LongWritable, Text, IpAccessDateKey, IntWritable>.Context context)
			throws IOException, InterruptedException {
		
		String line = value.toString();
		Pattern ipPattern = Pattern.compile(IP_FORMAT);		
		Pattern datePattern = Pattern.compile(DATE_FORMAT);
		
		Matcher ipMatcher = ipPattern.matcher(line);
		Matcher dateMatcher = datePattern.matcher(line);
		String ipaddress = null;
		String dateText = null;
		Date date = null;
		
		if(ipMatcher.find()){
			ipaddress = ipMatcher.group();
		}
		if(dateMatcher.find()){
			dateText = dateMatcher.group();
			try {
				date = dateFrmt.parse(dateText);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

//		System.out.println("Date in millis - " + date.getTime());
		long dateMilliseconds = date.getTime();
		IpAccessDateKey ipAccessdate = new IpAccessDateKey(ipaddress, dateMilliseconds);
		context.write(ipAccessdate, new IntWritable(1));

	}
	

}
