const router = require('koa-router')()
const userModel = require('../model/usermodel')
const ray = require("../common/ray");
const jwt = require('jsonwebtoken')
// const session = require('koa-session');
const secret = require('../config/secret.json')
router.post('/api/login', async (ctx, next) => {
    let userRepo = ray.getInst(userModel);
    let user = await userRepo.findOne(ctx.request.body.username,ctx.request.body.password);
    if(user){
        // ctx.session.username= user.name;
        // ctx.session.userid= user.id;
        const userToken = {
            name: user.name,
            id: user.id
        }
        const token = jwt.sign(userToken, secret.sign, {expiresIn: '1h'})  // 签发token
        ctx.body = {
            message: '成功',
            t:token,
            code: 1
        }
    }

})


router.post('/login/account', async (ctx, next) => {
    ctx.body = {
        t:'token',
        status: 'ok'
    }

})



module.exports = router