const router = require('koa-router')()
const userModel = require('../model/usermodel')
const ray = require("../common/ray");
router.post('/login', async (ctx, next) => {
    let userRepo = ray.getInst(userModel);
    let users = await userRepo.findOne(ctx.request.body.name,ctx.request.body.pwd);
    if(users && users.length > 0){
        await ctx.render('login_suc', {
            title: 'welcome' + users[0].name + '!',
            userName: users[0].name
        })
    }

})



module.exports = router