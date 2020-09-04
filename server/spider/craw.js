const core = require('./core')
const ray = require("../common/ray");
const from = require("./from.json");
const SyncData = require('./SyncData')
corr = ray.getInst(core);
syncdata = ray.getInst(SyncData)



async function work() {
    // await corr.login();
    //极客时间---完结列表folder
    // let jike_list = await craw_it(from.jike)
    // let jike_content = await corr.crawContent(jike_list)
    // await syncdata.do("jike",jike_content)
    // console.log(corr.replace);
}

async function craw_it(it) {
    let list = await crawfolders(it)
    list = list.concat(await crawItems(it))
    return list;
}

async function crawfolders(it) {
    let list = []
    let foo = it.folder
    for (let key in foo) {
        let ll = await corr.toCrawFolder(foo[key]);
        list = list.concat(ll);
    }
    return list;
}

async function crawItems(it) {
    let list = []
    let foo = it.item
    for (let key in foo) {
        list.push(foo[key])
        await corr.toCrawItem(foo[key]);
    }
    return list
}

work();
