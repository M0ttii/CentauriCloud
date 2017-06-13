package org.centauri.cloud.spigot;

import java.util.logging.Logger;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import org.centauri.cloud.opencloud.connector.netty.Client;
import org.centauri.cloud.spigot.config.CloudConfiguration;
import org.centauri.cloud.spigot.netty.NetworkHandler;

public class SpigotConnectorPlugin extends JavaPlugin{
	
	@Getter private static SpigotConnectorPlugin instance;
	@Getter private static Logger pluginLogger;
	@Getter private Client client;
	@Getter private CloudConfiguration cloudConfiguration;
	
	@Override
	public void onLoad() {
		SpigotConnectorPlugin.instance = this;
		SpigotConnectorPlugin.pluginLogger = this.getLogger();
		
		getPluginLogger().info("Loaded CentauriCloud spigot connector.");
		
	}
	
	@Override
	public void onEnable() {
		this.cloudConfiguration = new CloudConfiguration(getConfig());
		
		getPluginLogger().info(String.format("%s -> %s:%s", cloudConfiguration.getPrefix(), cloudConfiguration.getHostname(), cloudConfiguration.getPort()));
			
		new Thread(() -> {
			this.client = new Client(new NetworkHandler(), cloudConfiguration.getHostname(), cloudConfiguration.getPort());
		}, "Netty-Thread").start();
		
		
		getPluginLogger().info("Enabled CentauriCloud spigot connector.");
	}
	
	@Override
	public void onDisable() {
		getPluginLogger().info("Disabled CentauriCloud spigot connector.");
	}
}