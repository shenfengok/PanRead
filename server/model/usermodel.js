const  dbutil = require("../db/dbutil");
const ray = require("../common/ray");

class usermodel{

    constructor() {
        this.dao = ray.getInst(dbutil);

    }

    async findOne(name,pwd){
        let sql = 'select * from t_user where name =? and pwd =?';
        return await dao.execute(sql,[name,pwd]);
    }
}