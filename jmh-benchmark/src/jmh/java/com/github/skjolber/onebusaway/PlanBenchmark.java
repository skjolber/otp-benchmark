package com.github.skjolber.onebusaway;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Warmup;
import org.opentripplanner.gtfs.GtfsLibrary;

public class PlanBenchmark {

	// http://localhost:8080/otp/routers/default/plan?fromPlace=stop+Sofies+plass+%3A%3A59.924007%2C10.733511&toPlace=stop+Jernbanetorget+%3A%3A59.91227%2C10.751326&time=1%3A14am&date=07-30-2018&mode=TRANSIT%2CWALK&maxWalkDistance=804.672&arriveBy=false&wheelchair=false&locale=en
	/*
	@Benchmark
	@BenchmarkMode(Mode.SampleTime)
	@OutputTimeUnit(TimeUnit.MILLISECONDS)
	@Measurement(time = 24, timeUnit = TimeUnit.HOURS, batchSize = 1)
	@Warmup(iterations = 0)
	public Object plan(BenchmarkState state) throws Exception {
		return GtfsLibrary.readGtfs(new File("./resources/rb_rut-aggregated-gtfs.zip"));
	}
	*/
}
