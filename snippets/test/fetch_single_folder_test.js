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
        var result = await http_call(get_share_list_url(fsid,page),list_data,true);
        if(result.records && result.records.length > 0){
            resultList = resultList.concat(result.records);
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

console.log(fetch_share_list("1103599202812190"))