package com.github.skjolber.onebusaway;

import java.io.File;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.opentripplanner.gtfs.GtfsLibrary;

public class InitializeDaoBenchmark {

	@Benchmark
	@BenchmarkMode(Mode.SingleShotTime)
	public Object oldExample(BenchmarkState state) throws Exception {
		return GtfsLibrary.readGtfs(new File("./resources/rb_rut-aggregated-gtfs.zip"));
	}


}
