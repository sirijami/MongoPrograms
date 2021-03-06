import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.CombineFileSplit;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.input.LineRecordReader;


public class myCombineFileRecordReader extends RecordReader<LongWritable, Text> {
	private LineRecordReader lineRecorder = new LineRecordReader();
	
	public myCombineFileRecordReader(CombineFileSplit split, TaskAttemptContext context, Integer index ) throws IOException {
		FileSplit fileSplit = new FileSplit(split.getPath(index), split.getOffset(index), 
				split.getLength(index),split.getLocations());
		
		lineRecorder.initialize(fileSplit,context);
	}

	@Override
	public void close() throws IOException {
		lineRecorder.close();		
	}

	@Override
	public LongWritable getCurrentKey() throws IOException,
			InterruptedException {
		return lineRecorder.getCurrentKey();
	}

	@Override
	public Text getCurrentValue() throws IOException, InterruptedException {
		return lineRecorder.getCurrentValue();
	}

	@Override
	public float getProgress() throws IOException, InterruptedException {
		return lineRecorder.getProgress();
	}

	@Override
	public void initialize(InputSplit split, TaskAttemptContext context)
			throws IOException, InterruptedException {		
	}

	@Override
	public boolean nextKeyValue() throws IOException, InterruptedException {
		return lineRecorder.nextKeyValue();
	}
}


