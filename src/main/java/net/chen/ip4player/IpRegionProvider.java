package net.chen.ip4player;

import org.lionsoul.ip2region.xdb.Searcher;

import java.io.IOException;

public class IpRegionProvider {
    private final Searcher searcher;

    public IpRegionProvider(String xdbPath) throws IOException {
        byte[] cBuff = Searcher.loadContentFromFile(xdbPath);
        this.searcher = Searcher.newWithBuffer(cBuff);
    }

    public String search(String ip) {
        try {
            return searcher.search(ip);
        } catch (Exception e) {
            return "Unknown";
        }
    }

    // Raw format: 国家|区域|省份|城市|ISP  (index 0-4; "0" means not applicable)
    public static final int FIELD_COUNTRY  = 0;
    public static final int FIELD_PROVINCE = 2;
    public static final int FIELD_CITY     = 3;
    public static final int FIELD_ISP      = 4;

    /** Format raw ip2region result (国家|区域|省份|城市|ISP) into readable string. */
    public static String format(String region) {
        if (region == null || region.isBlank()) return "Unknown";
        String[] parts = region.split("\\|");
        StringBuilder sb = new StringBuilder();
        for (String part : parts) {
            if (!part.equals("0") && !part.isBlank()) {
                if (!sb.isEmpty()) sb.append(' ');
                sb.append(part);
            }
        }
        return sb.isEmpty() ? region : sb.toString();
    }

    /** Extract a single field from a raw ip2region string. Returns empty string for "0" or missing. */
    public static String field(String rawRegion, int index) {
        if (rawRegion == null || rawRegion.isBlank()) return "";
        String[] parts = rawRegion.split("\\|");
        if (index >= parts.length) return "";
        String v = parts[index];
        return v.equals("0") ? "" : v;
    }

    public void close() {
        try {
            searcher.close();
        } catch (IOException ignored) {}
    }
}
