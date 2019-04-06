package pw.azureagst.buildoff;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BuildMain implements CommandExecutor {
    private boolean isActive = false;
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
                server.broadcastMessage(main.prefix + player.getPlayerListName() + " Started the next round!");
                // TODO: Add support for starting rounds
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
