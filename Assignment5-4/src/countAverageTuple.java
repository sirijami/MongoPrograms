import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;


public class countAverageTuple implements Writable {
	float count = 0;
	float average = 0;
	
	
	public float getCount() {
		return count;
	}

	public float getAverage() {
		return average;
	}
	
	public void setCount(float count) {
		this.count = count;
	}

	public void setAverage(float average) {
		this.average = average;
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		count = in.readInt();
		average = in.readFloat();		
	}

	@Override
	public void write(DataOutput out) throws IOException {
		out.writeFloat(count);
        out.writeFloat(average);		
	}
	
	public String toString(){
		return count + "," + average;
	}

}
