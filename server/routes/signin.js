const router = require('koa-router')()
const userModel = require('../model/usermodel.js')
const ray = require("../common/ray");
router.get('/signin', async (ctx, next) => {
    let userRepo = ray.getInst(userModel);
    let user = await userRepo.findOne("shenfeng","ipqmtd123");
    if(user){
        await ctx.render('signin', {
            title: 'welcome!' + user.name
        })
    }

})



module.exports = router