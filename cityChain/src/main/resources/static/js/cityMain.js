// ------------------------------------- history.ftl
function historyLoadMore(page) {
	var obj = $.ajax({
		url : "/base/history?token=" + $.cookie("token")
				+ "&coin=0&type=0&plusMinus=0&page=" + page,
		async : false
	}).responseJSON.object;
	if (obj instanceof Array) {
		var objLen = obj.length;
		for (i = 0; i < objLen; i++) {
			$("#historyDiv").append("<tr data-v-88de3002=''><td data-v-88de3002='' class='nickname left'><img src='/imgs/" + obj[i].first + "' style='width: 30px;'></img>" + obj[i].second + "</td><td data-v-88de3002='' class='nickname center'>" + obj[i].third + "</td><td data-v-88de3002='' class='nickname center'>" + obj[i].fourd + "</td></tr>");
		}
		if (objLen == 0) {
			$("#moreDivSub").remove();
			$("#moreDiv").append("<div class='module__fetch__more'>没有更多了</div>");
		}
	}
}
// ------------------------------------- power.ftl
function powerLoadMore(page) {
	var obj = $.ajax({
		url : "/base/powerHistory?token=" + $.cookie("token")
				+ "&power=0&page=" + page,
		async : false
	}).responseJSON.object;
	if (obj instanceof Array) {
		var objLen = obj.length;
		for (i = 0; i < objLen; i++) {
			$("#historyDiv").append("<tr data-v-88de3002=''><td data-v-88de3002='' class='nickname left'><img src='/imgs/" + obj[i].first + "' style='width: 30px;'></img>" + obj[i].second + "</td><td data-v-88de3002='' class='nickname center'>" + obj[i].third + "</td><td data-v-88de3002='' class='nickname center'>" + obj[i].fourd + "</td></tr>");
		}
		if (objLen == 0) {
			$("#moreDivSub").remove();
			$("#moreDiv").append("<div class='module__fetch__more'>没有更多了</div>");
		}
	}
}

// 请求跳转url
function callUrl(url, title, depth) {
	var tokenParma = "token=" + $.cookie("token");
	if (url.indexOf("?") > -1) {
		url += "&" + tokenParma;
	} else {
		url += "?" + tokenParma;
	}
	var device = checkDevice();
	if (device == "android") {
		var urlAll = getRootPath() + "/" + url;
		if (depth == 2) {
			window.hello.showSecondActivity(urlAll, title);
		} else {
			window.hello.showActivity(urlAll, title);
		}
	} else if (device == "ios") {
		window.open(url, '_self');
	} else {
		window.open(url, '_self');
	}
}

/*
 * 检测设备操作系统
 */
function checkDevice() {
	var u = navigator.userAgent;
	if (u.indexOf('Android') > -1 || u.indexOf('Adr') > -1) { // android终端
		return "android";
	} else if (!!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/)) { // ios终端
		return "ios";
	} else {
		return "pc";
	}
}

/*
 * 获取网站根目录
 */
function getRootPath() {
	var strFullPath = window.document.location.href;
	var strPath = window.document.location.pathname;
	var pos = strFullPath.indexOf(strPath);
	var prePath = strFullPath.substring(0, pos);
	var postPath = strPath.substring(0, strPath.substr(1).indexOf('/') + 1);
	return (prePath + postPath);
}