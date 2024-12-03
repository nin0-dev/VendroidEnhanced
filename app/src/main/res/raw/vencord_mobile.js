(() => {
    const { findLazy, Common, onceReady } = Vencord.Webpack;

    // Lazy load the ModalEscapeHandler that binds the "esc" key to close modals
    const ModalEscapeHandler = findLazy(m => m.binds?.length === 1 && m.binds[0] === "esc");

    let isSidebarOpen = false;

    // Subscribe to the FluxDispatcher for sidebar open/close events
    onceReady.then(() => {
        Common.FluxDispatcher.subscribe("MOBILE_WEB_SIDEBAR_OPEN", () => {
            isSidebarOpen = true;
        });
        Common.FluxDispatcher.subscribe("MOBILE_WEB_SIDEBAR_CLOSE", () => {
            isSidebarOpen = false;
        });
    });

    // Window object to handle back press logic
    window.VencordMobile = {
        // Returns true if an action was taken, false if Java side should handle the back press
        onBackPress() {
            // If the modal close action returns false, no further action is needed
            if (ModalEscapeHandler.action() === false) return true;

            // Close the quick CSS window if open
            const quickCssWin = window.__VENCORD_MONACO_WIN__?.deref();
            if (quickCssWin && !quickCssWin.closed) {
                quickCssWin.close();
                delete window.__VENCORD_MONACO_WIN__;
                return true;
            }

            // If the sidebar is not open, open it
            if (!isSidebarOpen) {
                Common.FluxDispatcher.dispatch({ type: "MOBILE_WEB_SIDEBAR_OPEN" });
                return true;
            }

            // Return false if sidebar is open, allowing Java to handle it
            return false;
        }
    };

    // Append necessary stylesheets when DOM content is loaded
    document.addEventListener("DOMContentLoaded", () => {
        document.documentElement.appendChild(
            Object.assign(document.createElement("link"), {
                rel: "stylesheet",
                type: "text/css",
                href: "https://github.com/YashSoPro/pluginplus/releases/download/1.0.0/browser.css"
            })
        );
        document.documentElement.appendChild(
            Object.assign(document.createElement("link"), {
                rel: "stylesheet",
                type: "text/css",
                href: "https://github.com/YashSoPro/pluginplus/releases/download/1.0.0/fixes.css"
            })
        );
    }, { once: true });

})();
