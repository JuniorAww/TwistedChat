package me.junioraww.twistedchat;

import me.junioraww.twistedchat.eventhandlers.Full;
import me.junioraww.twistedchat.eventhandlers.Lite;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    public static LuckPerms luckPerms;
    public boolean supportsRGB;
    @Override
    public void onEnable() {
        this.luckPerms = this.getServer().getServicesManager().load(LuckPerms.class);
        var version = getVersion(Bukkit.getServer());
        if(version < 16) {
            Bukkit.getPluginManager().registerEvents(new Lite(), this);
            Bukkit.getLogger().info("Version lower 1.16.5 detected, using lite version of plugin");
        } else {
            Bukkit.getPluginManager().registerEvents(new Full(), this);
        }
    }

    public static Integer getVersion(Server server) {
        String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
        return Integer.parseInt(version.replace("v1_", "").replaceAll("_R\\d",""));
    }
}
