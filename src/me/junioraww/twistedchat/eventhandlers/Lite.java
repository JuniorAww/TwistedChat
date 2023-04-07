package me.junioraww.twistedchat.eventhandlers;

import me.junioraww.twistedchat.Main;
import net.luckperms.api.cacheddata.CachedMetaData;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.stream.Collectors;

public class Lite implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void chat(AsyncPlayerChatEvent event) {
        var format = event.getFormat();
        var player = event.getPlayer();

        CachedMetaData metaData = Main.luckPerms.getPlayerAdapter(Player.class).getMetaData(player);
        var prefix = metaData.getPrefix();
        if(prefix == null) prefix = "";
        format = colorize(prefix) + player.getDisplayName() + "§7: " + event.getMessage();

        event.setFormat(format);
    }

    private String colorize(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

}
