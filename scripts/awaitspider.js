
function http_call(url,datas,isget) {
  const p = new Promise((resolve, reject) => {
    $.ajax(url, {
      type : isget ? "GET" : "POST",
      contentType: "application/json;charset=UTF-8",
      url : url,
      data : datas,
      timeout: 5000,
      success: function (result) {
        console.log(result)
        if (result.errno === 0) {
          resolve(result);
        } else {
          reject(new Error(result.errno))
        }
      },
      error: function (e ) {
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
  return "https://pan.baidu.com/api/list?order=time&desc=1&showempty=0&web=1&page=1&num=300&dir="+dir+"&t=0.571569333030987&channel=chunlei&web=1&app_id=250528&bdstoken="+yunData.MYBDSTOKEN+"&clienttype=0";
}

function get_share_list_url(fsid){
  return "https://pan.baidu.com/mbox/msg/shareinfo?msg_id=471981340980807638&page=1&from_uk=228435709&gid=658103785633267975&type=2&fs_id="+fsid+"&num=300&bdstoken="+yunData.MYBDSTOKEN+"&channel=chunlei&web=1&app_id=250528&clienttype=0";
}

async function sync_folder(folder,route){
  var url_create ='https://pan.baidu.com/api/create?a=commit&channel=chunlei&web=1&app_id=250528&bdstoken='+bdstoken+'&clienttype=0';
    var create_data = {
        path: "/apps/Cloud Sync/zhuanlan-all/" + name,
        isdir: 1,
        block_list: [],
        ondup:"overwrite"
    }
    await http_call(url_create,create_data,false);
}

async function sync_file(sc){

}


async function caiji(){
 
  await handle_folder("294603226355310","\/00-\u8d44\u6e90\u6587\u4ef6\/14-\u6781\u5ba2\u65f6\u95f4\/01-\u4e13\u680f\u8bfe");//unfin  
  await handle_folder("1039355554886088","\/00-\u8d44\u6e90\u6587\u4ef6\/14-\u6781\u5ba2\u65f6\u95f4\/00-\u66f4\u65b0\u4e2d\u7684\u4e13\u680f");//fin 
}

async function handle_folder(fsid,folder_name){

  console.log("handling..." + folder_name);
  var list = await fetch_share_list(fsid);
  var name = share_folder_2_pan_folder(folder_name);
  var old_list = await fetch_pan_list(name);

  for(var sc in list_all){
    if(sc.isdir === 1){
      //不包含，sync
      if(old_list.indexOf(sc.server_filename) < 0 ){
         await sync_folder(sc.server_filename);
      }
     
      await handle_folder(fs_id);
    }else{
      await sync_file(sc);
    }
  }
}
function share_folder_2_pan_folder(name){
  return name.replace("\/00-\u8d44\u6e90\u6587\u4ef6\/14-\u6781\u5ba2\u65f6\u95f4\/01-\u4e13\u680f\u8bfe","").
  replace("\/00-\u8d44\u6e90\u6587\u4ef6\/14-\u6781\u5ba2\u65f6\u95f4\/00-\u66f4\u65b0\u4e2d\u7684\u4e13\u680f","");
}

caiji();