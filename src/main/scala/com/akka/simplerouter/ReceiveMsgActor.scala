package com.akka.simplerouter

import akka.actor._
import akka.routing.ActorRefRoutee
import akka.routing.RoundRobinPool
import akka.routing.RoundRobinRoutingLogic
import akka.routing.Router
import com.akka.simplerouter.MsgObject._
import scala.concurrent.duration.FiniteDuration
import java.util.concurrent.TimeUnit
import scala.concurrent.duration.Duration


object MsgObject {
  case class InitRouter(numOfInstance: Int, numOfMessage: Int)
  case class NumOfMessage(msgCount:Int)
}

class ReceiveMsgActor extends Actor with ActorLogging {

  var startedTime = System.currentTimeMillis();
  var numOfMsg = 0
  var count = 0
  var processActor: ActorRef=null

  import context._
  def receive: Receive = {
    case initm @ InitRouter(numOfInstances, numOfMsg) =>
      processActor=context.actorOf(RoundRobinPool(numOfInstances).props(Props(classOf[ProcessorActor],self)), "router")
      become(process)
      log.info("Initialization is done")
      self ! NumOfMessage(numOfMsg)
  }
  
  def process:Receive={
    case m @ NumOfMessage(msgCount) =>
      startedTime = System.currentTimeMillis();
      numOfMsg = msgCount;
      (1 to msgCount) map { i => processActor ! "Job Id " + i + "# send"}
    case "Done" =>
      count = count + 1
      if (count == numOfMsg) {
        var now = System.currentTimeMillis();
        log.info(s"Total time took {} ms to process {} messages", (now - startedTime), count)
        context.stop(self)
        context.system.terminate()  
      }
  }

}