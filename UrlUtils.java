import java.net.URI;

public class UrlUtils {

    /**
     * 判断给定字符串是否为 HTTP/HTTPS URL 或裸域名。
     */
    public static boolean isUrl(String url) {
        if (url == null || url.isBlank()) {
            return false;
        }

        // 先按原字符串判断；如果没有协议前缀，再补 http:// 判断一次裸域名。
        String[] candidates = {url, "http://" + url};

        for (String candidate : candidates) {
            try {
                URI uri = new URI(candidate);

                String scheme = uri.getScheme();
                String host = uri.getHost();
                int port = uri.getPort();

                // 基础 URL 限制：只允许 HTTP/HTTPS，且拒绝 userInfo、异常端口和明显不规范的 host。
                if (scheme == null
                        || (!scheme.equalsIgnoreCase("http") && !scheme.equalsIgnoreCase("https"))
                        || host == null
                        || !host.contains(".")
                        || host.length() > 253
                        || host.endsWith(".")
                        || uri.getUserInfo() != null
                        || (port != -1 && (port < 1 || port > 65535))) {
                    continue;
                }

                // 按 label 继续校验，允许合法域名或合法 IPv4，避免 a.b、example.、-example.com 等误判。
                String[] labels = host.split("\\.", -1);
                String topLevelDomain = labels[labels.length - 1];
                boolean validDomain = topLevelDomain.matches("[A-Za-z]{2,}");
                boolean validIpv4 = labels.length == 4;

                for (String label : labels) {
                    if (label.isEmpty()
                            || label.length() > 63
                            || label.startsWith("-")
                            || label.endsWith("-")
                            || !label.matches("[A-Za-z0-9-]+")) {
                        validDomain = false;
                        validIpv4 = false;
                        break;
                    }

                    if (validIpv4) {
                        if (!label.matches("\\d{1,3}") || Integer.parseInt(label) > 255) {
                            validIpv4 = false;
                        }
                    }
                }

                if (validDomain || validIpv4) {
                    return true;
                }
            } catch (Exception e) {
                // 尝试下一个候选 URL。
            }
        }

        return false;
	}
}
