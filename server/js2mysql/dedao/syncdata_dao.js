const  dbutil = require("../../db/dbutil");
const ray = require("../../common/ray");
const obj = require("./data.json");
async  function sync_data(){
    let dao = ray.getInst(dbutil);
    let all = obj;

    for(var a in all){
        let sa =  all[a];
        let fin = sa.finish;
        let fid = undefined === sa.fsid ? 0 : sa.fsid;
        let sql = "INSERT INTO t_dedao(title, finish, fid) VALUES('"+a+"', "+(sa.finish ? 1:0)
        +", "+fid+") ON DUPLICATE KEY UPDATE title='"+a+"', finish="+(sa.finish ? 1:0)+", fid="+fid +"";
        await dao.query(sql);
        let select = "select * from t_dedao where title ='" + a + "'";
        let spe =  await dao.query(select);
        if(spe){
            let insert_list = {};
            for (let i in sa){

                let ll = {};
                if("finish" === i || "fid" === i){
                    continue;
                }
                let title = get_t(i);
                if(!insert_list[title]){
                    insert_list[title] ={};
                }
                insert_list[title].title = title;
                insert_list[title].dedao_id = spe[0].id;
                if(!insert_list[title].file_info){
                    insert_list[title].file_info = [];
                }
                let fi = {};
                fi.path = sa[i].path;
                fi.fsid = sa[i].fsid;
                fi.file_name = sa[i].file_name;

                insert_list[title].file_info.push(fi);
            }
            let f_list = Object.keys(insert_list).sort();
            for (let ill in f_list){
                let il  = insert_list[f_list[ill]];

                if("" === il.title){
                    continue;
                }

                // let sqli = "INSERT INTO t_dedao_item(title,dedao_id,file_info) VALUES('"+il.title+"', "+il.dedao_id
                //     +",`"+JSON.stringify(il.file_info)+"`) ON DUPLICATE KEY UPDATE title='"+il.title+"', dedao_id='"+il.dedao_id+"', file_info=`"+JSON.stringify(il.file_info)+"`"  ;
                let sqli = "INSERT INTO t_dedao_item(title,dedao_id,file_info) VALUES(?,?,?) ON DUPLICATE KEY UPDATE title=?, dedao_id=?, file_info=?"  ;

                await dao.query(sqli,[il.title,il.dedao_id,JSON.stringify(il.file_info),il.title,il.dedao_id,JSON.stringify(il.file_info)]);
                console.log(il.title);
            }
        }
        console.log(a);
    }
    console.log('done');
}

function get_t(str) {
    return str.substr(0,str.lastIndexOf('.') );
}

function is_mp4(str) {
return str.endsWith('.mp4')
}

sync_data();