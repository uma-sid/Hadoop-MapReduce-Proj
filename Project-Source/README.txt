+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 TO RUN JOB USE FOLLOWING SYNTAX
+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

>> hadoop jar hadoop-project-1.0.jar CustomerTransactionJoinDriver <customer-file-path> <transaction-file-path> <output-path>




+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 COMMAND SAMPLES
+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

>> hadoop jar hadoop-project-1.0.jar CustomerTransactionJoinDriver /user/hadoop/input/Customers.csv /user/hadoop/input/Transactions.csv /user/hadoop/output/
