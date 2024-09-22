package com.sundogsoftware.spark

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkContext

object TotalAmountSpentSubhodeep {
  def parsetext(line: String): (Int,Float)={
    val fields=line.split(',')
    val field1=fields(0).toInt
    val field3=fields(2).toFloat
    (field1,field3)
  }
//  def f(t: (Int,Float)): (Int,String)={
//    val t2=(t._1, f"$t._2 %.2f")
//    t2
//  }
  def main(args: Array[String]) {
    Logger.getLogger("org").setLevel(Level.ERROR)
    val sc = new SparkContext("local[*]", "Subhodeep")
    val lines = sc.textFile("data/customer-orders.csv")
//    println(lines)
    val parsedlines = lines.map(parsetext)
    val totalByCustomer=parsedlines.reduceByKey((x,y)=>x+y)
    val flipped=totalByCustomer.map(x=>(x._2,x._1))
    val ans=flipped.sortByKey()
    val results=ans.collect()
    for(i<-results){
      println(f"Customer ${i._2}%02d spent a total of $$${i._1}%.2f")
    }

  }
}
