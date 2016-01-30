var RESULTS_PAGE_ID = "results-page",
    PICS_CLASS = "post_pic",
    LINKS_CLASS = "page-link",
    BR = "<br />",
    GET = "GET",
    wasUsed = false,
	TITLE_DIV = "entry-title";

function start(message) {
    var index = message.indexOf("#more");
    if (index > 0) {
        message = message.substring(0, index);
    }
	
    loadPage(message, function() {
        if (this.readyState == 4) {
            if (this.status == 200 &&
                this.responseText != null) {
                parse(this.responseText, message);
            } else {
                alert("Something went wrong");
            }
        }
    });
}

function loadPage(url, callback) {
    var xhr = new XMLHttpRequest();
    xhr.open(GET, url, true);
    xhr.onreadystatechange = callback;
    xhr.send();
}

function parse(webPage, url) {
    var subPages = $(webPage).find('.' + LINKS_CLASS).children(),
        resultsPage = $("." + RESULTS_PAGE_ID),
		title = $(webPage).find('.' + TITLE_DIV).children("img").attr("alt");
	if (!title) {
		title = url;
	}
	
	$("#loading-text").text(title);

    for (var i = 1; i <= subPages.length + 1; i++) {
        var newUrl = url + i + "/"
        getImgs(newUrl);
    }
}

function getImgs(url) {
    loadPage(url, function() {
        if (this.readyState == 4) {
            if (this.status == 200 &&
                this.responseText != null) {
                addToResultsPage($(this.responseText).find('.' + PICS_CLASS));
            } else {
                console.log("Cannot get images from page");
            }
        }
    });
}

function addToResultsPage(imgs) {
    var resultsPage = $("#" + RESULTS_PAGE_ID)
    $.each(imgs, function(index, value) {
        resultsPage.append(value).append(BR);
    });
}