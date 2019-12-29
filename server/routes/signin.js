const router = require('koa-router')()
const user_model = require('../model/user-model.js')

router.get('/signin', async (ctx, next) => {
    let login_res = await user_model.find("shenfeng","ipqmtd123");
    if(!login_res){

    }
    // await ctx.render('signin', {
    //     title: 'welcome!'
    // })
})



module.exports = router