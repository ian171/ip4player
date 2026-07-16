package net.chen.ip4player.papi;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.chen.ip4player.Ip4player;
import net.chen.ip4player.IpRegionProvider;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public class Ip4playerPlaceholder extends PlaceholderExpansion {
    private final Ip4player plugin;

    public Ip4playerPlaceholder(Ip4player plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "ip4player";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Chen";
    }

    @Override
    public @NotNull String getVersion() {
        return plugin.getPluginMeta().getVersion();
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onRequest(OfflinePlayer player, @NotNull String params) {
        if (player == null) return "";
        var dm = plugin.getPlayerDataManager();
        var uuid = player.getUniqueId();
        return switch (params) {
            case "ip"       -> dm.getIp(uuid);
            case "region"   -> dm.getRegion(uuid);
            case "country"  -> IpRegionProvider.field(dm.getRawRegion(uuid), IpRegionProvider.FIELD_COUNTRY);
            case "province" -> IpRegionProvider.field(dm.getRawRegion(uuid), IpRegionProvider.FIELD_PROVINCE);
            case "city"     -> IpRegionProvider.field(dm.getRawRegion(uuid), IpRegionProvider.FIELD_CITY);
            case "isp"      -> IpRegionProvider.field(dm.getRawRegion(uuid), IpRegionProvider.FIELD_ISP);
            default -> null;
        };
    }
}
