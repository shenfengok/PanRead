var token = yunData.MYBDSTOKEN;
function http_call(url,datas,isget,strict) {
  const p = new Promise((resolve, reject) => {
    $.ajax(url, {
      type : isget ? "GET" : "POST",
      contentType: "application/json;charset=UTF-8",
      url : url,
      data : datas,
      success: function (result) {
        console.log(result);
        if(strict){
          if(result.errno != 0){
             var at=1;
          }
          resolve(result.errno);
        }else{
          resolve(result);
        }
        
      },
      error: function (e ) {
        resolve(result);
        console.log(e);
        if(strict){
          resolve(500);
        }
        // reject(new Error())
      }
    })
  })
  return p
}

async function fetch_share_list(fsid){
    var list_data = {};
    var result = await http_call(get_share_list_url(fsid),list_data,true);
    return result.records;
}
function get_share_list_url(fsid){
  return "https://pan.baidu.com/mbox/msg/shareinfo?msg_id=471981340980807638&page=1&from_uk=228435709&gid=658103785633267975&type=2&fs_id="+fsid+"&num=300&bdstoken="+token+"&channel=chunlei&web=1&app_id=250528&clienttype=0";
}


async function sync_folder_overwrite(fid){
  
    var url_sync = "https://pan.baidu.com/mbox/msg/transfer?bdstoken="+token+"&channel=chunlei&web=1&app_id=250528&logid=MTU3NzUxOTg0OTgyMDAuNzIzNzg0OTc0ODEyNjY2MQ==&clienttype=0";
    var file_data = {
        "from_uk": "228435709",
        "msg_id": "471981340980807638",
        "path": "/apps/Cloud Sync/zhuanlan-all/",
        "ondup": "overwrite",
        "async": "1",
        "type": "2",
        "gid": "658103785633267975",
        "fs_ids": "["+fid+"]"
    };
   return await http_call(url_sync,file_data,false,true);
}

async function del_folder(folder_name){
  var del_url = "https://pan.baidu.com/api/filemanager?opera=delete&async=2&onnest=fail&channel=chunlei&web=1&app_id=250528&bdstoken="+token+"&logid=MTU3NzUyMDY2MDk3NjAuODgyNjMwNTU3MTExNTE1&clienttype=0";
  var del_data= {
    filelist: "[\"/apps/Cloud Sync/zhuanlan-all/"+folder_name+"\"]"
  };
  return await http_call(del_url,del_data,false,true);
}


var sleep = async (duration) => {
    return new Promise((resolve, reject) => {
        setTimeout(resolve, duration);
    });
};

async function guarantee_renew(fid,name){
   while(true ){
    var del_res = await del_folder(name);
    if(del_res === 12 ||del_res === 0) {
      break;
    }
    console.log("delete..." + name);
    sleep(200);
   }
   while(true ){
    var sync_result = await sync_folder_overwrite(fid,name);
     if(sync_result === 0  || sync_result === 2148 || sync_result === 12){
      break;
    }
    console.log("create..." + name);
    sleep(200);
   }
}


async function caiji(){
 

  var set = new Set();

  var list = await fetch_share_list("294603226355310"); 
  await sync_list(list);
  
  var list2 = await fetch_share_list("1039355554886088"); 
  await sync_list(list2);
  console.log("done");

}

async function sync_list(list){
     for(var s = 0; s < list.length; s++){
        var sc = list[s];
        console.log(sc.server_filename);
        await guarantee_renew(sc.fs_id,sc.server_filename);
      }
}



caiji();
