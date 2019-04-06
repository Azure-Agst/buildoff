package pw.azureagst.buildoff;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.Instant;

public class BuildMain implements CommandExecutor {
    private static long startTime = 0;
    private static long endTime = 0;
    private static int timeLeft = 0;
    private boolean eventIsActive = false;
    private Main main;

    BuildMain(Main main){
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        // init a few vars
        Server server = Bukkit.getServer();
        Player player;

        // check if player sent command
        if (!(sender instanceof Player)){
            server.broadcastMessage(main.prefix + "Something's off... Hey Admin, check logs?");
            return false;
        } else {
             player = (Player) sender;
        }

        // Check if argument was specified
        if (args.length == 0){
            server.broadcastMessage(main.prefix + "Chiles eSports Build-off Plugin v0.0.1");
            return true;
        }

        // Main switch
        switch (args[0]){
            case "start":
                if (eventIsActive){
                    server.broadcastMessage(main.prefix + "There's already an ongoing round!");
                    return true;
                }

                server.broadcastMessage(main.prefix + player.getPlayerListName() + " Started the next round!");

                // Set global vars
                eventIsActive = true;
                startTime = Instant.now().toEpochMilli();
                endTime = startTime + (15 * 60000);

                // Main Timer
                new BukkitRunnable(){
                    @Override
                    public void run() {
                        // Do time math
                        long curtime = startTime = Instant.now().toEpochMilli();
                        BuildMain.timeLeft = (int)(BuildMain.endTime - curtime)/1000;

                        //10 minutes left
                        if (BuildMain.timeLeft == 10*60){
                            // Alert users via title
                        } else if (BuildMain.timeLeft == 5*60){
                            // Alert users via title
                        }
                    }
                }.runTaskTimer(main, 20,20);



            case "help":
                // TODO: Create Help Command
                break;
            default:
                server.broadcastMessage(main.prefix + "Chiles eSports Build-off Plugin v0.0.1");
                break;
        }
        return true;
    }
}
