package com.akka.simplerouter

import akka.actor._
import scala.concurrent.duration.FiniteDuration
import java.util.concurrent.TimeUnit

class ProcessorActor(master: ActorRef) extends Actor with ActorLogging {

  import context._
  import com.akka.simplerouter.MsgObject._
  def receive: Receive = {
    case m @ _ =>
      context.system.scheduler.scheduleOnce(FiniteDuration(10, TimeUnit.MILLISECONDS), master, "Done");
  }
}