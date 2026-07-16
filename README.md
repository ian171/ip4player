# ip4player

A Paper plugin that records player IP addresses and looks up their geographic region using the [ip2region](https://github.com/lionsoul2014/ip2region) database.

---

## Features

- Automatically downloads the ip2region XDB database (~10 MB) on first start
- Looks up country, province, city, and ISP for every player on join
- Announces join region info to staff (configurable)
- `/ip` command to query any online player's IP and region
- PlaceholderAPI support

## Requirements

- Paper 26.1.2+
- Java 25+
- PlaceholderAPI (optional, soft dependency)

## Installation

1. Drop the jar into your `plugins/` folder
2. Start the server — the XDB database will download automatically on first launch
3. Configure `plugins/ip4player/config.yml` as needed

## Commands

| Command | Description | Permission |
|---|---|---|
| `/ip` | Show your own IP and region | `ip4player.ip.self` |
| `/ip <player>` | Show another player's IP and region | `ip4player.ip.others` |
| `/ip reload` | Reload config | `ip4player.reload` |

## Permissions

| Permission | Default | Description |
|---|---|---|
| `ip4player.ip.self` | everyone | View your own IP and region |
| `ip4player.ip.others` | op | View other players' IPs |
| `ip4player.see-joins` | op | Receive join announcements with region info |
| `ip4player.reload` | op | Reload the config |

## Configuration

```yaml
# Announce player region info to players with ip4player.see-joins permission
announce-join: true
announce-format: "&8[&bip4player&8] &f{player} &7加入自 &f{region}"

# Log join info to console
log-joins: true
```

Available format placeholders: `{player}`, `{ip}`, `{region}`

## PlaceholderAPI

| Placeholder | Example | Description |
|---|---|---|
| `%ip4player_ip%` | `1.2.3.4` | Player's IP address |
| `%ip4player_country%` | `中国` | Country |
| `%ip4player_province%` | `广东省` | Province (if available) |
| `%ip4player_city%` | `深圳市` | City (if available) |
| `%ip4player_isp%` | `电信` | ISP |
| `%ip4player_region%` | `中国 广东省 深圳市 电信` | Formatted full region string |

## Building

```bash
./gradlew shadowJar
```

Output jar: `build/libs/`

## Author

Chen
