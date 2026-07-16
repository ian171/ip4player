package net.chen.ip4player.api;

import net.chen.ip4player.Ip4player;
import net.chen.ip4player.IpRegionProvider;
import org.bukkit.entity.Player;

public final class PlayerIpApi {
    private PlayerIpApi(){

    }
    public String getPlayerRegion(Player player){
        var ipProvider = Ip4player.getPlugin(Ip4player.class).getRegionProvider();
        return ipProvider.search(player.getServer().getIp());
    }
}
