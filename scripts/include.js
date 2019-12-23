
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
				console.log('not loading ready')
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
				ti.attr('set-step',"1");
			}
		}
		return false;
	})

}

function loop_item_listen_event(){
	do_job_steps(function () {
		$('span.sharelist-item-title-name a').unbind('click').bind('click',function () {
			var item = $(this);
			save_viewed_status(item)
			download_html(item)
			//只保存html
			if(!is_item_dir(item) && (is_html(item) || is_mp4(item))){
				save_last_viewed_item(item)
			}

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
	return item.attr('data-dir')=="1"
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
			// window.caijing = 1;
			
			window.big_data = {};
			window.stack =[];
			window.start = get_current_parent();

			window.consuming = false;
			window.caijiing = true;
			window.caijiing_parent = get_current_parent();

			window.poping = false;
			window.poping_item = {};
			window.route = [];
			window.waiting_count= 0;


			console.log("caijiing...");

			window.producer_consumer = window.setInterval(function(){
				if(window.consuming){
					return;
				}
				window.consuming = true;

				//平面采集---状态转换成普通
				if(window.caijiing){

					//fix 修复无法采集非当前parent
					if(get_current_parent() !== window.caijiing_parent){
						// $("div.sharelist-item-name > div.sharelist-item-title > span > a:contains('"+ window.caijiing_parent+"')").click();
						// console.log("fix"+ window.caijiing_parent);
						console.log("waiting " + window.caijiing_parent);
						consuming = false;
						return;
					}
					console.log ("采集"+ window.caijiing_parent + "...");
					// window.big_data[window.stack_parent] =[];
					let caiji = '.sharelist-container ul li';
					if($(caiji).length >0){
						let aset = $(caiji + ' span.sharelist-item-title-name a');
						//找到当前对象
						let x = big_data;
						for(let j = 0;j < window.route.length; j++){
							x = x[window.route[j]];
						}
						for(let i = 0; i < aset.length; i ++){
							let ali = $(aset[i]);

							let to_caiji_item = {};
							to_caiji_item['title'] = ali.attr('title');
							to_caiji_item['parent'] = get_current_parent();
							x[ali.attr('title')] = {};
							if(is_item_dir(ali)){
								window.stack.push(to_caiji_item);
							}else{
								x[ali.attr('title')]['audio'] ='mp3';
							}
						}

						window.caijiing = false;

					}else if($('div.sharelist-container > div:contains(文件列表为空)').length > 0){
						window.caijiing = false;
					}
					window.consuming = false;
					return;//处理完就返回
				}else{
					// if(window.poping){
					// 	consuming = false;
					// 	return;
					// }
					
					//---------消费分割线--------------------------
					if(window.stack.length <=0 && !window.poping ){
						console.log(window.big_data);
						window.consuming = false;
						window.clearInterval(window.producer_consumer);
						return;//处理完就返回
					}
					
					

					if(!window.poping){
						window.poping_item =window.stack.pop();//正在访问的节点

						console.log("pop out" + window.poping_item.title);
						window.poping = true;
					}
					

					//需要返回
					if(window.poping_item.title === 'goback'){
						$("ul.sharelist-history > li:nth-child(2) > a[title='"+window.poping_item.parent+"']").fclick();
						// $("div.sharelist-item-title > span > a")
						// $("ul.sharelist-history > li:nth-child(2) > a:contains("+window.poping_item.parent+")").fclick();
						window.route.pop();
						window.poping = false;
						window.consuming = false;
						return;//处理完就返回
					}

					
					//深入采集----转换到caijiing状态

					let folder = $('a:contains('+window.poping_item.title+')');
					if(folder.length <=0){
						$("ul.sharelist-history > li:nth-child(2) > a[title='"+window.poping_item.parent+"']").fclick();
							if($("ul.sharelist-history > li:nth-child(2) > a[title='"+window.poping_item.parent+"']").length > 0){
							$("ul.sharelist-history > li:nth-child(2) > a[title='"+window.poping_item.parent+"']").fclick();
						}else{
							$("div.sharelist-item-title > span > a:contains('" +window.poping_item.parent+"')" ).fclick();
						}
						
						window.consuming = false;
						return;
					}
					 
			        let item = $(folder[0]);
			        if(is_item_dir(item)){
			        	let to_caiji_item1 = {};
			        	to_caiji_item1['title'] = 'goback';
						to_caiji_item1['parent'] = get_current_parent();
						window.stack.push(to_caiji_item1);
						console.log("click " + item.attr('title'));
			        	item.fclick();
			        	window.route.push(item.attr('title'));
			        	window.caijiing_parent = item.attr('title');
			        	window.caijiing = true;
			        	window.poping = false;
			        }else{
			        	window.waiting_count ++ ;
			        	console.log("waiting "+ window.poping_item.title + "...");

			        	if(window.waiting_count > 10){
			        		// try to fix 

			        	}
			        }
			        window.consuming = false;

				}

				
			},1000 );
			
		}
	}
);

function get_current_parent(){
	return $('ul.sharelist-history > li:nth-child(2) > span').last().attr('title');
}

// function caiji_obj(){
// 	let obj = windwo.big_data；
// 	let sele = '.sharelist-container ul li';
// 	let caiji_list = $(sele);
// 	for(let i =0;i < caiji_list.length; i ++){
// 		let alink = $(caiji_list[i]).find('span.sharelist-item-title-name a');
// 		if(undefined !== alink.attr('title')){
// 			obj[alink.attr('title')] = [];
// 			window.stack.push(alink.attr('title'));
// 		}
// 	}
// }


// function caiji_obj1(obj){
// 	let sele = '.sharelist-container ul li';
// 	let caiji_list =  $(sele);
// 	for(let i =0;i < caiji_list.length; i ++){
// 		caiji_list = $(sele);
// 		let alink = $(caiji_list[i]).find('span.sharelist-item-title-name a');
// 		if(undefined !== alink.attr('title')){
// 			obj[alink.attr('title')] = {};

// 			if(is_item_dir(alink)){
// 				 inner_obj(obj[alink.attr('title')]);
// 			}else{//数据节点处理
// 				obj[alink.attr('title')]['audio'] ='xx.mp3'
// 			}
// 			goback();
// 		}
// 	}
// }



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

