const router = require('koa-router')()
const userModel = require('../model/usermodel')
const ray = require("../common/ray");

const session = require('koa-session');

router.post('/login', async (ctx, next) => {
    let userRepo = ray.getInst(userModel);
    let user = await userRepo.findOne(ctx.request.body.name,ctx.request.body.pwd);
    if(user){
        ctx.session.username= user.name;
        ctx.session.userid= user.id;
        await ctx.render('login_suc', {
            title: 'welcome' + user.name + '!',
            userName: user.name
        })
    }

})



module.exports = router