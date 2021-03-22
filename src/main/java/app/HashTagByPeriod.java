package app;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import aux.Tweet;
import utils.ReaderTSV;

/*
 * Questão 2.a - Quais foram as hashtags mais usadas pela manhã, tarde e noite?
 */

public class HashTagByPeriod {

	public static class TokenizerMapper extends Mapper<Object, Text, Text, IntWritable> {

		public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
			
			ReaderTSV readerTSV = new ReaderTSV(value.toString());
			Tweet tweet = new Tweet(); 
			tweet.setContent(readerTSV.getColumnContent());
			tweet.setCreatedAt(readerTSV.getColumnCreatedAt());
			
			ArrayList<String> hashTags = tweet.getHashTagsFromContentByPeriod();
			
			for (String hashTag : hashTags) {
				context.write(new Text(hashTag), new IntWritable(1));
			}
		}
	}

	public static class IntSumReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
		private IntWritable result = new IntWritable();

		public void reduce(Text key, Iterable<IntWritable> values, Context context)
				throws IOException, InterruptedException {
			int sum = 0;
			for (IntWritable val : values) {
				sum += val.get();
			}
			result.set(sum);
			context.write(key, result);
		}
	}

	public static void main(String[] args) throws Exception {
		
		//Delete Directory OutPut
		FileUtils.deleteDirectory(new File(args[1]));
		
		Configuration conf = new Configuration();
//		conf.set("fs.hdfs.impl", "org.apache.hadoop.hdfs.DistributedFileSystem");
		Job job = Job.getInstance(conf, "Tarefa4Hadoop");
	    job.setJarByClass(HashTagByPeriod.class);
	    job.setMapperClass(TokenizerMapper.class);
	    job.setCombinerClass(IntSumReducer.class);
	    job.setReducerClass(IntSumReducer.class);
	    job.setOutputKeyClass(Text.class);
	    job.setOutputValueClass(IntWritable.class);
	    FileInputFormat.addInputPath(job, new Path(args[0]));
	    FileOutputFormat.setOutputPath(job, new Path(args[1]));
	    boolean success = job.waitForCompletion(true);
	    if (success){
	        FileSystem hdfs = FileSystem.get(job.getConfiguration());
	        FileStatus fs[] = hdfs.listStatus(new Path(args[1]));
	        if (fs != null){ 
	            for (FileStatus aFile : fs) {
	                if (!aFile.isDir()) {
	                    hdfs.rename(aFile.getPath(), new Path(aFile.getPath().toString()+".tsv"));
	                }
	            }
	        }
	    }

	}

}
