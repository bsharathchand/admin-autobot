package com.novicehacks.autobot.executor;

import static org.junit.Assert.fail;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.novicehacks.autobot.categories.EnvironmentalTest;
import com.novicehacks.autobot.categories.FunctionalTest;
import com.novicehacks.autobot.categories.IgnoredTest;
import com.novicehacks.autobot.config.AutobotConfigManager;

public class TestCommandExecutorTask {

	@Before
	public void setUp() throws InterruptedException, ExecutionException, TimeoutException {
		AutobotConfigManager.loadResourceConfig ();
	}

	@Test (timeout = 60000)
	@Ignore
	@Category ({ EnvironmentalTest.class, FunctionalTest.class, IgnoredTest.class })
	public void testCommandExecutor() throws InterruptedException {
		CommandExecutorTask task;
		task = new CommandExecutorTask ();
		try {
			task.run ();
		} catch (Exception ex) {
			ex.printStackTrace ();
			System.out.println (ex.getSuppressed ());
			fail ("Exception raised while command execution");
		}
	}
}
