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

public class InitializeDaoBenchmark {

	@Benchmark
	@BenchmarkMode(Mode.SingleShotTime)
	@Warmup(iterations = 0)
	public Object oldExample(BenchmarkState state) throws Exception {
		return GtfsLibrary.readGtfs(new File("./resources/rb_rut-aggregated-gtfs.zip"));
	}

}
