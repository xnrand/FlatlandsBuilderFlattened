package net.dkebnh.bukkit.FlatlandsBuilder;

import java.util.logging.Logger;

import org.bukkit.plugin.PluginDescriptionFile;

public class FLBLogger {

	private final FlatlandsBuilder plugin;
	private final Logger log;

	public FLBLogger(FlatlandsBuilder plugin) {
		this.plugin = plugin;
		this.log = Logger.getLogger("Minecraft");
	}

	private String formatMessage(String message) {
		PluginDescriptionFile pdFile = plugin.getDescription();
		return "[" + pdFile.getName() + "] " + message;
	}

	public void infoMSG(String message) {
		this.log.info(this.formatMessage(message));
	}

	public void warningMSG(String message) {
		this.log.warning(this.formatMessage(message));
	}

	public void severeMSG(String message) {
		this.log.severe(this.formatMessage(message));
	}

}
