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
