const  dbutil = require("../db/dbutil");
const ray = require("../common/ray");
const obj = require("../js2mysql/dataobj");

let dao = ray.getInst(dbutil);
let all = obj;
for(var a in all){
    let sa =  all[a];
    let fin = sa.finish;
    let check = await dao.query("INSERT INTO t_special(title, finish, f_name) VALUES('SH', '600000', '白云机场') ON DUPLICATE KEY UPDATE f_market='SH', f_name='浦发银行'",[a]);
    if(check && check.length > 0){

    }
    console.log(a);
    console.log(fin)
}