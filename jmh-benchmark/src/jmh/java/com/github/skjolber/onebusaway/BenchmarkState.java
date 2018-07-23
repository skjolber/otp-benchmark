package com.github.skjolber.onebusaway;

import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

@State(Scope.Thread)
public class BenchmarkState {

	@Setup(Level.Trial)
	public void init() throws Exception {
		System.out.println("Initialize state");
	}
	
}
