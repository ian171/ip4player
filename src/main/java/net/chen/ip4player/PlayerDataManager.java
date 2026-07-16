package net.chen.ip4player;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerDataManager {
    private final Map<UUID, String> ips = new ConcurrentHashMap<>();
    private final Map<UUID, String> regions = new ConcurrentHashMap<>();
    private final Map<UUID, String> rawRegions = new ConcurrentHashMap<>();

    public void set(UUID uuid, String ip, String rawRegion, String formattedRegion) {
        ips.put(uuid, ip);
        rawRegions.put(uuid, rawRegion);
        regions.put(uuid, formattedRegion);
    }

    public String getIp(UUID uuid) {
        return ips.getOrDefault(uuid, "Unknown");
    }

    public String getRegion(UUID uuid) {
        return regions.getOrDefault(uuid, "Unknown");
    }

    public String getRawRegion(UUID uuid) {
        return rawRegions.getOrDefault(uuid, "");
    }

    public void remove(UUID uuid) {
        ips.remove(uuid);
        regions.remove(uuid);
        rawRegions.remove(uuid);
    }
}
