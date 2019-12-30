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

    function getConnection(){
    	const c = new Promise((resolve, reject) => {
    		pool.getConnection(function(err, connection){
			  if(err) {
			   reject(new Error(err))
			  }else{
			   resolve(connection);
			  }
			});
    	}
    	return c;
    }

    function query()
}

module.exports = dbutil;



