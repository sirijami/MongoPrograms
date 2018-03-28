import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;


public class MedianStdDevTuple implements Writable {
    private float standardDeviation = 0;
    private float median = 0;
    
	public void setStdDev(float d) {
		this.standardDeviation = d;
		
	}
	
	public float getStdDev(){
		return standardDeviation;
	}

	public void setMedian(float f) {
		this.median = f;
	}
	
	public float getMedian(){
		return median;
	}
	
	@Override
	public void readFields(DataInput in) throws IOException {
		median = in.readFloat();
		standardDeviation = in.readFloat();
		
	}

	@Override
	public void write(DataOutput out) throws IOException {
		out.writeFloat(median);
        out.writeFloat(standardDeviation);		
	}
	
	public String toString(){
		return median + "," + standardDeviation;
	}

}
