package pw.azureagst.buildoff;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class Event implements Listener {

    // TODO: Fix this lol

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        player.sendTitle("Welcome to the Build-Off!", ":)", 1,5,1);
    }

}
