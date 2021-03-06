
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

jQuery.fn.fhover = function () {
	$(this).each( function() {
		fireEvent(this, "mouseover");
	});
};

fix_route = {}


fix_route['zhuanlan_wj'] =['00-资源文件','14-极客时间','01-专栏课','专栏-完结']
fix_route['shipin_wj'] =['00-资源文件','14-极客时间','02-视频课']
fix_route['zhuanlan_gx'] =['00-资源文件','14-极客时间','00-更新中的专栏','专栏-更新']
fix_route['shipin_gx'] =['00-资源文件','14-极客时间','02-视频课']

//do ...
go2filefactory()
do_last_routing()
loop_item_viewed_status()
loop_item_listen_event()
//do end...


lastDownloadClick = new Date().getTime()

// funcs
function go2filefactory () {
	do_job_steps(
		function () {

			if (window.location != 'https://pan.baidu.com/mbox/homepage#share/type=session' || $('li.session-list-item').length <= 1) {
				console.log('not loading ready');
				if(loc_has('pan.baidu.com')){
					window.location.href="https://pan.baidu.com/mbox/homepage#share/type=session";
				}
				return false;
			}
			return true;
		},
		function () {
			//未打开群组
			if ($('.empty-start').length > 0) {
				$('.user-name:contains(（禁言）大学堂15)').parent().click();

				console.log('open jike shijian')
				return true;
			}
			return false;
		},
		function () {
			if ($('.file-factory').length > 0) {

				$('.file-factory').click();
				return true;
			}
			return false;
		}
	)
}

function do_last_routing() {
	var last_route = get_last_route()
	walkRoute(last_route)
}

/**
 * 浏览历史栏包含str
 *
 * */
function history_contain(str){

	return exist('div.sharelist-header > ul.sharelist-history li a:contains('+str+')');
}

/** 判断元素存在
 *
 */
function exist(str){
	if($(str).length > 0){
		return $(str)[0];
	}
	return undefined;
}

function get_last_route() {
	if(localStorage['last_route'] === undefined || localStorage['last_route'] === null || localStorage['last_route'] ==''){
		localStorage['last_route'] = 'zhuanlan_wj'
	}
	return fix_route[localStorage['last_route']]
}
function walkRoute(full_route){
	function generate_route_step(currentRoute) {
		return function () {
			if($('.sharelist-item-title-name').find('a:contains('+currentRoute+')').length >0){
				$('.sharelist-item-title-name').find('a:contains('+currentRoute+')').fclick();
				return true;
			}
		}
	}
	var jobs = []
	for(var i=0;i<full_route.length;i++){
		jobs.push(generate_route_step(full_route[i]))
	}
	do_job(jobs)
}

/**
 * 更新阅读状态
 */
function loop_item_viewed_status(){
	do_job_steps(function () {
		for (var i =0;i <$('span.sharelist-item-title-name').length;i ++ ){
			var ti = $($('span.sharelist-item-title-name')[i]);
			var title = ti.find('a').first().attr('title');
			if(ti.attr('set-step') !="1"){
				if(localStorage['viewed_status'+title]){
					ti.append('<span>(' + localStorage['viewed_status'+title] + ')</span>')
				}
				else {
					ti.append('<span>(未观看)</span>')
					localStorage['viewed_status'+title] = '<span>未观看</span>'
				}

				ti.append('<button>采集</button>')
				ti.attr('set-step',"1");
			}
		}
		return false;
	})

}

function loc_has(str){
	return window.location.href.indexOf(str) >=0;
}

function loop_item_listen_event(){
	do_job_steps(function () {
		$('span.sharelist-item-title-name button').unbind('click').bind('click',function () {
			var item = $(this);
			let fid = item.parents('li').attr('data-fid');
			let list = fetch_share_list(fid)
			console.log(list)

		})
		return false;
	})

}

function save_viewed_status(item) {
	var title = item.attr('title');
	console.log(title)
	localStorage['viewed_status'+get_parent_dir()] = title
	if(!is_item_dir(item)){

		localStorage['viewed_status'+title] = '已观看';
		var el = item.siblings('span')
		if(el != undefined && el.html()!= undefined ){
			el.html(el.html().replace(/未观看/ig, '已观看'));
		}
	}


}

function download_html(item) {
	//同一个item，2秒内不能重复点击
	if(item.attr("down") =="1" && new Date().getTime() -lastDownloadClick < 2000 ){
		return
	}

	if( item.attr('title').search('.html') != -1 || item.attr('title').search('.m4a') != -1 || item.attr('title').search('.mp3') != -1){
		console.log('click down')
		lastDownloadClick =new Date().getTime()
		item.parent().parent().siblings(".sharelist-item-funcs").find("a:contains(下载)").fclick();
		item.attr("down","1")

		if(item.attr('title').search('.html') != -1){
			//如果是html,则点击m4a
			var title = item.attr('title').replace(/\.html/,'.m4a');
			window.setTimeout(function(){
				$('span.sharelist-item-title-name a:contains('+title+')').fclick();
			},2000)
		}
	}
}



function is_item_dir(item){
	return item.attr('js2mysql-dir')=="1"
}

// function isDir(title){
// 	if(title.search(".pdf") != -1 || title.search(".mp3") != -1
// 	|| title.search(".mp4") != -1|| title.search(".html") != -1
// 	|| title.search(".m4a") != -1){
// 		return false;
// 	}
// 	return true;
// }

function is_html(item){
	var title = item.attr('title')
	if(title.search(".html") != -1){
		return true;
	}
	return false;
}

function is_mp4(item){
	var title = item.attr('title')
	if(title.search(".mp4") != -1){
		return true;
	}
	return false;
}



function save_last_viewed_item(item){
	var last_viewed_item ={}
	last_viewed_item.title = item.attr('title')
	last_viewed_item.fix_route = localStorage['last_route']
	last_viewed_item.series = get_parent_dir();
	var title_list = get_title_list(item)
	var current = title_list.indexOf(item.attr('title'))
	if(current > 1){
		last_viewed_item.prev = title_list[current-1]
	}
	if(current + 1 < title_list.length){
		last_viewed_item.next = title_list[current+1]
	}

	chrome.runtime.sendMessage(last_viewed_item, function(response) {
		console.log(response.farewell);
	});
}

function get_title_list(item){
	var title = item.attr('title')
	var ext = getExt(title)
	var list = $('span.sharelist-item-title-name a').map(function(){
		return $(this).attr('title')}).get()
	var list1 = list.filter(word => getExt(word) == ext);
	console.log(list1)
	return Array.from(new Set(list1)).sort();;
}

function get_parent_dir() {
	return $('li[node-type=sharelist-history-list] span').last().attr('title')
}

function getExt(filename)
{
	var idx = filename.lastIndexOf('.');
	// handle cases like, .htaccess, filename
	return (idx < 1) ? "" : filename.substr(idx + 1);
}

function goto_item(item){
	var item_route = []
	if(localStorage['last_route']  != item.fix){
		//click 全部文件
		localStorage['last_route']  = item.fix
		$('.sharelist-history li a:contains(全部文件)').fclick()
		item_route= [...fix_route[item.fix]]
		item_route.push(item.series)
		item_route.push(item.current)
	} else if ($('.sharelist-item-title-name').find('a:contains('+item.series+')').length >0){
		item_route.push(item.series)
		item_route.push(item.current)
	} else {
		item_route.push(item.current)
	}

	walkRoute(item_route)
}


//events
chrome.runtime.onMessage.addListener(
	function(request, sender, sendResponse) {
		if(window.consuming){
			return;
		}
		console.log(request);
		if( request.goto != undefined && request.goto !='' ) {
			var route = request.goto;

			console.log(route.fix)
			goto_item(route)

		}else if( request.gotoNav != undefined && request.gotoNav !='' ) {
			var route = request.gotoNav;
			localStorage['last_route']  = route
			window.location.reload();
		}else if( request.caiji != undefined && request.caiji !='' ) {


		}
	}
);

function get_current_parent(){
	return $('ul.sharelist-history > li:nth-child(2) > span').last().attr('title');
}


function sleep(d){
	for(var t = Date.now();Date.now() - t <= d;);
}

function goback(){
	$('a:contains(返回上一级)').click();
}

//helper funcs
function do_job_steps(...steps) {
	var job = []
	for(var i=0;i<steps.length;i++){
		job.push(steps[i])
	}
	do_job(job)
}

function do_job(job) {
	var step_count = job.length;
	var current_step = 0;
	var t1 = window.setInterval( function () {
		if(job[current_step]()){
			current_step ++;
			step_count--;
		}
		if(step_count == 0){
			window.clearInterval(t1);
		}
	},2000);
}

