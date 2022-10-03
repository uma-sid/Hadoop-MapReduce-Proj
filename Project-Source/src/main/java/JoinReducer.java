import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class JoinReducer extends Reducer<IntWritable, Text, IntWritable, Text> {

    private static final Logger _log = LogManager.getLogger(JoinReducer.class);

    private final Pattern separator = Pattern.compile(",");
    private final Text joinedRecordAsValue = new Text();

    @Override
    protected void reduce(IntWritable custId, Iterable<Text> records, Context context) throws IOException, InterruptedException {

        String customerRecord = null;
        List<String> customerTransactions = new ArrayList<>();

        // Collect customer records with customer all transactions based on flag 'C' & 'T' at first position of each value
        for(Text record: records) {
            String[] typeRecord = separator.split(record.toString(), 2);
            if("C".equals(typeRecord[0])) {
                customerRecord= typeRecord[1];
            } else if("T".equals(typeRecord[0])) {
                customerTransactions.add(typeRecord[1]);
            }
        }

        if (customerRecord != null) {
            if (customerTransactions.isEmpty()) {
                // Write customer record with empty/null transactions (left join)
                String noTransaction = ",".repeat(12);
                joinedRecordAsValue.set(customerRecord + noTransaction);
                context.write(custId, joinedRecordAsValue);
            } else {
                // Write customer records with each transaction record
                for (String transaction : customerTransactions) {
                    joinedRecordAsValue.set(customerRecord + "," +transaction);
                    context.write(custId, joinedRecordAsValue);
                }
            }

        }
    }
}
