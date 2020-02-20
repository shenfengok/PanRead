const core = require('./core')
const ray = require("../common/ray");

corr = ray.getInst(core);
async function work(){
    await  corr.login();
    //极客时间---完结列表folder
    await corr.toCrawFolder("294603226355310");
    //即可时间，更新中的列表
    await corr.toCrawItem("1039355554886088");
    console.log(token);
}
work();
