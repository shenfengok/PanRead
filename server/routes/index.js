const router = require('koa-router')();
const specialmodel = require("../model/specialmodel")

const ray = require("../common/ray");
/**多想高兴事，老大对自己挺好的
 * 多思自己不足，是不是因为自己惹了别人所以
 */
router.get('/', async (ctx, next) => {
  var uid = ctx.session.userid;
  let specialRepo =  ray.getInst(specialmodel);
  let list = specialRepo.findlist(uid,ctx.request.query.page);

  await ctx.render('index', {
    title: 'Hello Koa 2!',
    name: ctx.session.username,
    list_table : list
  });
});

router.get('/string', async (ctx, next) => {
  ctx.body = 'koa2 string'
});

router.get('/json', async (ctx, next) => {
  ctx.body = {
    title: 'koa2 json'
  }
});

module.exports = router;
