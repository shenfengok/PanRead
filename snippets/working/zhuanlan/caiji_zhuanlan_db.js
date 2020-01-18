var token = yunData.MYBDSTOKEN;
function http_call(url,datas,isget,strict) {
    const p = new Promise((resolve, reject) => {
        $.ajax(url, {
            type : isget ? "GET" : "POST",
            contentType: "application/json;charset=UTF-8",
            url : url,
            timeout: 40000,
            data : datas,
            success: function (result) {
                console.log(result);
                if(strict){
                    if(result.errno !== 0){
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
    var resultList = [];
    var page = 1;
    while(page < 10){
        sleep(200)
        var result = await http_call(get_share_list_url(fsid,page),list_data,true);
        resultList = resultList.concat(result.records);
        if(result.has_more){

            page++;
        }else{
            break;
        }
    }
    return resultList;
}
function get_share_list_url(fsid,page){
    return "https://pan.baidu.com/mbox/msg/shareinfo?msg_id=471981340980807638&page="+page+"&from_uk=228435709&gid=658103785633267975&type=2&fs_id="+fsid+"&num=300&bdstoken="+token+"&channel=chunlei&web=1&app_id=250528&clienttype=0";
}
var sleep = async (duration) => {
    return new Promise((resolve, reject) => {
        setTimeout(resolve, duration);
    });
};



async function handle_folder(fsid,obj){

    await sleep(200);
    var list = await fetch_share_list(fsid);
    // var name = share_folder_2_pan_folder(folder_name);

    for(var s = 0; s < list.length; s++){
        var sc = list[s];

        //文件没必要
        if(sc.isdir === 1){
            await handle_folder(sc.fs_id,obj);

        }else{
            if(is_skip(sc.server_filename)){
                continue;
            }
            obj[sc.server_filename] = {};
            let cobj = obj[sc.server_filename];
            // await sync_file(sc.fs_id,share_folder_2_pan_folder(sc.path).replace(sc.server_filename,""));
            //obj[sc.server_filename].dlink = await get_dlink(sc.fs_id);
            cobj.fsid = sc.fs_id;
            cobj.size = sc.size;
            cobj.path = share_folder_2_pan_folder(sc.path);

        }
    }
}



window.finish_list = {"662233362056296":"/00-资源文件/14-极客时间/01-专栏课/100-","587919476866806":"/00-资源文件/14-极客时间/01-专栏课/051-99","714384765881638":"/00-资源文件/14-极客时间/01-专栏课/01-50"};

function share_folder_2_pan_folder(name){
    var str = name.replace("/00-资源文件/14-极客时间/00-更新中的专栏","");
    for(var i in window.finish_list){
        str = str.replace(window.finish_list[i],"");
    }
    return str;
}

function is_skip(file_name){
    if(file_name.endsWith(".pdf")){
        return true;
    }
    return false;
}

async function sync_list(list,obj,finish){
    for(var s = 0; s < list.length; s++){
        var sc = list[s];
        console.log(sc.server_filename);
        obj[sc.server_filename] = {
            finish :finish,

        };

        obj[sc.server_filename].fsid = sc.fs_id;

        await handle_folder(sc.fs_id,obj[sc.server_filename]);
    }
}


async function caiji(){
    var big_obj  ={};
    //专栏课
    // await handle_folder("294603226355310","\/00-\u8d44\u6e90\u6587\u4ef6\/14-\u6781\u5ba2\u65f6\u95f4\/01-\u4e13\u680f\u8bfe",big_obj,true);
    //
    // await handle_folder("1039355554886088","",big_obj);
    // var list = await fetch_share_list("587919476866806");
    // await sync_list(list,big_obj,true);

    for(var i in window.finish_list){
        // var list = await fetch_share_list(i);
        await sync_list(await fetch_share_list(i),big_obj,true);
    }

    var list2 = await fetch_share_list("1039355554886088");

    await sync_list(list2,big_obj,false);
    console.log(JSON.stringify(big_obj));
    console.log("done");

}


caiji();
