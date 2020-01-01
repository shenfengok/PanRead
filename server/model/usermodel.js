const  dbutil = require("../db/dbutil");
const  data = require("");
const ray = require("../common/ray");

class usermodel{

    constructor() {
        this.dao = ray.getInst(dbutil);
    }

    async findOne(name,pwd){
        let sql = 'select * from t_user where name =? and pwd =?';
        let result =  await this.dao.execute(sql,[name,pwd]);
        if(result && result.length >0){
            return result[0];
        }
    }
}


module.exports = usermodel;