package com.sundogsoftware.spark
import org.apache.log4j._
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types.{StructType,IntegerType,FloatType}

object TotalSpentByCustomerSubhodeep {
  case class Order(customer:Int,id:Int,money:Float)

    def main(args: Array[String]){
      Logger.getLogger("org").setLevel(Level.ERROR)
      val spark=SparkSession
        .builder
        .master("local[*]")
        .appName("scala")
        .getOrCreate()

      val schema=new StructType()
        .add("customer",IntegerType)
        .add("id",IntegerType)
        .add("money",FloatType)

      import spark.implicits._
      val ds= spark.read.schema(schema).csv("data/customer-orders.csv")
        .as[Order]

      ds.groupBy("customer").agg(round(sum("money"),2)).withColumnRenamed("round(sum(money), 2)","Total_Spent").sort("Total_Spent").show()



    }

}
