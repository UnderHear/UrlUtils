void main() {
    // ====================
    // isUrl examples.
    // ====================

    // Normal domains.
    IO.println(UrlUtils.isUrl("example.com"));                    // true
    IO.println(UrlUtils.isUrl("www.example.com"));                // true
    IO.println(UrlUtils.isUrl("sub.example.co.uk"));              // true
    IO.println(UrlUtils.isUrl("http://example.com"));             // true
    IO.println(UrlUtils.isUrl("https://www.example.com"));        // true
    IO.println(UrlUtils.isUrl("HTTPS://EXAMPLE.COM"));            // true
    IO.println(UrlUtils.isUrl("https://example.com/path/to/page"));// true
    IO.println(UrlUtils.isUrl("https://example.com/search?q=java"));// true
    IO.println(UrlUtils.isUrl("https://example.com/index.html#top"));// true
    IO.println(UrlUtils.isUrl("http://example.com:1"));           // true
    IO.println(UrlUtils.isUrl("http://example.com:8080"));        // true
    IO.println(UrlUtils.isUrl("http://example.com:65535"));       // true

    // IPv4 addresses.
    IO.println(UrlUtils.isUrl("115.190.254.60"));                 // true
    IO.println(UrlUtils.isUrl("http://115.190.254.60"));          // true
    IO.println(UrlUtils.isUrl("http://127.0.0.1:8080"));          // true

    // Empty or incomplete input.
    IO.println(UrlUtils.isUrl(null));                             // false
    IO.println(UrlUtils.isUrl(""));                               // false
    IO.println(UrlUtils.isUrl("   "));                            // false
    IO.println(UrlUtils.isUrl("example"));                        // false
    IO.println(UrlUtils.isUrl("localhost"));                      // false
    IO.println(UrlUtils.isUrl("a.b"));                            // false
    IO.println(UrlUtils.isUrl("com."));                           // false
    IO.println(UrlUtils.isUrl("example.com."));                   // false

    // Unsupported protocols or malformed URLs.
    IO.println(UrlUtils.isUrl("ftp://example.com"));              // false
    IO.println(UrlUtils.isUrl("mailto:test@example.com"));        // false
    IO.println(UrlUtils.isUrl("http://"));                        // false
    IO.println(UrlUtils.isUrl("https://"));                       // false
    IO.println(UrlUtils.isUrl("http://example.com/%zz"));         // false
    IO.println(UrlUtils.isUrl("abc def.com"));                    // false

    // Invalid domain labels.
    IO.println(UrlUtils.isUrl("example..com"));                   // false
    IO.println(UrlUtils.isUrl("http://example..com"));            // false
    IO.println(UrlUtils.isUrl("-example.com"));                   // false
    IO.println(UrlUtils.isUrl("example-.com"));                   // false
    IO.println(UrlUtils.isUrl("exa_mple.com"));                   // false
    IO.println(UrlUtils.isUrl("http://-example.com"));            // false
    IO.println(UrlUtils.isUrl("http://example-.com"));            // false

    // User info or confusing host formats.
    IO.println(UrlUtils.isUrl("foo@bar.com"));                    // false
    IO.println(UrlUtils.isUrl("http://user:pass@bar.com"));       // false
    IO.println(UrlUtils.isUrl("https://example.com@evil.com"));   // false

    // Invalid ports.
    IO.println(UrlUtils.isUrl("http://example.com:0"));           // false
    IO.println(UrlUtils.isUrl("http://example.com:65536"));       // false
    IO.println(UrlUtils.isUrl("http://example.com:99999"));       // false
    IO.println(UrlUtils.isUrl("https://example.com:abc"));        // false

    // Invalid IPv4 addresses.
    IO.println(UrlUtils.isUrl("1.2.3"));                          // false
    IO.println(UrlUtils.isUrl("1.2.3.4.5"));                      // false
    IO.println(UrlUtils.isUrl("256.256.256.256"));                // false
    IO.println(UrlUtils.isUrl("999.999.999.999"));                // false

    // ===============================
    // smartCompleteUrl examples.
    // ===============================

    // Already has a protocol.
    IO.println(UrlUtils.smartCompleteUrl("https://example.com"));                 // https://example.com
    IO.println(UrlUtils.smartCompleteUrl("http://example.com"));                  // http://example.com
    IO.println(UrlUtils.smartCompleteUrl("HTTPS://EXAMPLE.COM"));                 // HTTPS://EXAMPLE.COM
    IO.println(UrlUtils.smartCompleteUrl("https://example.com/path/to/page"));    // https://example.com/path/to/page
    IO.println(UrlUtils.smartCompleteUrl("https://example.com/search?q=java"));   // https://example.com/search?q=java
    IO.println(UrlUtils.smartCompleteUrl("https://example.com/index.html#top"));  // https://example.com/index.html#top

    // No protocol, HTTPS is reachable.
    IO.println(UrlUtils.smartCompleteUrl("example.com"));                         // https://example.com
    IO.println(UrlUtils.smartCompleteUrl("www.example.com"));                     // https://www.example.com
    IO.println(UrlUtils.smartCompleteUrl("example.com/path/to/page"));            // https://example.com/path/to/page
    IO.println(UrlUtils.smartCompleteUrl("example.com/search?q=java"));           // https://example.com/search?q=java
    IO.println(UrlUtils.smartCompleteUrl("example.com/index.html#top"));          // https://example.com/index.html#top

    // No protocol, HTTPS is not reachable.
    IO.println(UrlUtils.smartCompleteUrl("example.invalid"));                     // http://example.invalid
}
