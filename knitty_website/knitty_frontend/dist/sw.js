
const CACHE_NAME = "knitty-pwa-34144051e3b7";
const OFFLINE_PAGE = "/offline.html";
const PRECACHE_URLS = ["/","/index.html","/offline.html","/offline-cormorant.woff2","/manifest.json","/knitty-icon-192.png","/knitty-icon-512.png","/assets/cormorant-garamond-cyrillic-400-normal-CVFrM67f.woff","/assets/cormorant-garamond-cyrillic-400-normal-DD2KOZkl.woff2","/assets/cormorant-garamond-cyrillic-600-normal-C2atO-64.woff2","/assets/cormorant-garamond-cyrillic-600-normal-FNmA3REe.woff","/assets/cormorant-garamond-cyrillic-ext-400-normal-DLdKLAvx.woff","/assets/cormorant-garamond-cyrillic-ext-400-normal-W3Dto7M0.woff2","/assets/cormorant-garamond-cyrillic-ext-600-normal-BMhMHfrj.woff2","/assets/cormorant-garamond-cyrillic-ext-600-normal-CsIYWmWK.woff","/assets/cormorant-garamond-latin-400-normal-B-1hWBU7.woff2","/assets/cormorant-garamond-latin-400-normal-B7YtguxJ.woff","/assets/cormorant-garamond-latin-600-normal-2CBVLo0M.woff","/assets/cormorant-garamond-latin-600-normal-Co1r35X9.woff2","/assets/cormorant-garamond-latin-ext-400-normal-Drx2k2n9.woff2","/assets/cormorant-garamond-latin-ext-400-normal-uvC0WHQr.woff","/assets/cormorant-garamond-latin-ext-600-normal-DaBc-lu7.woff2","/assets/cormorant-garamond-latin-ext-600-normal-Dk3-quAP.woff","/assets/cormorant-garamond-vietnamese-400-normal-4uxlocMh.woff","/assets/cormorant-garamond-vietnamese-400-normal-6K-YXo6g.woff2","/assets/cormorant-garamond-vietnamese-600-normal-BoXdMtcr.woff2","/assets/cormorant-garamond-vietnamese-600-normal-C4HEjEaf.woff","/assets/index-BGiJ-87v.js","/assets/index-C91amd1Q.js","/assets/index-CJ2DX-u5.css","/assets/stripe-CORc2SYw.js"];

self.addEventListener("install", (event) => {
  event.waitUntil((async () => {
    const cache = await caches.open(CACHE_NAME);

    await cache.addAll(["/", "/index.html", OFFLINE_PAGE, "/offline-cormorant.woff2"]);

    const optional = PRECACHE_URLS.filter((url) => !["/", "/index.html", OFFLINE_PAGE, "/offline-cormorant.woff2"].includes(url));
    await Promise.allSettled(optional.map((url) => cache.add(url)));
    await self.skipWaiting();
  })());
});

self.addEventListener("activate", (event) => {
  event.waitUntil((async () => {
    const names = await caches.keys();
    await Promise.all(names.filter((name) => name.startsWith("knitty-pwa-") && name !== CACHE_NAME).map((name) => caches.delete(name)));
    await self.clients.claim();
  })());
});

self.addEventListener("fetch", (event) => {
  const request = event.request;
  if (request.method !== "GET") return;
  const url = new URL(request.url);
  if (url.origin !== self.location.origin) return;

  if (url.pathname.startsWith("/api/") || url.pathname.startsWith("/media/")) return;

  if (request.mode === "navigate") {
    event.respondWith((async () => {
      try {
        const response = await fetch(request);
        if (response.ok) return response;
        return response;
      } catch {
        const cache = await caches.open(CACHE_NAME);
        if (url.pathname === "/" || url.pathname === "/index.html") {
          const homepage = await cache.match("/");
          if (homepage) return homepage;
        }
        return (await cache.match(OFFLINE_PAGE)) || new Response("Offline", { status: 503, headers: { "Content-Type": "text/plain; charset=utf-8" } });
      }
    })());
    return;
  }


  if (!url.pathname.startsWith("/assets/") && !PRECACHE_URLS.includes(url.pathname)) return;
  event.respondWith((async () => {
    const cache = await caches.open(CACHE_NAME);
    const cached = await cache.match(request);
    if (cached) return cached;
    try {
      const response = await fetch(request);
      if (response.ok && response.type === "basic") {
        await cache.put(request, response.clone());
      }
      return response;
    } catch {

      return new Response("Resource unavailable offline", { status: 503, headers: { "Content-Type": "text/plain; charset=utf-8" } });
    }
  })());
});
