package com.novicehacks.autobot.core;

import java.lang.Thread.UncaughtExceptionHandler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GenericUncaughtExceptionHandler implements UncaughtExceptionHandler {
	private Logger	logger	= LogManager.getLogger (this);

	@Override
	public void uncaughtException(Thread t, Throwable e) {
		try {
			this.logger.error ("Uncaught Exception On Thread {} ", t.getName (), e);
		} catch (Throwable error) {
			this.logger.error (error);
		}
	}

}