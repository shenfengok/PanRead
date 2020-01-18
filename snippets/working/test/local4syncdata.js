


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

window.big_data = {};
	window.stack =[];
	window.start = get_current_parent();
	window.starting = true;
	window.current_special = {};

	window.consuming = false;
	window.caijiing = true;
	window.caijiing_parent = get_current_parent();

	window.poping = false;
	window.poping_item = {};
	window.route = [];
	window.waiting_count= 0;

	window.syncing = false;

	window.syncing_folder = true;


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

				let parent_cur = get_current_parent();

				//先去重
				let aset_distinct = new Set();
			 

				for(let i = 0; i < aset.length; i ++){
					let ali = $(aset[i]);
					//先去重
					if(aset_distinct.has(ali.attr('title'))){
						continue;
					}else{
						aset_distinct.add(ali.attr('title'));
					}

					let to_caiji_item = {};
					to_caiji_item['title'] = ali.attr('title');
					to_caiji_item['parent'] = parent_cur;
					x[ali.attr('title')] = {};
					if(window.starting){
						to_caiji_item.is_speclial =true;
					}

					if(is_item_dir(ali)){
						window.stack.push(to_caiji_item);
					}else{
						//todo ajaxGetDlinkShare      getDlinkMbox          u.prototype.directDownload
						x[ali.attr('title')]['fid'] = ali.parent().parent().parent().parent().attr('js2mysql-fid');

						if(is_resource(ali.attr('title'))){
							sync_item(window.current_special.title,x[ali.attr('title')]['fid']);
						}
					}
				}
				window.starting = false;
				window.caijiing = false;

			}else if($('div.sharelist-container > div:contains(文件列表为空)').length > 0){
				window.caijiing = false;
			}
			window.consuming = false;
			return;//处理完就返回
		}else{

			//---------消费分割线--------------------------
			if(window.stack.length <=0 && !window.poping ){
				console.log(window.big_data);
				window.consuming = false;
				window.clearInterval(window.producer_consumer);
				return;//处理完就返回
			}
			
			

			if(!window.poping){
				window.poping_item =window.stack.pop();//正在访问的节点
				if(window.poping_item.is_speclial){

					Object.assign(window.current_special,window.poping_item);
					console.log("current special " + window.current_special.title)

				}
				console.log("pop out" + window.poping_item.title);
				window.poping = true;
			}
			

			//需要返回
			if(window.poping_item.title === 'goback'){
				$("ul.sharelist-history > li:nth-child(2) > a[title='"+window.poping_item.parent+"']").fclick();
				
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
	
console.log("done");

function sync_item(folder,fid) {
	    $.ajax({
        type :  "POST",
        contentType: "application/json; charset=UTF-8",
        url : "https://pan.baidu.com/mbox/msg/transfer?bdstoken=6d30583a50d01666122ed84721517d77&channel=chunlei&web=1&app_id=250528&logid=MTU3NzM2NDYwMDg4ODAuOTYyMzEwMDE4MjA2ODI5Mg==&clienttype=0",
        data : {
            from_uk: "228435709",
            msg_id: "471981340980807638",
            path: "/apps/Cloud Sync/zhuanlan-all/" + folder,
            ondup: "overwrite",
            async: "1",
            type: "2",
            gid: "658103785633267975",
            fs_ids: "["+fid+"]"
        },
        success : function(rs) {
            result = rs;
            console.log(folder +"-"+ fid)
            console.log(rs);
        },
        error : function(e){

            console.log(e.status);
            console.log(e.responseText);
        }
    });
}

function is_resource(name){
	if(name.endsWith(".m4a") || name.endsWith(".mp3") || name.endsWith(".html")){
		return true;
	}
	return false;
}


function create_folder(name){
    var url_create ='https://pan.baidu.com/api/create?a=commit&channel=chunlei&web=1&app_id=250528&bdstoken='+bdstoken+'&clienttype=0';
    var create_data = {
        path: "/apps/Cloud Sync/zhuanlan-all/" + name,
        isdir: 1,
        block_list: [],
        ondup:"overwrite"
    }
    http_call(url_create,create_data,false);
}

function get_list() {
    var list_data = {};
    var url_list = "https://pan.baidu.com/api/list?order=time&desc=1&showempty=0&web=1&page=1&num=100&dir=%2Fapps%2FCloud+Sync%2Fzhuanlan-all&t=0.571569333030987&channel=chunlei&web=1&app_id=250528&bdstoken="+bdstoken+"&clienttype=0";
    var result = http_call(url_list,list_data,true);
    var result_obj = {};
    for(var i = 0;i < result.list.length;i ++){
        result_obj[result.list[i].server_filename] = true;
    }
    return result_obj;
}

function http_call(url,datas,isget){
    var result = {};
    $.ajax({
        type : isget ? "GET" : "POST",
        contentType: "application/json;charset=UTF-8",
        url : url,
        data : datas,
        async: false,
        success : function(rs) {
            result = rs;
        },
        error : function(e){
            console.log(e.status);
            console.log(e.responseText);
        }
    });

    return result;
}



function get_current_parent(){
	return $('ul.sharelist-history > li:nth-child(2) > span').last().attr('title');
}

function is_item_dir(item){
	return item.attr('js2mysql-dir')=="1"
}