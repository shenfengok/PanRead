var token = yunData.MYBDSTOKEN;
var foo ='shidian-all'
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
                    if(result.errno != 0){
                        var at=1;
                    }
                    resolve(result.errno);
                }else{
                    resolve(result);
                }

            },
            error: function (e ) {
                resolve(500);
                // reject(new Error())
            },
            complete : function(XMLHttpRequest,status){ //请求完成后最终执行参数
                if(status=='timeout'){
                    resolve(500);
                }
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
        "path": "/apps/Cloud Sync/"+foo+"/",
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
        filelist: "[\"/apps/Cloud Sync/"+foo+"/"+folder_name+"\"]"
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
window.t_skip_list = {"106-零基础瑜伽塑型课，带你打造迷人S型曲线":true,"104-超懂学生的作文提分课：高分变容易，学习更给力！":true,"102-简单易学的吉他弹唱课  李健吉他手，带你从入门到精":true,"100-零基础也能学好的书法课 ，15天轻松写出漂亮人生！":true,"85-女性必备护肤课，带你轻松养出逆龄好肌肤":true,"84-  极速瘦身有氧操，高效燃脂不反弹":true,"82-零基础极简化妆法，明星御用化妆师带你变身气质女神":true,"81-实用高效的语言表达速成课， 12个独门秘技，帮你成为最受欢迎的人!":true,"78-揭开情绪的真相，把握关系中的主动权":true,"77-教你巧用心理学，过更有效率的人生":true,"75-积极心理学：30天打造普通人受益终生的高效行动力":true,"74-人人都可以学会的超级记忆法，让你的人生更高效":true,"73-升级你的学习力，让你成为有效学习的高手":true,"72-大神教你玩转手机摄影，随手拍出好照片":true,"71-随学随用的零基础生活手绘课":true,"70-每天15分钟，21天带你写出一手漂亮好字":true,"69-高效生活管理术，过人人羡慕的优质生活":true,"68-厉害了！教你用手机拍出高逼格的人像照":true,"67-家的空间管理术， 让房子轻松扩容30%":true,"66-掌握主动权，妈妈最需要的人生成长课":true,"64-让孩子流利说英文，零基础家长也能玩转的英语启蒙课":true,"62-脱颖而出，12堂让孩子更自信的语言表达课":true,"59-儿童长高必修课，抓住3~7岁黄金生长期":true,"58-让你越过越幸福的婚姻心理学课":true,"57-摆脱题海奥数班，24堂颠覆传统的数学课高效提升成绩":true,"56-颠覆传统的超级作文课，让孩子轻松搞定写作":true,"55-儿童长高必修课，抓住8~14岁最后生长期":true,"54-成就自己，12堂语言表达魅力课让你与众不同":true,"53-脱颖而出，12堂优质女人成长课":true,"52-练就好声音，让你的话好听、耐听、爱听":true,"42-不将就！女生快速上手的美颜必修课":true,"41-体态管理，气质女人的减压瑜伽课":true,"40-更自信，气质女生的衣品必修课":true,"39-越吃越瘦，不一样的减肥课轻松吃出好身材":true,"38-在家就能练的健身课":true,"37-一线明星御用化妆师，教你快速掌握精致妆容":true,"36-提升衣品，12堂气质女人的速成穿搭课":true,"33-办公神器，12堂颠覆传统的Word进阶必修课":true,"31-轻松易懂的财商课，教你成为经营、赚钱达人":true,"30-大神教你零基础学PS，30堂课从入门到精通":true,"28-用搜索提升收入，掌握最热门的职场技能":true,"27-大神教你制作高逼格的PPT":true,"26-逆天啦！大神教你制作创意爆棚的PPT":true,"25-前洛杉矶副市长  陈愉的人生赢家攻略，用CEO猎头的方法猎到事业贵人、生活爱人":true,"24-用公众号增加收入，掌握最热门的职场技能":true,"22-提升“言值”，成为说话与演讲高手":true,"21-大神教你玩转Excel，收获高效人生":true,"19-撕掉单词语法书，颠覆你的传统英语学习（杨妈）":true,"18-精致女人护肤课，教你打造明星般的“冻龄”美肌":true,"17-经营自己，人人都需要的人生管理术":true,"13-大神教你最实用的时间管理术":true,"11-人人都需要学会的销售攻略：卖出一切你想卖的":true,"10-大牌明星都在练的懒人瘦身法，每天15分钟，轻松享“瘦”每一刻":true,"09-写作魔法42招，“打扮”作文得高分":true,"07-12堂女性必备的黄金人际课":true,"04-小学生高效作业课：让孩子主动学习，摆脱磨蹭拖拉":true,"02-国际超模的极简瘦身课：减脂塑形一步到位，轻松瘦身不反弹":true,"01-即学即用的高情商沟通课，轻松化解表达难题":true,"108-精致女人减龄热舞操，12天打造凹凸有致好身材":true,"105-双手按出嫩白肌，懒人护肤操":true};


async function sync_list(list){
    for(var s = 0; s < list.length; s++){
        var sc = list[s];
        if(t_skip_list[sc.server_filename]){
            console.log("skip" + sc.server_filename)
            continue;
        }
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
    var dir = "%2Fapps%2FCloud+Sync%2F"+foo+"";
    if(folder){
        dir = "%2Fapps%2FCloud+Sync%2F"+foo+"%2F"+ encodeURI(folder);
    }
    return "https://pan.baidu.com/api/list?order=time&desc=1&showempty=0&web=1&page=1&num=300&dir="+dir+"&t=0.571569333030987&channel=chunlei&web=1&app_id=250528&bdstoken="
        + token+"&clienttype=0";
}

async function buchong(list){
    var pan_list= await fetch_pan_list();
    var list_all_len = list.length;
    var all = true;
    for(var s = 0; s < list.length; s++){
        sleep(500);
        var sc = list[s];
        if(pan_list[sc.server_filename] || t_skip_list[sc.server_filename]){

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

    var list = await fetch_share_list("107317409720841");
    await sync_list(list);

    var list2 = await fetch_share_list("501442295234500");

    await sync_list(list2);
    var list3 = await fetch_share_list("299761363283010");

    await sync_list(list3);

    var list4 = await fetch_share_list("154377502367819");

    await sync_list(list4);


    var list_all = list.concat(list2).concat(list3).concat(list4);

    while(!await buchong(list_all)){
        await sleep(1000);
    }

    console.log("done");

}


caiji();
