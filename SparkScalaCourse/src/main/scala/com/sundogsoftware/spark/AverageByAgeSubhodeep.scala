package com.sundogsoftware.spark

//import breeze.numerics.round
import org.apache.log4j._
import org.apache.spark.sql._
import org.apache.spark.sql.functions._

object AverageByAgeSubhodeep {
  case class Person(id:Int,name:String,age:Int,friends:Int)

  def main(args: Array[String]) {
    Logger.getLogger("org").setLevel(Level.ERROR)
    val spark = SparkSession
      .builder
      .master("local[*]")
      .appName("avg friends by age")
      .getOrCreate()
    import spark.implicits._
    val ds = spark.read
      .option("inferSchema","true")
      .option("header","true")
      .csv("data/fakefriends.csv")
      .as[Person]
    ds.groupBy("age").agg(round(avg("friends"),2).alias("avg friends")).sort("avg friends").show()
//    val ds3=ds2.select(ds2("age"),ds2("avg friends"))

//    ds.createOrReplaceTempView("table1")
//    val ans=spark.sql("select age,avg(friends) from table1 group by age")
//    ans.show()
  }
}
