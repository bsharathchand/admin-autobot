package com.novicehacks.autobot.config;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.novicehacks.autobot.BotUtils;
import com.novicehacks.autobot.types.Command;
import com.novicehacks.autobot.types.Executable;
import com.novicehacks.autobot.types.Monitor;
import com.novicehacks.autobot.types.Server;
import com.novicehacks.autobot.types.ShellCommand;
import com.novicehacks.autobot.types.UnixServer;

/**
 * <p>
 * Will hold the configuration object, and other object for using across the
 * application where ever needed. This is a singleton instance and also an
 * immutable object. Hence once the data is loaded, there will be no
 * modifications for this object.
 * </p>
 * <p>
 * Below are some of the configurations that this Config bean hold
 * <ul>
 * <li>Configuration (System Configuration)</li>
 * <li>Collection of Servers</li>
 * <li>Collection of Commands</li>
 * <li>Collection of Executables</li>
 * <li>Collection of Monitors</li>
 * </ul>
 * </p>
 * 
 * @author Sharath Chand Bhaskara for NoviceHacks
 *
 */
public final class ResourceConfig {

	private static AtomicBoolean ConfigLoaded = new AtomicBoolean(false);
	private Set<Server> servers;
	private Map<String, Server> serverMap;
	private Map<String, Command> commandMap;
	private Set<Command> commands;
	private Set<Executable> executables;
	private Set<Monitor> monitors;

	private Logger logger = LogManager.getLogger(ResourceConfig.class);

	/**
	 * Private Constructor, will make no instance created outside this class.
	 */
	private ResourceConfig() {
	}

	/**
	 * Loads the configurations for this application. Tyring to invoke this
	 * method more than once will throw IllegalStateException.
	 * <p>
	 * <em>Throwing the exception is being reviewed and might be modified in the next releases</em>
	 * </p>
	 * 
	 * @param serverSet
	 *            Set of servers where the commands to be executed
	 * @param command
	 *            Set of command to execute on the servers
	 * @param executables
	 *            Set of commands that has to be executed in a scheduled
	 *            interval
	 * @param monitors
	 *            Set of commands to be monitored and reported if crossing the
	 *            threshold
	 */
	protected void loadConfig(Set<Server> serverSet, Set<Command> command,
			Set<Executable> executables, Set<Monitor> monitors) {
		logger.entry();
		if (!ConfigLoaded.get()) {
			ConfigLoaded.set(true);
			this.servers = serverSet;
			this.commands = command;
			this.monitors = monitors;
			this.executables = executables;
			this.commandMap = BotUtils.CreateMap(command, commandType());
			this.serverMap = BotUtils.CreateMap(serverSet, serverType());
		} else {
			throw new IllegalStateException(
					"Load Config cannot be called multiple times");
		}
		logger.exit();
	}

	/**
	 * Creates a sample type for passing to BotUtils.createMap method.
	 * 
	 * @return
	 */
	private Server serverType() {
		Server server;
		server = new UnixServer("Temp Server");
		return server;
	}

	/**
	 * Create a sample type for passing to BotUtils.createMap method.
	 * 
	 * @return
	 */
	private Command commandType() {
		Command command;
		command = new ShellCommand("Temp Command");
		return command;
	}

	/**
	 * Returns the singleton instance of the AutobotConfig object.
	 * 
	 * @return Configuration Object will the system and resource configurations.
	 */
	public static ResourceConfig getInstance() {
		return ResourceConfigSingleton.getInstance();
	}

	/**
	 * A Set of Servers parsed from the resource file.
	 * 
	 * @return
	 */
	public Set<Server> servers() {
		return servers;
	}

	/**
	 * A Set of Commands parsed from the resource file
	 * 
	 * @return
	 */
	public Set<Command> commands() {
		return commands;
	}

	/**
	 * A Set of Executables parsed from the resource file
	 * 
	 * @return
	 */
	public Set<Executable> executables() {
		return executables;
	}

	/**
	 * A Set of Monitors parsed from the resource file.
	 * 
	 * @return
	 */
	public Set<Monitor> monitors() {
		return monitors;
	}

	/**
	 * Singleton implementation using an inner class.
	 * 
	 * @author Sharath Chand Bhaskara for NoviceHacks
	 *
	 */
	private static class ResourceConfigSingleton {
		private static ResourceConfig instance = new ResourceConfig();

		public static ResourceConfig getInstance() {
			return instance;
		}
	}

	/**
	 * A Map of Servers identified by serverId as key.
	 * 
	 * @return
	 */
	public Map<String, Server> serverMap() {
		return serverMap;
	}

	/**
	 * A Map of Commands identified by commandId as key.
	 * 
	 * @return
	 */
	public Map<String, Command> commandMap() {
		return commandMap;
	}

}