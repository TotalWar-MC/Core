package com.steffbeard.totalwar.modules.crops;

public class PersistConfig {
	
	// Database stuff
	public String databaseName;
	public String host;
	public String port;
	public String user;
	public String password;
	public String prefix;
	
	public int poolSize;

	public boolean enabled;
	
	// First execution delay. Name of parameter is misleading.
	public long unloadBatchPeriod;
	
	// Delay inbetween executions of unload batches. NOT max time of execution, but rather time inbetween.
	// Execution time is unbounded.
	public long unloadBatchMaxTime;
	
	// the chance that a grow_event on a block will trigger a plant chunk load
	public double growEventLoadChance;
	
	// flag that determines if the plugin should log db load and save events.
	public boolean logDB;
	
	// flag that determines if the plugin should log db load and save events
	// this differs from logDB in that it will ONLY log db load/save events if 
	// they go over a certain time, which is defined in the next two variables
	// this overrides logDB (if logDB is false)
	public boolean productionLogDb;
	
	// values that set the minimum amount of time (in milliseconds) that a unload or load
	// event has to take before we log it. 
	public long productionLogLoadMintime;
	public long productionLogUnloadMintime;
	
	// whether to cache the entire database into memory and not do delayed loading
	// when this is enabled, we still unload chunks normally (but don't actually eject it from memory
	// just to save to the database in case the server crashes...)
	public boolean cacheEntireDatabase;
}
