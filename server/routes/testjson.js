const router = require('koa-router')()
const userModel = require('../model/usermodel')
const ray = require("../common/ray");

router.get('/user/login', async (ctx, next) => {
    let userRepo = ray.getInst(userModel);
    let user = await userRepo.findOne(ctx.request.body.name,ctx.request.body.pwd);
    ctx.body = {a:3,b:"test"}

})



module.exports = router