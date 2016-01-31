var SCRIPT_NAME = 'scripts/parser.js',
    HTML_PAGE = 'gallery.html',
    REGEX = "http:\/\/englishrussia\.com\/20";

chrome.contextMenus.onClicked.addListener(onLinkClick);
chrome.contextMenus.create({
    'title': 'Show images from this page',
    "documentUrlPatterns": ["http://englishrussia.com/", "http://englishrussia.com/page/*"],
    'contexts': ['link']
});

function onLinkClick(info, tab) {
    if (info.linkUrl.match(REGEX)) {
        createTab(info.linkUrl);
    } else {
        alert("This extension works only on englishrussia.com page");
    }
}

function createTab(url) {
    chrome.tabs.create({
        'url': chrome.extension.getURL("gallery.html"),
        'active': false
    }, function(tab) {
        var selfTabId = tab.id;
        chrome.tabs.onUpdated.addListener(function(tabId, changeInfo, tab) {
            if (changeInfo.status == "complete" && tabId == selfTabId) {
                // send the data to the page's script:
                var tabs = chrome.extension.getViews({
                    type: "tab"
                });
                for (var i = 0; i < tabs.length; i++) {
                    if (!tabs[i].wasUsed) {
                        tabs[i].wasUsed = true;
                        tabs[i].start(url);
						break;
                    }
                }
            }
        });
    });
}