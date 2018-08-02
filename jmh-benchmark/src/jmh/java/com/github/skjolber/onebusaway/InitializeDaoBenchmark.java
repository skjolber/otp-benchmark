package com.github.skjolber.onebusaway;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.onebusaway.gtfs.serialization.GtfsReader;
import org.onebusaway.gtfs.services.GtfsRelationalDao;
import org.onebusaway.gtfs.services.calendar.CalendarService;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.opentripplanner.graph_builder.module.GtfsFeedId;
import org.opentripplanner.gtfs.GtfsContext;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Warmup;
import org.opentripplanner.gtfs.GtfsLibrary;
import org.opentripplanner.model.impl.OtpTransitBuilder;

import com.github.skjolber.onebusaway.factory.GtfsCsvFileEntryHandler;
import com.github.skjolber.unzip.FileEntryHandler;
import com.github.skjolber.unzip.FileZipFileFactory;
import com.github.skjolber.unzip.NewLineSplitterEntryHandler;
import com.github.skjolber.unzip.ZipFileEngine;

public class InitializeDaoBenchmark {

	private static final File file = new File("./resources/rb_rut-aggregated-gtfs.zip");
	
	@Benchmark
	@BenchmarkMode(Mode.SingleShotTime)
	@OutputTimeUnit(TimeUnit.MILLISECONDS)
	@Warmup(iterations = 0)
	public Object oldExample(BenchmarkState state) throws Exception {
		return GtfsLibrary.readGtfs(file);
	}

	@Benchmark
	@BenchmarkMode(Mode.SingleShotTime)
	@OutputTimeUnit(TimeUnit.MILLISECONDS)
	@Warmup(iterations = 0)
	public Object newExample32(BenchmarkState state) throws Exception {
		return load(32);
	}

	@Benchmark
	@BenchmarkMode(Mode.SingleShotTime)
	@OutputTimeUnit(TimeUnit.MILLISECONDS)
	@Warmup(iterations = 0)
	public Object newExample64(BenchmarkState state) throws Exception {
		return load(64);
	}

	@Benchmark
	@BenchmarkMode(Mode.SingleShotTime)
	@OutputTimeUnit(TimeUnit.MILLISECONDS)
	@Warmup(iterations = 0)
	public Object newExample48(BenchmarkState state) throws Exception {
		return load(48);
	}

	@Benchmark
	@BenchmarkMode(Mode.SingleShotTime)
	@OutputTimeUnit(TimeUnit.MILLISECONDS)
	@Warmup(iterations = 0)
	public Object newExample16(BenchmarkState state) throws Exception {
		return load(16);
	}

	private Object load(int size) throws IOException {
		OtpTransitBuilder dao = new OtpTransitBuilder();
		
		GtfsCsvFileEntryHandler gtfsCsvFileEntryHandler = new GtfsCsvFileEntryHandler(dao);

		int chunkLength = size * 1024 * 1024;

		FileEntryHandler handler = new NewLineSplitterEntryHandler(chunkLength, gtfsCsvFileEntryHandler);

		List<String> files = new ArrayList<>();
		files.add("agency.txt");
		files.add("block.txt");
		files.add("shapes.txt");
		files.add("timetable_notes.txt");
		files.add("routes.txt");
		files.add("stops.txt");
		files.add("trips.txt");
		files.add("stop_times.txt");
		files.add("calendar.txt");
		files.add("calendar_dates.txt");
		files.add("fare_attributes.txt");
		files.add("fare_rules.txt");
		files.add("frequencies.txt");

		int count = Runtime.getRuntime().availableProcessors();

		ZipFileEngine engine = new ZipFileEngine(handler, count);

		try {
			if(engine.handle(new FileZipFileFactory(file), Arrays.asList("feed_info.txt"))) {
				System.out.println("Got feed info");
				if(engine.handle(new FileZipFileFactory(file), files)) {
					System.out.println("Got feed info");

					boolean handle = engine.handle(new FileZipFileFactory(file), Arrays.asList("transfers.txt"));
					if(handle) {
						System.out.println("Success");

						return dao.build();
					}
				}
			}
		} finally {
			engine.close();
		}
		throw new RuntimeException();
	}

	private static class GtfsContextImpl implements GtfsContext {

		private GtfsFeedId _feedId;

		private GtfsRelationalDao _dao;

		private CalendarService _calendar;

		public GtfsContextImpl(GtfsFeedId feedId, GtfsRelationalDao dao, CalendarService calendar) {
			_feedId = feedId;
			_dao = dao;
			_calendar = calendar;
		}

		@Override
		public GtfsFeedId getFeedId() {
			return _feedId;
		}

		@Override
		public GtfsRelationalDao getDao() {
			return _dao;
		}

		@Override
		public CalendarService getCalendarService() {
			return _calendar;
		}
	}

}
