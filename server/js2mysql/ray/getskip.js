const  dbutil = require("../../db/dbutil");
const ray = require("../../common/ray");
const prefix = 'shidian';//dedao ,video,shidian,zhuanlan
const obj = require("./"+prefix+"-data.json");

async  function sync_data(){
    let dao = ray.getInst(dbutil);
    let all = obj;
    let sql = 'select * from t_'+prefix+'_item where is_video =1;';
    let list =  await this.dao.query(sql, [id,cur *row, row]);
    JSON.stringify(list);
}




sync_data();