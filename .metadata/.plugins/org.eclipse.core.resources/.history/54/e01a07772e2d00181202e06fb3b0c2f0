package Question3;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

public class CompositeKeyComparator extends WritableComparator {
	protected CompositeKeyComparator(){
		super(IpAccessDateKey.class, true);
	}

	@Override
	public int compare(WritableComparable a, WritableComparable b) {
		IpAccessDateKey ip1 = (IpAccessDateKey) a;
		IpAccessDateKey ip2 = (IpAccessDateKey) b;
		
		int res = ip1.getIpAddress().compareTo(ip2.getIpAddress());
		if(res == 0){
			res = -1 * ip1.getAccessDate().compareTo(ip2.getAccessDate());
		}
		return res;
	}
	
	
	

}
