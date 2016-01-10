package com.akka.simplerouter

import akka.actor._
import scala.concurrent.duration._
import java.util.concurrent.TimeUnit
import com.akka.simplerouter.MsgObject._

object SimpleRouterMain {

  def main(args: Array[String]):Unit = {

    val nInstances:Int = if (args.isEmpty) {println("using default number of processor instances");2000} else args(0).toInt
    
    val nOfMessages:Int = if (args.isEmpty) {println("using default number of maximum messages to be tested");1000} else args(1).toInt
    
    println(s"Number of instances $nInstances and number of maximum messages to be tested $nOfMessages")
    
    val systemActor = ActorSystem("SimpleRouterSystem")

    val actor = systemActor.actorOf(Props[ReceiveMsgActor])
    
    actor ! InitRouter(nInstances, nOfMessages)
   
  }

}