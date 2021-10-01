package de.jupiterpi.mc.edcaria.warp;

import jupiterpi.tools.util.AppendingList;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class WarpCommand implements CommandExecutor {
    private WarpPositionsService service = new WarpPositionsService();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command!");
            return true;
        }
        if (args.length == 0) return true;

        Player player = (Player) sender;

        String method = args[0];
        switch (method) {
            case "to":
                Location warpPosition = service.getPosition(args[1]);
                if (warpPosition != null) {
                    player.teleport(warpPosition);
                } else {
                    sender.sendMessage("That warp position doesn't exist!");
                    return true;
                }
                break;
            case "set":
                service.setPosition(args[1], player.getLocation());
                break;
            case "list":
                AppendingList positions = new AppendingList();
                positions.addAll(service.getWarpPositions());
                if (positions.size() > 0) {
                    String positionsStr = "- " + positions.render("\n- ");
                    sender.sendMessage("The following warp positions exist: \n" + positionsStr);
                } else {
                    sender.sendMessage("Not a single warp position exists. ");
                }
                break;
        }

        return true;
    }
}
