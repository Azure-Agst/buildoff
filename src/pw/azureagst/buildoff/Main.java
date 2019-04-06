package pw.azureagst.buildoff;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class Main extends JavaPlugin {
    FileConfiguration config = getConfig();
    String prefix = config.getString("prefix");

    @Override
    public void onEnable() {
        // Config Stuff
        File configFile = new File(this.getDataFolder(), "config.yml");
        if (!configFile.exists()){
            this.saveDefaultConfig();
        }

        // Register as event listener
        getServer().getPluginManager().registerEvents(new Event(), this);

        // Register our commands
        this.getCommand("build").setExecutor(new BuildMain(this));
    }

    @Override
    public void onDisable() {
        saveConfig();
    }
}
