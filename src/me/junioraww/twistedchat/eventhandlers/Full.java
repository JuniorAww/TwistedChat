package me.junioraww.twistedchat.eventhandlers;

import me.junioraww.twistedchat.Main;
import net.luckperms.api.cacheddata.CachedMetaData;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Full implements Listener {
    private final static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");

    @EventHandler(priority = EventPriority.LOWEST)
    public void chat(AsyncPlayerChatEvent event) {
        var message = event.getMessage();
        var sender = event.getPlayer();
        var global = message.charAt(0) == '!';
        var recipients = event.getRecipients();
        for(var player : recipients) {
            if(global) continue;
            if(player.getWorld().equals(sender.getWorld()) && player.getLocation().distance(sender.getLocation()) <= 200) continue;
            recipients.remove(player);
        }

        var channel = global ? "§6[G]" : "§3[L]";
        LocalDateTime now = LocalDateTime.now();
        var time = dtf.format(now);
        var hoverText = new Text("§eОтправлено: §f" + time + "\nУвидело: §f" + recipients.size());
        if(global) message = message.substring(1);

        CachedMetaData metaData = Main.luckPerms.getPlayerAdapter(Player.class).getMetaData(sender);
        var prefix = metaData.getPrefix();
        if(prefix == null) prefix = "";
        var visibleName = colorize(prefix) + sender.getDisplayName();

        for(var player : recipients) {
            var result = message;

            var username = player.getName();
            if(result.matches(username)) {
                result = result.replaceAll(username, "§b" + username + "§f");
                player.playSound(player.getLocation(), Sound.ENTITY_CAT_PURR, 1, 1);
            }

            var format = new TextComponent(channel + " §f" + visibleName + "§e: §f" + result);
            format.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, hoverText));
            player.spigot().sendMessage(format);
        }

        event.setCancelled(true);
    }

    private String colorize(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

}
