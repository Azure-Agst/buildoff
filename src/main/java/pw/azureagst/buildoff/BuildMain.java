package pw.azureagst.buildoff;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.*;

public class BuildMain implements CommandExecutor {
    private static long startTime = 0;
    private static long endTime = 0;
    private static Score timeLeft;
    private boolean eventIsActive = false;
    private Main main; private Server server;
    private BukkitTask timer;

    ScoreboardManager manager = Bukkit.getScoreboardManager();
    Scoreboard board = manager.getNewScoreboard();
    Objective objective;

    BuildMain(Main main){
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        // init a few vars
        server = Bukkit.getServer();
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
                // cancel if running event
                if (eventIsActive){
                    server.broadcastMessage(main.prefix + "There's already an ongoing round!");
                    return true;
                } else {
                    eventIsActive = true;
                    server.broadcastMessage(main.prefix + player.getPlayerListName() + " Started the next round!");
                    startTime = System.currentTimeMillis();
                    endTime = startTime + (15 * 60000);
                }

                // Scoreboard stuff
                objective = board.registerNewObjective("time", "dummy", "Time");
                objective.setDisplaySlot(DisplaySlot.SIDEBAR);
                timeLeft = objective.getScore(ChatColor.GREEN + "Sec Left:");

                // Main Timer
                timer = new BukkitRunnable(){
                    @Override
                    public void run() {
                        // Do time math
                        long curtime = System.currentTimeMillis();
                        int remain = (int)(BuildMain.endTime - curtime)/1000;

                        timeLeft.setScore(remain);

                        //10 minutes left
                        if (remain == 10*60){
                            server.broadcastMessage(main.prefix + "10 Minutes Left!");
                        } else if (remain == 5*60){
                            server.broadcastMessage(main.prefix + "5 Minutes Left!");
                        }

                        for(Player online : Bukkit.getOnlinePlayers()){
                            online.setScoreboard(board);
                        }
                    }
                }.runTaskTimer(main, 0,20);
                break;

            case "stop":
                if (!eventIsActive){
                    server.broadcastMessage(main.prefix + "There's not an ongoing round!");
                    return true;
                }

                eventIsActive = false;
                if (objective != null){
                    objective.unregister();
                }
                timer.cancel();

                for(Player online : Bukkit.getOnlinePlayers()){
                    online.setScoreboard(manager.getNewScoreboard());
                }

                server.broadcastMessage(main.prefix + player.getPlayerListName() + " ended the current round!");
                break;

            case "help":
                // TODO: Create Help Command
                break;
            default:
                server.broadcastMessage(main.prefix + "subcommand " + args[0] + " is not a valid subcommand.");
                break;
        }
        return true;
    } // end main

    public static void alert(int time){
        // TODO: wrap title implementation into here.
    }
}
