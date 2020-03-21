const dbutil = require("../db/dbutil");
const ray = require("../common/ray");
// const prefix = 'zhuanlan';//dedao ,video,shidian,zhuanlan
// const obj = require("./" + prefix + "-data.json");

class SyncData {

    async do(prefix,obj) {
        let dao = ray.getInst(dbutil);
        let all = obj;

        for (var a in all) {
            let sa = all[a];
            let fid = undefined === sa.fsid ? 0 : sa.fsid;
            let sql = "INSERT INTO t_" + prefix + "(title, finish, fid) VALUES('" + a + "', " + (sa.finish ? 1 : 0)
                + ", " + fid + ") ON DUPLICATE KEY UPDATE title='" + a + "', finish=" + (sa.finish ? 1 : 0) + ", fid=" + fid + "";
            await dao.query(sql);
            let select = "select * from t_" + prefix + " where title ='" + a + "'";
            let spe = await dao.query(select);
            if (spe) {
                let insert_list = {};
                let is_mp4 = 0;

                for (let i in sa) {

                    let ll = {};
                    if ("finish" === i || "fid" === i) {
                        continue;
                    }
                    let title = this.get_t(i);
                    if (!insert_list[title]) {
                        insert_list[title] = {};
                    }
                    insert_list[title].title = title;
                    insert_list[title].pid = spe[0].id;
                    if (!insert_list[title].file_info) {
                        insert_list[title].file_info = {};
                    }
                    let fi = {};
                    fi.path = sa[i].path;
                    fi.fsid = sa[i].fsid;
                    fi.file_name = sa[i].file_name;
                    let ext = this.get_e(i);
                    if (ext === 'mp4') {
                        is_mp4++;
                        // if(is_mp4 >= 3){
                        //     insert_list[title].is_video = 1;
                        // }
                    }
                    insert_list[title].file_info[ext] = fi;
                }
                let f_list = Object.keys(insert_list).sort();
                for (let ill in f_list) {
                    let il = insert_list[f_list[ill]];

                    if ("" === il.title) {
                        continue;
                    }
                    let isV = il.file_info['mp4'] ? 1 : 0;
                    let sqli = "INSERT INTO t_" + prefix + "_item(title,pid,file_info,is_video) VALUES(?,?,?,?) ON DUPLICATE KEY UPDATE title=?, pid=?, file_info=?,is_video=?";
                    let fin = JSON.stringify(il.file_info);
                    await dao.query(sqli, [il.title, il.pid, fin, isV, il.title, il.pid, fin, isV]);
                    console.log(il.title);
                }
                if (is_mp4 >= 5) {
                    let select = "update t_" + prefix + " set is_video =1 where id =" + spe[0].id;
                    await dao.query(select);
                } else {
                    let select = "update t_" + prefix + " set is_video =0 where id =" + spe[0].id;
                    await dao.query(select);
                }
            }
            console.log(a);
        }
        console.log('done');
    }

    get_t(str) {
        return str.substr(0, str.lastIndexOf('.'));
    }

    get_e(str) {
        return str.substr(str.lastIndexOf('.') + 1);
    }

}
module.exports = SyncData;
