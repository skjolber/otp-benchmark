package com.github.skjolber.onebusaway;

import java.io.File;
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

import com.github.skjolber.onebusaway.factory.GtfsCsvFileEntryHandler;
import com.github.skjolber.unzip.FileEntryHandler;
import com.github.skjolber.unzip.FileZipFileFactory;
import com.github.skjolber.unzip.NewLineSplitterEntryHandler;
import com.github.skjolber.unzip.ZipFileEngine;

public class InitializeDaoBenchmark {

	@Benchmark
	@BenchmarkMode(Mode.SingleShotTime)
	@Warmup(iterations = 0)
	public Object oldExample(BenchmarkState state) throws Exception {
		return GtfsLibrary.readGtfs(new File("/home/skjolber/Nedlastinger/example.zip"));
	}

	@Benchmark
	@BenchmarkMode(Mode.SingleShotTime)
	public Object newExample(BenchmarkState state) throws Exception {

		ParallellGtfsRelationalDaoImpl dao = new ParallellGtfsRelationalDaoImpl();

		GtfsCsvFileEntryHandler gtfsCsvFileEntryHandler = new GtfsCsvFileEntryHandler(dao);

		FileEntryHandler handler = new NewLineSplitterEntryHandler(16 * 1024 * 1024, gtfsCsvFileEntryHandler);

		File file = new File("/home/skjolber/Nedlastinger/example.zip");

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

						CalendarService calendarService = GtfsLibrary.createCalendarService(dao);

						GtfsReader reader = new GtfsReader();
						reader.setInputLocation(file);
						reader.setEntityStore(dao);

						GtfsFeedId feedId = new GtfsFeedId.Builder().fromGtfsFeed(reader.getInputSource()).build();

						return new GtfsContextImpl(feedId, dao, calendarService);
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
