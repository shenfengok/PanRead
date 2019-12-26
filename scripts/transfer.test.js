var bdstoken= '336e45b3f37b61eaa4de44b16751164c';

var big_list={};

var target_folder = "test";

var new_list = $('span.sharelist-item-title-name a').map(function(){
    return $(this).attr('title')}).get().sort().reverse();

// if(!big_list[target_folder] || !big_list[target_folder].isset){
//     big_list[target_folder] = get_list(target_folder);
// }


// for(var j = 0;j < new_list.length;j ++){

//     //先去重
//     if(new_list_set.has(new_list[j])){
//         continue;
//     }else{
//         new_list_set.add(new_list[j]);
//     }
//     if(big_list[target_folder][new_list[j]]){
//         continue;
//     }
//     if(is_resource(new_list[j])){

//     }
// }

sync_item(735623199325956,"test");

function sync_item(fid,folder){
    var fids = [];
    fids.push(fid);
    var sync_data ={
        from_uk: 228435709,
        msg_id: 471981340980807638,
        path: "/apps/Cloud Sync/zhuanlan-all/"+folder,
        ondup: "newcopy",
        async: 1,
        type: 2,
        gid: 658103785633267975,
        fs_ids: "[735623199325956]"
    };

    var sync_url = "https://pan.baidu.com/mbox/msg/transfer?bdstoken=336e45b3f37b61eaa4de44b16751164c&channel=chunlei&web=1&app_id=250528&logid=MTU3NzM1NzU5OTgxOTAuMjY5MDc3NTQ2NDgyMDU5NA==&clienttype=0";
    http_call(sync_url,sync_data,false);
}

function is_resource(name){
    if(name.endWith(".mp3") || name.endWith(".html") || name.endWith("m4a")){
        return true;
    }

    return false;
}
console.log("done");

function get_list(folder) {
    var list_data = {};

    var url_list = "https://pan.baidu.com/api/list?order=time&desc=1&showempty=0&web=1&page=1&num=100&dir=%2Fapps%2FCloud+Sync%2Fzhuanlan-all%2F" 
    + encodeURI(folder) + 
    "&t=0.571569333030987&channel=chunlei&web=1&app_id=250528&bdstoken="+bdstoken+"&clienttype=0";
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
        contentType: "application/x-www-form-urlencoded",
        url : url,
        data : datas,
        async: false,
        success : function(rs) {
            result = rs;
            console.log(rs);
        },
        error : function(e){
            console.log(e.status);
            console.log(e.responseText);
        }
    });

    return result;
}



