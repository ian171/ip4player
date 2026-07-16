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

---

## 中文介绍

一款适用于 Paper 服务端的轻量级插件，可在玩家加入时自动记录其 IP 地址，并通过 [ip2region](https://github.com/lionsoul2014/ip2region) 数据库解析归属地（国家、省份、城市、运营商）。

### 功能介绍

- **自动下载数据库**：首次启动时自动从远程拉取 ip2region XDB 数据库（约 10 MB），无需手动配置。
- **归属地解析**：玩家加入时实时查询 IP 归属地，支持解析国家、省份、城市及运营商信息。
- **加入广播**：可向拥有权限的管理员广播玩家加入时的归属地信息，格式可在配置文件中自定义。
- **指令查询**：通过 `/ip` 指令查询在线玩家的 IP 及归属地。
- **PlaceholderAPI 支持**：提供多个变量，可在记分板、Tab 列表等位置展示玩家归属地。
- **Folia 兼容**：支持 Folia 服务端。

### 指令

| 指令 | 说明 | 权限节点 |
|---|---|---|
| `/ip` | 查看自己的 IP 及归属地 | `ip4player.ip.self` |
| `/ip <玩家名>` | 查看其他玩家的 IP 及归属地 | `ip4player.ip.others` |
| `/ip reload` | 重载配置文件 | `ip4player.reload` |

### 权限节点

| 权限节点 | 默认 | 说明 |
|---|---|---|
| `ip4player.ip.self` | 所有人 | 查看自己的 IP 及归属地 |
| `ip4player.ip.others` | OP | 查看其他玩家的 IP 及归属地 |
| `ip4player.see-joins` | OP | 接收玩家加入时的归属地广播 |
| `ip4player.reload` | OP | 重载插件配置 |

### 配置文件

```yaml
# 是否向拥有 ip4player.see-joins 权限的玩家广播加入信息
announce-join: true
announce-format: "&8[&bip4player&8] &f{player} &7加入自 &f{region}"

# 是否在控制台记录玩家加入日志
log-joins: true
```

消息格式支持以下占位符：`{player}`（玩家名）、`{ip}`（IP 地址）、`{region}`（完整归属地）。

### PlaceholderAPI 变量

| 变量 | 示例值 | 说明 |
|---|---|---|
| `%ip4player_ip%` | `1.2.3.4` | 玩家 IP 地址 |
| `%ip4player_country%` | `中国` | 国家 |
| `%ip4player_province%` | `广东省` | 省份（若有） |
| `%ip4player_city%` | `深圳市` | 城市（若有） |
| `%ip4player_isp%` | `电信` | 运营商 |
| `%ip4player_region%` | `中国 广东省 深圳市 电信` | 完整归属地字符串 |
