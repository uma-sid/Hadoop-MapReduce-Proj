# Hadoop-MapReduce-Project
This is project using Hadoop MapReduce 

## 1. Introduction
The Sprocket Central Pvt Ltd (imaginary) is a multinational bikes and cycling accessories manufacturing and selling organization, with its presence across 100+ countries and serving 96 million customers yearly.  There are around 500k sales transactions recorded daily and the number of customers that were involved in these transactions was around 300K. In total, the amount of data that is being generated daily is around 2GB. The company uses the OLTP system for its daily analysis of sales and other areas by only passing the necessary details to the OLTP systems. But the complete data that is being generated is stored in the HDFS.

## 2. Problem Description
The global sales of the company have declined in the past 3 quarters. So, the management decided to analyse the customer demographics linked to the sales data with the help of the Data Engineering team on a monthly basis.  The question they wanted to analyse is below.

-	Is the number of customers that purchasing the small size products is declining and how does this vary in terms of people having a car?

By understanding the answer for this question the sales and marketing team can target their marketing strategy straight to the customers segment that are declining in number.
Why MapReduce?

1.	As the files/amount of data is too large to be handled by any other conventional data warehousing or RDBMS systems and the data is already present in the existing HDFS architecture, MapReduce jobs will fit right into the business need here.
2.	The requirement here is to analyse the data on a monthly basis and generate a report, which is a batch processing job scenario, so implementing the 
MapReduce job is an ideal solution for this requirement.

## 3. Associated Dataset
The dataset is taken from Kaggle.com and can be found at the following url.
https://www.kaggle.com/datasets/archit9406/customer-transaction-dataset. This dataset is originally provided by KPMG in a virtual internship challenge, which originally belongs to one of the KPMG’s client few years ago. There are four csv files (Customer Address, CustomerDemographic, NewCustomerList and Transactions) present as part of the original dataset. As the current project is interested in analyzing the sales Transactions along with the customer demographics, only those 2 files are used as part of this project.

<img width="474" alt="image" src="https://user-images.githubusercontent.com/98278525/193547734-73d55de5-8cb3-4dec-bef5-03556fcc43fb.png">

## 4. Design and Implementation
### 4.1. Design:
To answer the question, the data in both the sheets must be joined and then the logic to find out the monthly-wise number of customers with and without car that are purchasing different product sizes has to be applied on the joined data.

So, here two MapReduce jobs to run sequentially are designed to achieve this. First MapReduce job joins the data from the two files by performing an inner join. Second MapReduce job takes the output of the first MapReduce job as input and then the business logic is applied on it. Only one Driver class (Customer TransactionDriver) is implemented to run the whole project. The driver class ‘Customer TransactionDriver’ runs the first map reduce job involving the two mappers and a reducer namely, ‘CustomerMapper’, ‘TransactionMapper’ and ‘JoinReducer’. Once this is run and the output is generated, then this output is passed as input for the next MapReduce job involving ‘CustomerCountMapper’ and ‘CustomerCountReducer’, which is run by the driver class.

<img width="528" alt="image" src="https://user-images.githubusercontent.com/98278525/193546911-42533370-534f-479b-adf7-8537cf1ab0fe.png">

<img width="465" alt="image" src="https://user-images.githubusercontent.com/98278525/193547105-f54046f9-17bd-48e9-bf58-011cd9d52be3.png">

### 4.2. Implementation:
For the complete implementation steps along with the screenshots please use the file Implementation_steps.pdf

## 5. Results & Evaluation
The obtained output is in the format as shown in the below screenshot.

<img width="416" alt="image" src="https://user-images.githubusercontent.com/98278525/193548563-9e4348dd-41ed-45e2-889e-20a4ceb0bfb0.png">


The obtained output file is exported on to Tableau and the visualizations are attempted to identify the sales trend by different customer demographics.
Observations:
1. It is observed that irrespective of the product size the number of transactions made by the car owned customers are not declining as shown in the below screenshot.
<img width="452" alt="image" src="https://user-images.githubusercontent.com/98278525/193548633-e1c83963-5ac8-4853-8e77-0d2663112410.png">

2. It is observed that irrespective of the product size the number of transactions made by the people without a car are indeed slightly declining as shown in the below screenshot.
<img width="452" alt="image" src="https://user-images.githubusercontent.com/98278525/193548722-e71dae62-8eb8-4715-96e3-2d07cdd454b8.png">

3.	It is observed that the number of customers having a car and purchasing the small sized products is not declining.![image](https://user-
<img width="452" alt="image" src="https://user-images.githubusercontent.com/98278525/193548779-0eb9a14f-2e41-47b1-bf58-6d0226603409.png">

4.	However, the number of customers without a car and purchasing the small sized products are declining.
<img width="452" alt="image" src="https://user-images.githubusercontent.com/98278525/193548848-12ce3532-a0cd-4f35-9fe3-64fa6beb2a9e.png">

With the observations 3 & 4 our research question is answered. Now, the sales and marketing team can find the reasons for the declining in sales transaction numbers in case of customers without a car, make a strategy towards running marketing campaigns to target them and thereby increasing the sales numbers.

## 6. References:
Nguyen, T. (2020). Chaining Multiple MapReduce Jobs with Hadoop/ Java. [online] Medium. Available at: https://towardsdatascience.com/chaining-multiple-mapreduce-jobs-with-hadoop-java-832a326cbfa7 [Accessed on 14 Apr, 2022].

logging.apache.org. (n.d.). LogManager (Apache Log4j API 2.14.1 API). [online] Available at: https://logging.apache.org/log4j/2.x/log4j-api/apidocs/org/apache/logging/log4j/LogManager.html [Accessed on 17 Apr, 2022].

www.programcreek.com. (n.d.). Java Code Examples for org.apache.log4j.LogManager. [online] Available at: https://www.programcreek.com/java-api-examples/org.apache.log4j.LogManager [Accessed 24 Apr. 2022].

www.kaggle.com. (n.d.). Customer_transaction_dataset. [online] Available at: https://www.kaggle.com/datasets/archit9406/customer-transaction-dataset [Accessed 24 Apr. 2022].

DataFlair. (n.d.). Output of one MapReduce job as input to another. [online] Available at: https://data-flair.training/forums/topic/output-of-one-mapreduce-job-as-input-to-another/ [Accessed 24 Apr. 2022].

Stack Overflow. (n.d.). hadoop - How to Use Output of one reducer as input to another mapper? [online] Available at: https://stackoverflow.com/questions/35475571/how-to-use-output-of-one-reducer-as-input-to-another-mapper [Accessed 25 Apr. 2022].
