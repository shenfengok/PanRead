const  dbutil = require("../db/dbutil");
const ray = require("../common/ray");

class specialmodel{

    constructor() {
        this.dao = ray.getInst(dbutil);
    }

    async findlist(uid,page){
        if(!page || page <=0) page =1;
        page -=1;
        let row = 30;
        let start = page * row;

        let sql = 'select tread.id,tread.finish,tread.title,tread.item_id,ii.title as ititle from (select s.id,s.finish, s.title,(case  when r.cid is null then (select i.id from t_special_item i   where i.special_id = s.id order by i.title asc limit 1)  else r.cid end ) as item_id from t_special s  left join t_his r on s.id = r.pid and r.uid =? order by s.title asc LIMIT ?, ? )  tread inner join t_special_item ii on tread.item_id = ii.id;';
        return  await this.dao.query(sql, [uid,start, row]);
    }
}


module.exports = specialmodel;