package net.chen.ip4player.command;

import net.chen.ip4player.Ip4player;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class IpCommand implements CommandExecutor, TabCompleter {
    private final Ip4player plugin;

    public IpCommand(Ip4player plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            if (!(sender instanceof Player player)) {
                sender.sendMessage("§cUsage: /ip <player>");
                return true;
            }
            sendInfo(sender, player.getUniqueId(), player.getName());
            return true;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("ip4player.reload")) {
                sender.sendMessage("§cYou don't have permission.");
                return true;
            }
            plugin.reloadConfig();
            sender.sendMessage("§aConfig reloaded.");
            return true;
        }

        if (!sender.hasPermission("ip4player.ip.others")) {
            sender.sendMessage("§cYou don't have permission.");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage("§cPlayer not found: §f" + args[0]);
            return true;
        }

        sendInfo(sender, target.getUniqueId(), target.getName());
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) {
            String input = args[0].toLowerCase();
            List<String> completions = Bukkit.getOnlinePlayers().stream()
                    .map(Player::getName)
                    .filter(n -> n.toLowerCase().startsWith(input))
                    .collect(java.util.stream.Collectors.toList());
            if ("reload".startsWith(input) && sender.hasPermission("ip4player.reload")) {
                completions.add("reload");
            }
            return completions;
        }
        return List.of();
    }

    private void sendInfo(CommandSender sender, UUID uuid, String name) {
        String ip = plugin.getPlayerDataManager().getIp(uuid);
        String region = plugin.getPlayerDataManager().getRegion(uuid);
        sender.sendMessage("§7Player: §f" + name);
        sender.sendMessage("§7IP: §f" + ip);
        sender.sendMessage("§7Region: §f" + region);
    }
}
