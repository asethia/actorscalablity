# Actor scalablity
This is sample application to test how many messages a cactor can process by using RoundRobinPool.

The application can be built using following command

mvn install

To run the application from the target folder:

java -jar simplerouter-1.0-jar-with-dependencies.jar <pool size> <max number of message to test>
