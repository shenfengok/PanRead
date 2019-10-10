
fireEvent= function  (element, event) {
	if (document.createEventObject) {
		var evt = document.createEventObject();
		return element.fireEvent("on" + event, evt);
	} else {
		var evt = document.createEvent("HTMLEvents");
		evt.initEvent(event, true, true);
		return !element.dispatchEvent(evt);
	}
};


jQuery.fn.fclick = function () {
	$(this).each( function() {
		fireEvent(this, "click");
	});
};


state = -2;
last = ''
lastDownloadClick = new Date().getTime()
route = {}
route['zhuanlan0'] =['极客','geek完结','专栏-完结']
route['shipin0'] =['极客','geek完结','视频-完结']
route['zhuanlan1'] =['geek_all『极客』','geek_all『极客』','geek更新','专栏-更新']
route['shipin1'] =['geek_all『极客』','geek_all『极客』','geek更新','视频-更新']
if(localStorage['type'] == ''){
	localStorage['type'] = 'zhuanlan1'
}

currentRount = route[localStorage['type']]
function doCheckHasContent(){
	console.log('coming' +state)
	if(window.location !='https://pan.baidu.com/mbox/homepage#share/type=session' || $('li.session-list-item').length <=1){
		console.log('not loading ready')
		return;
	}
	//未打开群组
	if($('.empty-start').length >0 && state == -2){
		$('.user-name:contains(极客30)').parent().click();
		state = -1;
		console.log('open jike 30')
	}
	if($('.file-factory').length > 0 && state == -1){

		$('.file-factory').click();
		state = 0;
		console.log('open file factory' + state)
	}

	walkRoute();


	if(state > 0  ){

		$('span.sharelist-item-title-name a').unbind('click').bind('click',bindEventAndShowStatus)

		for (var i =0;i <$('span.sharelist-item-title-name').length;i ++ ){
			var ti = $($('span.sharelist-item-title-name')[i]);
			var title = ti.find('a').first().attr('title');
			if(ti.attr('set-step') !="1"){
				if(localStorage['his'+title]){
					ti.append('<span>(' + localStorage['his'+title] + ')</span>')
				}
				else {
					ti.append('<span>(未观看)</span>')
					localStorage['his'+title] = '未观看'
				}
				ti.attr('set-step',"1");
			}
		}
	}


}

function bindEventAndShowStatus() {

		var title = $(this).attr('title');

		console.log(title)

		localStorage['his'+last] = title
		if(!isDir(title)){
			if(last != ""){
				addLog(last)
			}

			localStorage['his'+title] = '已观看';
			var el = $(this).siblings('span')
			if(el != undefined && el.html()!= undefined ){
				el.html(el.html().replace(/未观看/ig, '已观看'));
			}

			if(new Date().getTime() -lastDownloadClick > 3000){
				console.log('click down')
				lastDownloadClick =new Date().getTime()
				$(this).parent().parent().siblings(".sharelist-item-funcs").find("a:contains(下载)").fclick();
				$(this).attr("down","1")
			}
		}

		if(isDir(title)){
			last = title
		}



}

function isDir(title){
	if(title.search(".pdf") != -1 || title.search(".mp3") != -1
	|| title.search(".mp4") != -1|| title.search(".html") != -1
	|| title.search(".m4a") != -1){
		return false;
	}
	return true;
}



function addLog(keyword){
	var logs ={}
	if (localStorage['logs']  === undefined || localStorage['logs'] =="" ) {

	}else{
		logs = JSON.parse(localStorage['logs'])
	}
	logs[keyword] = true
	localStorage['logs'] = JSON.stringify(logs)
	refreshTable()
}
// t1 = window.setInterval(doCheckHasContent,1000);
$.get(chrome.extension.getURL('/html/flot.html'), function(data) {
	$(data).appendTo('body');
	// Or if you're using jQuery 1.8+:
	// $($.parseHTML(data)).appendTo('body');

	refreshTable();
});

function refreshTable(){
	$("#logsTable").empty();

	if (localStorage['logs']  !== undefined ) {
		var logs = JSON.parse(localStorage['logs']);
		for(let k of Object.keys(logs) ){
			$("#logsTable").append("<tr><td>"+k+"</td><td>" + localStorage['his'+k] +"</td></tr>")
		}
	}

}

function walkRoute(){
	if(state >= 0 && state < currentRount.length &&$('.sharelist-item-title-name').find('a:contains('+currentRount[state]+')').length >0){
		$('.sharelist-item-title-name').find('a:contains('+currentRount[state]+')').fclick();
		state ++;
	}
}


window.setInterval(doCheckHasContent,1000);



