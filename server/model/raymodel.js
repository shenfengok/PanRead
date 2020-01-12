const  dbutil = require("../db/dbutil");
const ray = require("../common/ray");

class raymodel{

    constructor(table_prefix) {
        this.dao = ray.getInst(dbutil);
        this.table =table_prefix;
    }

    async findvlist(uid,cur){
        if(!cur || cur <=0) cur =0;
        let row = 30;

        let sql = 'select tread.id,tread.finish,tread.title,tread.item_id,ii.title as ititle from' +
            ' (select s.id,s.finish, s.title,' +
            '(case  when r.cid is null then (select i.id from t_'+this.table+'_item i   where i.pid = s.id order by i.title asc limit 1) ' +
            ' else r.cid end ) as item_id from t_'+this.table+' s  left join t_his r on s.id = r.pid and r.uid =? and r.prefix=\''+this.table+'\' order by s.title asc LIMIT ?, ? )  tread ' +
            'inner join t_'+this.table+'_item ii on tread.item_id = ii.id;';
        return  await this.dao.query(sql, [uid,cur *row, row]);
    }

    async findlist(id,cur){
        if(!cur || cur <=0) cur =0;
        let row = 30;
        let sql = 'select * from t_'+this.table+'_item where pid = ? order by title asc LIMIT ?, ?;';
        return  await this.dao.query(sql, [id,cur *row, row]);
    }

    async findOne(id){

        let sql = 'select * from t_'+this.table+'_item where id = ? ;';
        let list =  await this.dao.query(sql, [id]);
        if(list && list.length > 0){
            return list[0];
        }
    }

    async loghis(id,cid,prefix,uid,pname,cname){

        let sql = "INSERT INTO t_his(pid, cid, prefix,uid,pname,cname) VALUES(?,?,?,?,?,?) ON DUPLICATE KEY UPDATE cid =?,cname =?";
        let list =  await this.dao.query(sql, [id,cid,prefix,uid,pname,cname,cid,cname]);

    }

    async findPrev(id,cur){

        let sql = 'select * from t_'+this.table+'_item where id < ? and pid =? limit 1 ;';
        let list =   await this.dao.query(sql, [cur,id]);
        if(list && list.length > 0){
            return list[0];
        }
    }

    async findNext(id,cur){

        let sql = 'select * from t_'+this.table+'_item where id > ? and pid =? limit 1 ;';
        let list =   await this.dao.query(sql, [cur,id]);
        if(list && list.length > 0){
            return list[0];
        }
    }
    async findhistorylist(uid,cur){
        if(!cur || cur <=0) cur =0;
        let row = 30;
        let sql = 'select * from t_his where  uid =? order by up_time desc limit ?,?  ;';
        return  await  this.dao.query(sql, [uid,cur *row, row]);

    }
}


module.exports = raymodel;