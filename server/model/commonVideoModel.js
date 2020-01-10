const  dbutil = require("../db/dbutil");
const ray = require("../common/ray");

class commonVideoModel{

    constructor(table_prefix,type) {
        this.dao = ray.getInst(dbutil);
        this.table =table_prefix;
        this.type = type;
    }

    async findvlist(uid,cur){
        if(!cur || cur <=0) cur =0;
        let row = 15;

        let sql = 'select tread.id,tread.finish,tread.title,tread.item_id,ii.title as ititle from' +
            ' (select s.id,s.finish, s.title,' +
            '(case  when r.cid is null then (select i.id from t_'+this.table+'_item i   where i.'+this.table+'_id = s.id order by i.title asc limit 1) ' +
            ' else r.cid end ) as item_id from t_'+this.table+' s  left join t_his r on s.id = r.pid and r.uid =? and r.type='+this.type+' order by s.title asc LIMIT ?, ? )  tread ' +
            'inner join t_'+this.table+'_item ii on tread.item_id = ii.id;';
        return  await this.dao.query(sql, [uid,cur *row, row]);
    }

    async findlist(id,cur){
        if(!cur || cur <=0) cur =0;
        let row = 30;
        let sql = 'select * from t_'+this.table+'_item where '+this.table+'_id = ? order by title asc LIMIT ?, ?;';
        return  await this.dao.query(sql, [id,cur *row, row]);
    }

    async findOne(id){

        let sql = 'select * from t_'+this.table+'_item where id = ? ;';
        let list =  await this.dao.query(sql, [id]);
        if(list && list.length > 0){
            return list[0];
        }
    }

    async loghis(id,cid,type,uid){

        let sql = "INSERT INTO t_his(pid, cid, type,uid) VALUES(?,?,?,?) ON DUPLICATE KEY UPDATE cid =?";
        let list =  await this.dao.query(sql, [id,cid,type,uid,cid]);

    }

    async findPrev(id,cur){

        let sql = 'select * from t_'+this.table+'_item where id < ? and  '+this.table+'_id =? limit 1 ;';
        let list =   await this.dao.query(sql, [cur,id]);
        if(list && list.length > 0){
            return list[0];
        }
    }

    async findNext(id,cur){

        let sql = 'select * from t_'+this.table+'_item where id > ? and  '+this.table+'_id =? limit 1 ;';
        let list =   await this.dao.query(sql, [cur,id]);
        if(list && list.length > 0){
            return list[0];
        }
    }
}


module.exports = commonVideoModel;