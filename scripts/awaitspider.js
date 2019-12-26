
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

}


var folder_stack = [];

//{
//  "folder1" :{ fetched: true}
//  "folder2" :{exist:true,fetched: false}
//}
var big_folder = {};
var walking_route = [];

var big_data = {};

async function caiji(){
  big_folder = await fetch_pan_list();
  var un_fin = await fetch_share_list("294603226355310");
  var fin = await fetch_share_list("1039355554886088");

  var list_all = un_fin.concat(fin);
  for(var sc in list_all){
    if(sc.isdir === 1){
      folder_stack.push(sc);
      await sync_folder(sc.server_filename);
    }
  }

  while(folder_stack.length > 0){
    var item = folder_stack.pop();
    if(sc.isdir === 1){
      folder_stack.push(sc);
      await sync_folder(sc.server_filename);
    }
  }
}


caiji();