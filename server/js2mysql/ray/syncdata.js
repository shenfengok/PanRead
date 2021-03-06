const  dbutil = require("../../db/dbutil");
const ray = require("../../common/ray");
const prefix = 'zhuanlan';//dedao ,video,shidian,zhuanlan
const obj = require("./"+prefix+"-data.json");
async  function sync_data(){
    let dao = ray.getInst(dbutil);
    let all = obj;

    for(var a in all){
        let sa =  all[a];
        let fin = sa.finish;
        let fid = undefined === sa.fsid ? 0 : sa.fsid;
        let sql = "INSERT INTO t_"+prefix+"(title, finish, fid) VALUES('"+a+"', "+(sa.finish ? 1:0)
        +", "+fid+") ON DUPLICATE KEY UPDATE title='"+a+"', finish="+(sa.finish ? 1:0)+", fid="+fid +"";
        await dao.query(sql);
        let select = "select * from t_"+prefix+" where title ='" + a + "'";
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
                insert_list[title].pid = spe[0].id;
                if(!insert_list[title].file_info){
                    insert_list[title].file_info = {};
                }
                let fi = {};
                fi.path = sa[i].path;
                fi.fsid = sa[i].fsid;
                fi.file_name = sa[i].file_name;

                insert_list[title].file_info[get_e(i)] = fi ;
            }
            let f_list = Object.keys(insert_list).sort();
            for (let ill in f_list){
                let il  = insert_list[f_list[ill]];

                if("" === il.title){
                    continue;
                }
                let sqli = "INSERT INTO t_"+prefix+"_item(title,pid,file_info) VALUES(?,?,?) ON DUPLICATE KEY UPDATE title=?, pid=?, file_info=?"  ;
                let fin = JSON.stringify(il.file_info);
                await dao.query(sqli,[il.title,il.pid,fin,il.title,il.pid,fin]);
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

function get_e(str) {
    return str.substr(str.lastIndexOf('.')+1 );
}


sync_data();