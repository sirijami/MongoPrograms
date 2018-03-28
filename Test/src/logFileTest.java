import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class logFileTest {
//	public static String IP_FORMAT = "((?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){"
//			+ "3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?))(?![\\d])";
	public static String IP_FORMAT =  "\\d+\\.\\d+\\.\\d+\\.\\d+";
	//"\\d+\\.\\d+\\.\\d+\\.\\d+"
	 public static String DATE_FORMAT = "\\d+\\/\\w+\\/\\d+\\:\\d+\\:\\d+\\:\\d+.[-+ ]\\d+" ;
	 private final static SimpleDateFormat dateFrmt = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss Z");
	 

	public static void main(String[] args) throws ParseException {
		
		//String line = "129.10.65.240 - - [15/Oct/2011:12:05:58 -0400] \"GET /favicon.ico HTTP/1.1\" 404 209";
		String line = "129.10.65.240 - - [15/Oct/2011:12:05:58 -0400] \"GET /favicon.ico HTTP/1.1\" 404 209";
//		Pattern ipPattern = Pattern.compile(IP_FORMAT);		
//		Pattern datePattern = Pattern.compile(DATE_FORMAT);
//		
//		Matcher ipMatcher = ipPattern.matcher(line);
//		boolean matchFound = ipMatcher.find();
//		System.out.println("Did we find a match? " + matchFound);
//		String ipaddress1 = ipMatcher.group();
//		System.out.println("ipAdress " + ipaddress1);
//		
//		Matcher dateMatcher = datePattern.matcher(line);
//		boolean dateMatchFound = dateMatcher.find();
//		System.out.println("Did we find a dateMatch? " + dateMatchFound);
//		String dateText = dateMatcher.group();
//		System.out.println("dateText " + dateText);
//		SimpleDateFormat dateFrmt = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss Z");
//		Date date = dateFrmt.parse(dateText);
//		System.out.println("Date " + date.toString());
//		System.out.println("Date in millis - " + date.getTime());

		Pattern ipPattern = Pattern.compile(IP_FORMAT);		
		Pattern datePattern = Pattern.compile(DATE_FORMAT);
		
		Matcher ipMatcher = ipPattern.matcher(line);
		Matcher dateMatcher = datePattern.matcher(line);
		String ipaddress;
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
        System.out.println("date " + date.toString());
		System.out.println("Date in millis - " + date.getTime());
		
		
	}

}
