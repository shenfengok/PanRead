var bdstoken= '336e45b3f37b61eaa4de44b16751164c';

var new_list_set = new Set();
var new_list = $('span.sharelist-item-title-name a').map(function(){
    return $(this).attr('title')}).get().sort().reverse();

var old_list = get_list();
for(var j = 0;j < new_list.length;j ++){

    //先去重
    if(new_list_set.has(new_list[j])){
        continue;
    }else{
        new_list_set.add(new_list[j]);
    }
    if(old_list[new_list[j]]){
        continue;
    }
    create_folder(new_list[j]);
}
console.log("done");
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



