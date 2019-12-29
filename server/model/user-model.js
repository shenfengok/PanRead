const mysql = require("mysql")

class dbutil{
    constructor() {
        this.pool = mysql.createPool({
            host: '192.168.33.101',
            user: 'shenfeng',
            password: 'ipqmtd123',
            database: 'ray',
            port: 3307
        });
    }

    async function query(sql){

    }
}

module.exports = dbutil;