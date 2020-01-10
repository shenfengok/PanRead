const  dbutil = require("../../db/dbutil");
const ray = require("../../common/ray");
const obj = require("./dataobj_video.json");
async  function sync_data(){
    let dao = ray.getInst(dbutil);
    let all = obj;

    for(var a in all){
        let sa =  all[a];
        let fin = sa.finish;
        let fid = undefined === sa.fsid ? 0 : sa.fsid;
        let sql = "INSERT INTO t_video(title, finish, fid) VALUES('"+a+"', "+(sa.finish ? 1:0)
        +", "+fid+") ON DUPLICATE KEY UPDATE title='"+a+"', finish="+(sa.finish ? 1:0)+", fid="+fid +"";
        await dao.query(sql);
        let select = "select * from t_video where title ='" + a + "'";
        let spe =  await dao.query(select);
        if(spe){
            let insert_list = {};
            for (let i in sa){
                if(!is_mp4(i)){
                    continue;
                }
                let ll = {};
                if("finish" === i || "fid" === i){
                    continue;
                }
                let title = get_t(i);
                if(!insert_list[title]){
                    insert_list[title] ={};
                }
                insert_list[title].title = title;
                let fsiid = 0;
                if(sa[i].fsid !== undefined){
                    fsiid = sa[i].fsid;
                }
                let pathh = '';
                if( sa[i].path!== undefined){
                    pathh= sa[i].path;
                }
                insert_list[title].path = pathh;
                insert_list[title].fsid = fsiid;
                insert_list[title].file_name = sa[i].file_name;
                insert_list[title].video_id = spe[0].id;
            }
            let f_list = Object.keys(insert_list).sort();
            for (let ill in f_list){
                let il  = insert_list[f_list[ill]];

                if("" === il.title){
                    continue;
                }

                let sqli = "INSERT INTO t_video_item(title, fsid,path,video_id,file_name) VALUES('"+il.title+"', "+il.fsid
                    +",'"+il.path+"','"+il.video_id+"','"+il.file_name+"') ON DUPLICATE KEY UPDATE title='"+il.title+"', fsid='"+il.fsid+"', path='"+il.path
                    +"', video_id='"+il.video_id +"',file_name='"+ il.file_name+"'"  ;
                await dao.query(sqli);
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