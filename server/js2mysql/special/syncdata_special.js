const  dbutil = require("../../db/dbutil");
const ray = require("../../common/ray");
const obj = require("./dataobj_special.json");
async  function sync_data(){
    let dao = ray.getInst(dbutil);
    let all = obj;

    for(var a in all){
        let sa =  all[a];
        let fin = sa.finish;
        let fid = undefined === sa.fsid ? 0 : sa.fsid;
        let sql = "INSERT INTO t_special(title, finish, fid) VALUES('"+a+"', "+(sa.finish ? 1:0)
        +", "+fid+") ON DUPLICATE KEY UPDATE title='"+a+"', finish="+(sa.finish ? 1:0)+", fid="+fid +"";
        await dao.query(sql);
        let select = "select * from t_special where title ='" + a + "'";
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
                    insert_list[title].html_path = '';
                    insert_list[title].fsid_html = 0;
                    insert_list[title].audio_path = '';
                    insert_list[title].fsid_audio = 0;
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
                if(is_mp4(i)){
                    insert_list[title].audio_path = pathh;
                    insert_list[title].fsid_audio = fsiid;
                }else{
                    insert_list[title].html_path = pathh;
                    insert_list[title].fsid_html = fsiid;
                }

                insert_list[title].special_id = spe[0].id;
            }
            let f_list = Object.keys(insert_list).sort();
            for (let ill in f_list){
                let il  = insert_list[f_list[ill]];

                if("" === il.title){
                    continue;
                }

                let sqli = "INSERT INTO t_special_item(title, fsid_audio, audio_path,content_path,special_id,fsid_content) VALUES('"+il.title+"', "+il.fsid_audio
                    +",'"+il.audio_path+"','"+il.html_path+"'," +il.special_id +"," + il.fsid_html +
                    ") ON DUPLICATE KEY UPDATE title='"+il.title+"', fsid_audio='"+il.fsid_audio+"', audio_path='"+il.audio_path
                    +"', content_path='"+il.html_path +"', special_id="+il.special_id +",fsid_content=" + il.fsid_html ;
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
return str.endsWith('.mp3') || str.endsWith('.m4a')
}

sync_data();