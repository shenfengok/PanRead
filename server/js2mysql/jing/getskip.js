const  dbutil = require("../../db/dbutil");
const ray = require("../../common/ray");
const prefix = 'shidian';//dedao ,video,shidian,zhuanlan


async  function sync_data(){
    let dao = ray.getInst(dbutil);

    let sql = 'select * from t_'+prefix+' where is_video =1;';
    let list =  await dao.query(sql);
    let result = {};
    for(var s = 0; s < list.length; s++){

        result[list[s].title] = true;
    }
    console.log(JSON.stringify(result));
}




sync_data();