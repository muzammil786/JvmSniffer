JVM Sniffer
===========

This tool is developed as a part of [Integration Test Impact Analysis](http://man-cisrv-1:8000/tia/IntegrationTestImpactAnalysis) project. 

This tool aims to capture runtime information 
about the method calls by hooking into the target JVM.

Distribution
============

Run the gradle build task that will produce tar and zip packages under sniffer/build/distribution.

The runnable jar is sniffer/lib/sniffer.jar that executes *com.ueas.tai.Sniffer* class.  

Requirements
============

1. JDK 8
2. tools.jar should be on classpath. Currently added into the build and distribution.

Run
===

1. Copy the distribution file to the target host
1. Unzip/Untar the distribution file. This will expand into folder called _sniffer_
1. Edit config/config.properties. See Configuration section for details.
1. Navigate to sniffer/bin
1. Execute ./sniffer

Configuration
=============

This tool uses SUN Virtual Machine libraries to hook into an existing and running JVM. Therefore, the target JVM must be running into the debug mode. This can be done by passing the following arguments in the Java command:

```
-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=9865 
```

The details for config/config.properties are as follows:

```
# the target host which should be 'localhost' at the moment. This property will be used in future when Sniffer can be executed remotely
host=localhost 

# Debug port
port=9865

# which package of the target program we are interested to capture method calls
class.filter=com.ueas.ib.aftn.tcp.*

# path where method-trace.log will be outputted on exit
output.path=../output/
```
