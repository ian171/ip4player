package net.chen.ip4player;

import net.chen.ip4player.command.IpCommand;
import net.chen.ip4player.listener.PlayerJoinListener;
import net.chen.ip4player.papi.Ip4playerPlaceholder;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class Ip4player extends JavaPlugin {
    private static final String IP_DOWNLOAD_URL = "https://ip.adysec.com/ip2region/ip2region.xdb";

    private IpRegionProvider regionProvider;
    private PlayerDataManager playerDataManager;
    public Logger logger;

    @Override
    public void onEnable() {
        logger = getLogger();
        saveDefaultConfig();
        getDataFolder().mkdirs();

        playerDataManager = new PlayerDataManager();

        File xdbFile = new File(getDataFolder(), "ip2region.xdb");
        if (!xdbFile.exists()) {
            logger.info("Downloading ip2region database...");
            Thread.ofVirtual().start(() -> {
                try {
                    downloadDatabase(xdbFile);
                    initRegionProvider(xdbFile);
                } catch (Exception e) {
                    logger.log(Level.SEVERE, "Failed to download ip2region database", e);
                }
            });
        } else {
            initRegionProvider(xdbFile);
        }

        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);

        var ipCmd = getCommand("ip");
        if (ipCmd != null) {
            var handler = new IpCommand(this);
            ipCmd.setExecutor(handler);
            ipCmd.setTabCompleter(handler);
        }

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new Ip4playerPlaceholder(this).register();
            logger.info("PlaceholderAPI hooked successfully.");
        }
    }

    @Override
    public void onDisable() {
        if (regionProvider != null) {
            regionProvider.close();
        }
    }

    private void downloadDatabase(File target) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(IP_DOWNLOAD_URL))
                .build();
        HttpResponse<InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());
        try (InputStream in = response.body(); FileOutputStream out = new FileOutputStream(target)) {
            in.transferTo(out);
        }
        getLogger().info("ip2region database downloaded successfully.");
    }

    private void initRegionProvider(File xdbFile) {
        try {
            regionProvider = new IpRegionProvider(xdbFile.getAbsolutePath());
            logger.info("ip2region initialized successfully.");
        } catch (Exception e) {
            logger.severe("Failed to initialize ip2region\n"+e.getMessage());
        }
    }

    public IpRegionProvider getRegionProvider() {
        return regionProvider;
    }

    public PlayerDataManager getPlayerDataManager() {
        return playerDataManager;
    }
}
