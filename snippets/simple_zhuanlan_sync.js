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
        resolve(e);
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

async function guarantee_renew(fid,name,only_create,redo){
  var count = 0;
  if(!only_create){
     while(true){
      var del_res = await del_folder(name);
      if((del_res === 12 && !redo) ||del_res === 0) {
        break;
      }
      console.log("delete..." + name);
      await sleep(1000);
     }
  }
  
   while(true){
    count ++;
    var sync_result = await sync_folder_overwrite(fid,name);
    if(sync_result === 0  || sync_result === 2148 || sync_result === 12){
      return sync_result;
    }
    if(count > 20){
        console.log("redoing")
      await guarantee_renew(fid,name,only_create,true);
      break;
    }
    console.log("create..." + name);
    await sleep(1000);
   }
}

window.list_all = [];



async function sync_list(list){
     for(var s = 0; s < list.length; s++){
        var sc = list[s];
        console.log(sc.server_filename);
        await guarantee_renew(sc.fs_id,sc.server_filename);
      }
}


async function fetch_pan_list() {
    var list_data = {};
    var result = await http_call(get_pan_list_url(),list_data,true);
    var result_obj = {};

    for(var i = 0;i < result.list.length;i ++){
        result_obj[result.list[i].server_filename] = {exist:true};
    }
    return result_obj;
}


function get_pan_list_url(folder){
  var dir = "%2Fapps%2FCloud+Sync%2Fzhuanlan-all";
  if(folder){
    dir = "%2Fapps%2FCloud+Sync%2Fzhuanlan-all%2F"+ encodeURI(folder);
  }
  return "https://pan.baidu.com/api/list?order=time&desc=1&showempty=0&web=1&page=1&num=300&dir="+dir+"&t=0.571569333030987&channel=chunlei&web=1&app_id=250528&bdstoken="
  + token+"&clienttype=0";
}

async function buchong(list){
    var pan_list= await fetch_pan_list();
    var list_all_len = list.length;
    var all = true;
     for(var s = 0; s < list.length; s++){
        var sc = list[s];
        if(pan_list[sc.server_filename]){
          continue;
        }
        console.log("buchong..." + sc.server_filename);
        if(2148 !== await guarantee_renew(sc.fs_id,sc.server_filename,false)){
          all = false;
        }
      }
      return all;
}


async function caiji(){
 

  var set = new Set();

  var list = await fetch_share_list("294603226355310"); 
  await sync_list(list);
  
  var list2 = await fetch_share_list("1039355554886088"); 

  await sync_list(list2);

  var list_all = list.concat(list2);

  while(!await buchong(list_all)){
    await sleep(1000);
  }
  
  console.log("done");

}


caiji();
