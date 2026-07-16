package net.chen.ip4player.listener;

import net.chen.ip4player.Ip4player;
import net.chen.ip4player.IpRegionProvider;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerJoinListener implements Listener {
    private final Ip4player plugin;

    public PlayerJoinListener(Ip4player plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        var player = event.getPlayer();
        String ip = player.getAddress() != null
                ? player.getAddress().getAddress().getHostAddress()
                : "Unknown";

        IpRegionProvider provider = plugin.getRegionProvider();
        String rawRegion = provider != null ? provider.search(ip) : "Loading...";
        String region = IpRegionProvider.format(rawRegion);

        plugin.getPlayerDataManager().set(player.getUniqueId(), ip, rawRegion, region);

        if (plugin.getConfig().getBoolean("log-joins", true)) {
            plugin.getLogger().info(player.getName() + " | IP: " + ip + " | Region: " + region);
        }

        if (plugin.getConfig().getBoolean("announce-join", true)) {
            String fmt = plugin.getConfig().getString(
                    "announce-format",
                    "&8[&bip4player&8] &f{player} &7joined from &f{region}");
            String msg = fmt
                    .replace("{player}", player.getName())
                    .replace("{ip}", ip)
                    .replace("{region}", region)
                    .replace("&", "§");
            plugin.getServer().getOnlinePlayers().stream()
                    .filter(p -> p.hasPermission("ip4player.see-joins"))
                    .forEach(p -> p.sendMessage(msg));
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        plugin.getPlayerDataManager().remove(event.getPlayer().getUniqueId());
    }
}
