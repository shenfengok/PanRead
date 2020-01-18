const mysql = require("mysql")

class dbutil{
    constructor() {
        this.pool = mysql.createPool({
            host: '192.168.33.102',
            user: 'root',
            password: 'Qwert,1102',
            database: 'jing',
            port: 3306
        });
    }

    async query(sql,params){
        let conn = await this.getConnection();
        return new Promise((resolve, reject) => {
            conn.query(sql,params,function(err,result){
                conn.release();
                if (err) {
                    console.log(sql);
                    reject(new Error( err.message))
                } else {

                    resolve(result);
                }
            });
        });
    }

    async execute(sql,params){
        let conn = await this.getConnection();
        return new Promise((resolve, reject) => {

             conn.query(sql,params,function (err, result) {
                 conn.release();
                if (err) {
                    reject(new Error(err.message))
                } else {
                    resolve(result);
                }

            });
        });

    }

     async getConnection(){
         return new Promise((resolve, reject) => {
            this.pool.getConnection(function (err, connection) {
                if (err) {
                    reject(new Error(err.message))
                } else {
                    resolve(connection);
                }
            });
        });
    }

}

module.exports = dbutil;



