[![Build Status](https://travis-ci.org/skjolber/otp-benchmark.svg?branch=master)](https://travis-ci.org/skjolber/otp-benchmark)

# otp-benchmark
Project for benchmarking popular open source project [Open Trip Planner] using [JMH].

## License
[Apache 2.0]

# Obtain
The project is based on [Gradle].

# Usage
Modify the build version to your current snapshot, then run 

```
./gradlew --stop && ./gradlew clean jmhClasses jmh --refresh-dependencies --info
```

The JMH plugin seems to have trouble refreshing the project, so restart the Gradle deamon before running.
## Benchmarks

  * Load GTFS feed (from ZIP archive) - single shot

# History

 - 1.0.0: Initial version

[Apache 2.0]:          			http://www.apache.org/licenses/LICENSE-2.0.html
[issue-tracker]:       			https://github.com/skjolber/otp-benchmark/issues
[Gradle]:              		 	https://gradle.org/
[JMH]:							http://openjdk.java.net/projects/code-tools/jmh/
[visualization]:				https://skjolber.github.io/otp-benchmark/jmh/index.html
[Open Trip Planner]:			https://github.com/opentripplanner/OpenTripPlanner

