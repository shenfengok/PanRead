var token = yunData.MYBDSTOKEN;
function http_call(url,datas,isget) {
  const p = new Promise((resolve, reject) => {
    $.ajax(url, {
      type : isget ? "GET" : "POST",
      contentType: "application/json;charset=UTF-8",
      url : url,
      data : datas,
      success: function (result) {
        console.log(result)
        resolve(result);
      },
      error: function (e ) {
        console.log(e);
        reject(new Error('返回错误'))
      }
    })
  })
  return p
}


async function fetch_pan_list(folder) {
    var list_data = {};
    var result = await http_call(get_pan_list_url(folder),list_data,true);
    var result_obj = {};

    for(var i = 0;i < result.list.length;i ++){
        result_obj[result.list[i].server_filename] = {exist:true};
    }
    return result_obj;
}

async function fetch_share_list(fsid){
    var list_data = {};
    var result = await http_call(get_share_list_url(fsid),list_data,true);
    return result.records;
}

function get_pan_list_url(folder){
  var dir = "%2Fapps%2FCloud+Sync%2Fzhuanlan-all";
  if(folder){
    dir = "%2Fapps%2FCloud+Sync%2Fzhuanlan-all%2F"+ encodeURI(folder);
  }
  return "https://pan.baidu.com/api/list?order=time&desc=1&showempty=0&web=1&page=1&num=300&dir="+dir+"&t=0.571569333030987&channel=chunlei&web=1&app_id=250528&bdstoken="
  + token+"&clienttype=0";
}

function get_share_list_url(fsid){
  return "https://pan.baidu.com/mbox/msg/shareinfo?msg_id=471981340980807638&page=1&from_uk=228435709&gid=658103785633267975&type=2&fs_id="+fsid+"&num=300&bdstoken="+token+"&channel=chunlei&web=1&app_id=250528&clienttype=0";
}

async function sync_folder(folder){
  var url_create ='https://pan.baidu.com/api/create?a=commit&channel=chunlei&web=1&app_id=250528&bdstoken='+ token +'&clienttype=0';
    var create_data = {
        path: "/apps/Cloud Sync/zhuanlan-all" + folder,
        isdir: 1,
        block_list: [],
        ondup:"overwrite"
    }
    await http_call(url_create,create_data,false);
}

async function sync_file(fid,path){
    var url_sync = "https://pan.baidu.com/mbox/msg/transfer?bdstoken="+ token +"&channel=chunlei&web=1&app_id=250528&logid=MTU3NzM2NDYwMDg4ODAuOTYyMzEwMDE4MjA2ODI5Mg==&clienttype=0";
    var file_data = {
        from_uk: "228435709",
        msg_id: "471981340980807638",
        path: "/apps/Cloud Sync/zhuanlan-all"+ path,
        ondup: "overwrite",
        async: "1",
        type: "2",
        gid: "658103785633267975",
        fs_ids: "["+fid+"]"
    };
    await http_call(url_sync,file_data,false);
}

async function get_dlink(fid){
 var url_dlink = "https://pan.baidu.com/api/sharedownload?sign=&timestamp=&bdstoken="+ token +"&channel=chunlei&web=1&app_id=250528&logid=MTU3NzM3MDg4NzY5NTAuOTE4MDE3MjE1OTY0NDEy&clienttype=0";
 var data_dlink = {
       encrypt: "0",
        uk: "3757516097",
        product: "mbox",
        primaryid: "471981340980807638",
        fid_list: "["+fid+"]",
        extra: "{\"type\":\"group\",\"gid\":\"658103785633267975\"}"
    };
  var link = await http_call(url_dlink,data_dlink,false);
  return link.list[0].dlink;
}
var sleep = async (duration) => {
    return new Promise((resolve, reject) => {
        setTimeout(resolve, duration);
    });
};


async function caiji(){
 
  var final_data = {};
  await handle_folder("294603226355310","\/00-\u8d44\u6e90\u6587\u4ef6\/14-\u6781\u5ba2\u65f6\u95f4\/01-\u4e13\u680f\u8bfe",final_data);//unfin  
  await handle_folder("1039355554886088","\/00-\u8d44\u6e90\u6587\u4ef6\/14-\u6781\u5ba2\u65f6\u95f4\/00-\u66f4\u65b0\u4e2d\u7684\u4e13\u680f",final_data);//fin 
  console.log(final_data);
}

async function handle_folder(fsid,folder_name,obj){

  console.log("handling..." + folder_name);
  await sleep(200);
  var list = await fetch_share_list(fsid);
  var name = share_folder_2_pan_folder(folder_name);
  // var old_list = await fetch_pan_list(name);

  for(var s = 0; s < list.length; s++){
    var sc = list[s];
    obj[sc.server_filename] = {};
    //文件没必要
    if(sc.isdir === 1){
      await handle_folder(sc.fs_id,sc.path,obj[sc.server_filename]);
      //不包含，sync
      // if(!old_list[sc.server_filename] ){
      //    await sync_folder(share_folder_2_pan_folder(sc.path));
      // }
      // await handle_folder(sc.fs_id,sc.path,obj[sc.server_filename]);
    }else{
      if(is_skip(sc.server_filename)){
        continue;
      }
      await sync_file(sc.fs_id,share_folder_2_pan_folder(sc.path).replace(sc.server_filename,""));
      //obj[sc.server_filename].dlink = await get_dlink(sc.fs_id);
      obj[sc.server_filename].fsid = sc.fs_id;
      obj[sc.server_filename].size = sc.size;
    }
  }
}
function share_folder_2_pan_folder(name){
  return name.replace("\/00-\u8d44\u6e90\u6587\u4ef6\/14-\u6781\u5ba2\u65f6\u95f4\/01-\u4e13\u680f\u8bfe","").
  replace("\/00-\u8d44\u6e90\u6587\u4ef6\/14-\u6781\u5ba2\u65f6\u95f4\/00-\u66f4\u65b0\u4e2d\u7684\u4e13\u680f","");
}

function is_skip(file_name){
  if(file_name.endsWith(".pdf")){
    return true;
  }
  return false;
}

async function sync_list(list){
    for(var s = 0; s < list.length; s++){
        var sc = list[s];
        console.log(sc.server_filename);
        await guarantee_renew(sc.fs_id,sc.server_filename);
    }
}


caiji();