import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class CustomerTransactionJoinDriver {

    private static final Logger _log = LogManager.getLogger(CustomerTransactionJoinDriver.class);

    public static void main(String[] args) {

        if (args.length < 3) {
            _log.error(String.format("Usage: %s <customer-file-path> <transaction-file-path> <output-path>", CustomerTransactionJoinDriver.class.getSimpleName()));
            System.exit(-1);
        }

        try {

            //Prepare job instance
            Configuration conf = new Configuration();
            conf.set("mapreduce.output.textoutputformat.separator", ",");


            JobConf jobConf = new JobConf(conf);
            Job job = Job.getInstance(jobConf, "Customer Left Join Transaction Job");
            job.setJarByClass(CustomerTransactionJoinDriver.class);

            // Set input and output paths
            MultipleInputs.addInputPath(job, new Path(args[0]), TextInputFormat.class, CustomerMapper.class);   // customer file will processed by CustomerMapper
            MultipleInputs.addInputPath(job, new Path(args[1]), TextInputFormat.class, TransactionMapper.class);    // transaction file will processed by TransactionMapper

            FileOutputFormat.setOutputPath(job, new Path(args[2]));

            // Set output type and format classes
            job.setMapOutputKeyClass(IntWritable.class);
            job.setMapOutputValueClass(Text.class);
            job.setOutputKeyClass(IntWritable.class);
            job.setOutputValueClass(Text.class);
            job.setOutputFormatClass(TextOutputFormat.class);

            // Set mapper/reduce implementation classes
//            job.setMapperClass(CustomerMapper.class);
//            job.setMapperClass(TransactionMapper.class);
            job.setReducerClass(JoinReducer.class);

            // Start job and wait for complete
            _log.info(job.waitForCompletion(_log.isDebugEnabled())?  "Success :)": "Fail :(");

            System.exit(job.isSuccessful() ? 0 : 1);
        } catch (Exception e) {
            _log.error("Error", e);
            System.exit(1);
        }
    }
}