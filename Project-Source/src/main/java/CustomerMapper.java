import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CustomerMapper extends Mapper<LongWritable, Text, IntWritable, Text> {

    private static final Logger _log = LogManager.getLogger(CustomerMapper.class);

    private final Pattern separator = Pattern.compile(",");

    private final IntWritable custIdAsKey = new IntWritable();
    private final Text custRecordAsValue = new Text();

    @Override
    protected void map(LongWritable offset, Text line, Context context) throws IOException, InterruptedException {

        try {
            // Split input line to get customer id
            final List<String> columns = Arrays.stream(separator.split(line.toString(), 4)).collect(Collectors.toList());

            try {
                // Extract customer id from customer record and parse it
                int customerId = Integer.parseInt(columns.remove(0));

                // Add customer record indicator (will use in reducer)
                columns.add(0, "C");

                // Write customer id as key and customer record as value
                custIdAsKey.set(customerId);
                custRecordAsValue.set(String.join(",", columns));
                context.write(custIdAsKey, custRecordAsValue);

            } catch (NumberFormatException ignore) {
            }

        } catch (Exception e) {
            _log.error("Failed to process record: " + line.toString(), e);
            throw e;
        }
    }

}