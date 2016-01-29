var SCRIPT_NAME = 'scripts/parser.js',
    HTML_PAGE = 'gallery.html',
    activeUrl = "";

chrome.contextMenus.create({
    'title': 'Show images from this page',
    'contexts': ['link'],
    'onclick': onLinkClick
});

function onLinkClick(info, tab) {
    if (info.linkUrl.indexOf("englishrussia") >= 0) {
        activeUrl = info.linkUrl;
        chrome.tabs.create({
            url: HTML_PAGE
        }, onTabCreated);
    } else {
        alert("This extension works only on englishrussia.com page");
    }
}

function onTabCreated(tab) {
    chrome.tabs.executeScript(tab.id, {
        file: SCRIPT_NAME
    }, function() {
        chrome.tabs.sendMessage(tab.id, activeUrl);
    });
}