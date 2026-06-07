# UrlUtils

![Java](https://img.shields.io/badge/Java-11%2B-blue)
![Dependencies](https://img.shields.io/badge/dependencies-none-brightgreen)

一个轻量级 Java URL 工具类，用于判断 URL、补全 HTTP/HTTPS 协议，并规范化协议与域名大小写。

## Features

- 判断字符串是否为 HTTP/HTTPS URL 或裸域名。
- 支持常见域名、子域名、路径、查询参数、锚点、端口和 IPv4 地址。
- 自动为裸域名补全协议，优先尝试 HTTPS，失败时回退 HTTP。
- 只规范化协议和域名大小写，不改路径、查询参数、锚点或已有百分号编码。
- 仅使用 Java 标准库，无第三方依赖。

## Quick Start

将 `UrlUtils.java` 放到项目源码目录后即可直接调用。

```java
String input = "HTTPS://GitHub.COM/user/About?id=123";

if (UrlUtils.isUrl(input)) {
    String completed = UrlUtils.smartCompleteUrl(input);
    String normalized = UrlUtils.normalizeUrl(completed);

    System.out.println(normalized);
    // https://github.com/user/About?id=123
}
```

## API

| Method | Description |
| --- | --- |
| `isUrl(String url)` | 判断字符串是否为合法的 HTTP/HTTPS URL 或裸域名。 |
| `smartCompleteUrl(String url)` | 对已通过 `isUrl` 的字符串补全协议；已有 HTTP/HTTPS 协议时原样返回。 |
| `normalizeUrl(String url)` | 对已通过 `isUrl` 且已完成协议补全的 URL 做规范化，只小写协议和域名。 |

## Examples

### Validate URL

```java
UrlUtils.isUrl("example.com");                     // true
UrlUtils.isUrl("https://example.com/search?q=java");// true
UrlUtils.isUrl("http://127.0.0.1:8080");           // true

UrlUtils.isUrl("example");                         // false
UrlUtils.isUrl("ftp://example.com");               // false
UrlUtils.isUrl("http://example.com:99999");        // false
```

### Complete Protocol

```java
UrlUtils.smartCompleteUrl("https://example.com");
// https://example.com

UrlUtils.smartCompleteUrl("http://example.com");
// http://example.com

UrlUtils.smartCompleteUrl("example.com");
// Usually https://example.com; falls back to http://example.com if HTTPS is unreachable.
```

`smartCompleteUrl` 会对补全后的 HTTPS 地址发起 `HEAD` 请求，连接超时和读取超时均为 3 秒。只要能拿到 HTTP 状态码，就认为 HTTPS 可连通。

### Normalize URL

```java
UrlUtils.normalizeUrl("HTTPS://GitHub.COM/user/About?id=123");
// https://github.com/user/About?id=123

UrlUtils.normalizeUrl("HTTPS://GitHub.COM/User/About");
// https://github.com/User/About

UrlUtils.normalizeUrl("HTTPS://GitHub.COM:080/user/About%2FName");
// https://github.com:080/user/About%2FName

UrlUtils.normalizeUrl("HTTP://115.190.254.60:8080/User/About");
// http://115.190.254.60:8080/User/About
```

`normalizeUrl` 的调用前提是：传入值已经通过 `isUrl`，并且已经由 `smartCompleteUrl` 补全协议。它不会重新做完整合法性校验。

## Run Examples

当前仓库提供了 `HowToUse.java` 作为示例入口：

> `UrlUtils.java` 本身仅依赖 Java 标准库；`HowToUse.java` 中的 `void main()` 是 JDK 25 的简化入口特性，本仓库使用 JDK 25 验证。若使用更低版本 JDK 运行示例，需要改成传统的 `public static void main(String[] args)`。

```powershell
javac -d out UrlUtils.java HowToUse.java
java --class-path out HowToUse
```

## Notes

- `isUrl` 只接受 HTTP/HTTPS URL 或裸域名，不支持 `ftp://`、`mailto:` 等协议。
- `isUrl` 会拒绝 user info，例如 `http://user:pass@example.com`。
- `smartCompleteUrl` 的裸域名补全结果依赖当前网络和目标站点 HTTPS 可达性。
- `normalizeUrl` 只处理协议和域名大小写，不会修改路径大小写、查询参数大小写、锚点或百分号编码。
