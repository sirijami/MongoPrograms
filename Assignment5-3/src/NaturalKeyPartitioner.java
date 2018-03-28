

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Partitioner;

/* Partition based on natural Key so that all values similar to same key goes to same reducer*/
public class NaturalKeyPartitioner extends Partitioner<IpAccessDateKey, IntWritable> {

	@Override
	public int getPartition(IpAccessDateKey key, IntWritable value, int numPartition) {
		int hash = key.getIpAddress().hashCode();
		int partition = hash % numPartition;
		return partition;
	}
	

}
