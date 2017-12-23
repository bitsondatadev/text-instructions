# OSRM Text Instructions - Java J2V8 Wrapper

[![Build Status](https://travis-ci.org/brianolsen87/text-instructions.svg?branch=master)](https://travis-ci.org/brianolsen87/text-instructions)

[![Maven Central](https://img.shields.io/maven-central/v/com.github.brianolsen87/text-instructions.svg)](http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22com.github.brianolsen87%22)

This library wraps javascript functions from the [OSRM Text Instructions](https://github.com/Project-OSRM/osrm-text-instructions) library that is programmed in javascript as a java project. The osrm-text-instructions library uses the [MapBox LegStep](https://www.mapbox.com/android-docs/api/mapbox-java/libjava-services/2.2.9/com/mapbox/services/api/directions/v5/models/LegStep.html) to model the routes and compiles human readable instructions on how to follow the next direction on a route. I had the need and idea to port the node module by running a V8 instance on the JVM using the wonderful [J2V8 library](https://github.com/eclipsesource/J2V8) and allowing direct calls to the main javascript library from Java. This will make keeping up the development simple as new functionality arises. 

## Some example uses
```
try (OSRMTextInstructions textInstructions = new OSRMTextInstructions(VERSION)) {
	String wayName = "Route 66";
	StepManeuver maneuver = new StepManeuver("turn", "left", 1);
	LegStep step = new LegStep();
	
	step.setManeuver(maneuver);
	step.setName(wayName);
			
	assertEquals("Turn left onto Route 66", textInstructions.compile("en", step, null));
	assertEquals("Gire a la izquierda en Route 66", textInstructions.compile("es", step, null));
	assertEquals("Svolta a sinistra in Route 66", textInstructions.compile("it", step, null));
	assertEquals("Ga linksaf naar Route 66", textInstructions.compile("nl", step, null));
	assertEquals("左转，上Route 66", textInstructions.compile("zh-Hans", step, null));
}
```
## Available on the central maven repository
This library is available on [The Central Repository](http://repo1.maven.apache.org/maven2/com/github/brianolsen87/text-instructions/0.11.0/). My versioning will match the OSRM Text Instruction projects and will have the maven dependency tags for the pom included on [the release page](https://github.com/brianolsen87/text-instructions/releases). I will stay posted when a version has updated and keep up with original library as quickly as I can.

## Rolling your own version
I have pre-installed the node modules in the resource folders to avoid the user needing to install node.js or npm. If, however, you want the latest version you will need to go to [osrm-text-instruction release page](https://github.com/Project-OSRM/osrm-text-instructions/releases) and download the version you like as a zip file and add it to src/main/resources/node_modules. You'll then need ot update OSRMTextInstructions.MODULE_VERSION to the verison that you have added. At this point you must verify that any new or changed methods in install.js are wrapped correctly in [OSRMTextInstructions.java](https://github.com/brianolsen87/text-instructions/blob/master/src/main/java/us/brianolsen/instructions/OSRMTextInstructions.java). If you don't want to do this just wait a few days and i'll add the latest version.

## FYI
The [MapBox LegStep](https://www.mapbox.com/android-docs/api/mapbox-java/libjava-services/2.2.9/com/mapbox/services/api/directions/v5/models/LegStep.html) model supplements the [OSRM RouteStep object](https://github.com/Project-OSRM/osrm-backend/blob/master/docs/http.md#routestep-object) required in the compile steps. While a [java version has already been created](https://github.com/Project-OSRM/osrm-text-instructions.java/), it doesn't seem to be maintained currently to keep it up to date with the main project but I have drawn on some of its setup for my own project. However it does have an android/gradle setup which may be useful for someone developing on Android.
