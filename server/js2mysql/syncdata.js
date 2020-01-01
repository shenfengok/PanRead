const  dbutil = require("../db/dbutil");
const ray = require("../common/ray");
const obj = require("../js2mysql/dataobj");

let dao = ray.getInst(dbutil);
let all = obj;
for(var a in all){
    let sa =  all[a];
    let fin = sa.finish;
    // let check = await dao.query("select * from t_special whre title =?",title);
    // if(check && check.length > 0){
    //
    // }
    console.log(a);
    console.log(fin)
}