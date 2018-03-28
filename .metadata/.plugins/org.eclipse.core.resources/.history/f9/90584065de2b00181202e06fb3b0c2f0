package Question3;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

/* Grouping the values based on natural key (IpAddress) */
public class NaturalKeyGroupingComparator extends WritableComparator{
	protected NaturalKeyGroupingComparator() {
		super(IpAccessDateKey.class, true);
	}

	@Override
	public int compare(WritableComparable a, WritableComparable b) {
		IpAccessDateKey ip1 = (IpAccessDateKey) a;
		IpAccessDateKey ip2 = (IpAccessDateKey) b;
		
		return ip1.getIpAddress().compareTo(ip2.getIpAddress());
	
	}


	
	

		


}
