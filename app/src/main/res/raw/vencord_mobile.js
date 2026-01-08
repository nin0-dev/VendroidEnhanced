!(() => {
    const { findLazy, Common, onceReady } = Vencord.Webpack;
    const ModalEscapeHandler = findLazy(m => m.binds?.length === 1 && m.binds[0] === "esc");

    let isSidebarOpen = false;
    onceReady.then(() => {
        Common.FluxDispatcher.subscribe("MOBILE_WEB_SIDEBAR_OPEN", () => {
            isSidebarOpen = true;
        });
        Common.FluxDispatcher.subscribe("MOBILE_WEB_SIDEBAR_CLOSE", () => {
            isSidebarOpen = false;
        });
    });

    window.VencordMobile = {
        // returns true if an action was taken, false if the java side should handle the back press
        onBackPress() {
            // false means modal closed (no further action rewuired?)
            if (ModalEscapeHandler.action() === false) return true;

            if (!isSidebarOpen) {
                Common.FluxDispatcher.dispatch({ type: "MOBILE_WEB_SIDEBAR_OPEN" });
                return true;
            }

            return false;
        }
    };


    document.addEventListener("DOMContentLoaded", () => {
            document.documentElement.appendChild(
                Object.assign(document.createElement("link"), {
                rel: "stylesheet",
                type: "text/css",
                href: Vencord.Api.isEquicord ? "https://github.com/VendroidEnhanced/plugin/releases/download/equicord-new/browser.css" : "https://github.com/VendroidEnhanced/plugin/releases/download/vencord-new/browser.css"
            }));
            document.documentElement.appendChild(
                Object.assign(document.createElement("link"), {
                rel: "stylesheet",
                type: "text/css",
                href: "https://raw.githubusercontent.com/VendroidEnhanced/random-files/refs/heads/main/moreFixes.css"
            }));
    }, { once: true });

})();
