package Question3;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableUtils;
import org.apache.hadoop.shaded.org.apache.commons.lang3.text.StrBuilder;

/**
 * IpAccessDateKey is the composite key. The natural key is the "IpAddress" 
 * and the secondary sort will be performed on AccessDate.
 * @author- sirisha epari
 **/
public class IpAccessDateKey implements WritableComparable<IpAccessDateKey> {
	private String ipAddress;
	private Long accessDate;
	
	public IpAccessDateKey(){}
	
	public IpAccessDateKey(String ipAddress, Long accessDate){
		this.ipAddress = ipAddress;
		this.accessDate = accessDate;
	}
	
	@Override
	public String toString() {
		return (new StrBuilder()).append('{')
				.append(ipAddress).append(',').append(accessDate)
				.append('}').toString();
	}

	@Override
	public void readFields(DataInput arg0) throws IOException {
		ipAddress = WritableUtils.readString(arg0);
		accessDate = arg0.readLong();
		
	}

	@Override
	public void write(DataOutput arg0) throws IOException {
		WritableUtils.writeString(arg0, ipAddress);
		arg0.writeLong(accessDate);		
	}

	@Override
	public int compareTo(IpAccessDateKey o) {
		int ad = ipAddress.compareTo(o.ipAddress);
		if(0 == ad){
			ad = accessDate.compareTo(o.accessDate);
		}
		return ad;
	}
	
	/* Getters and setters */
	public void setIpAddress(String ip){
		this.ipAddress = ip;
	}
	
	public String getIpAddress(){
		return ipAddress;
	}
	
	public void setAccessDate(Long d) {
		this.accessDate = d;
	}
	
	public Long getAccessDate(){
		return accessDate;
	}
}
