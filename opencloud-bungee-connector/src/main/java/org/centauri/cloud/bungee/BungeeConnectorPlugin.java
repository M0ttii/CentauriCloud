package org.centauri.cloud.bungee;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.Getter;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import org.centauri.cloud.bungee.config.CloudConfiguration;
import org.centauri.cloud.bungee.netty.NetworkHandler;
import org.centauri.cloud.opencloud.connector.netty.Client;

public class BungeeConnectorPlugin extends Plugin{
	
	@Getter private static BungeeConnectorPlugin instance;
	@Getter private static Logger pluginLogger;
	@Getter private Client client;
	@Getter private CloudConfiguration cloudConfiguration;
	
	@Override
	public void onLoad() {
		BungeeConnectorPlugin.instance = this;
		BungeeConnectorPlugin.pluginLogger = this.getLogger();
		
		initConfig();
		
		getPluginLogger().info("Loaded CentauriCloud bungee connector.");
	}
	
	@Override
	public void onEnable() {
		
		getPluginLogger().info("[" + cloudConfiguration.getPrefix() + "] -> " + cloudConfiguration.getHostname() + ":" + cloudConfiguration.getPort());
		
		new Thread(() -> {
 			this.client = new Client(new NetworkHandler(), cloudConfiguration.getHostname(), cloudConfiguration.getPort());
 		}, "Netty-Thread").start();
		
		getPluginLogger().info("Enabled CentauriCloud bungee connector.");
	}
	
	@Override
	public void onDisable() {
		getPluginLogger().info("Disabled CentauriCloud bungee connector.");
	}
	
	private void initConfig(){
		try {
			
			if(!getDataFolder().exists()){
				getDataFolder().mkdirs();
			}
			
			File config = new File(getDataFolder(), "config.yml");
			
			if (!config.exists()) {
				try (InputStream in = getResourceAsStream("config.yml")) {
					Files.copy(in, config.toPath());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			Configuration configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(config);
			this.cloudConfiguration = new CloudConfiguration(configuration);
			
		} catch (IOException ex) {
			Logger.getLogger(BungeeConnectorPlugin.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
