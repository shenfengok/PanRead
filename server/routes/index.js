const router = require('koa-router')();
const specialmodel = require("../model/specialmodel")

const ray = require("../common/ray");
/**多想高兴事，老大对自己挺好的
 * 多思自己不足，是不是因为自己惹了别人所以
 */
router.get('/', async (ctx, next) => {
  var uid = ctx.session.userid;
  let specialRepo =  ray.getInst(specialmodel);
  let list = await specialRepo.findlist(uid,ctx.request.query.cur);

  ctx.body = list;
});

router.get('/api/zlist', async (ctx, next) => {
  var uid = ctx.state.user.id;
  let specialRepo =  ray.getInst(specialmodel);
  let list = await specialRepo.findzlist(uid,ctx.query.start);

 ctx.body = list;
});

router.get('/api/list', async (ctx, next) => {
  let specialRepo =  ray.getInst(specialmodel);
  let list = await specialRepo.findlist(ctx.query.id,ctx.query.cur);
  ctx.body = list;
});

router.get('/api/dtl', async (ctx, next) => {
  let specialRepo =  ray.getInst(specialmodel);
  let list = await specialRepo.findlist(ctx.query.id,ctx.query.cur);
  ctx.body = list;
});


router.get('/json', async (ctx, next) => {
  ctx.body = {
    title: 'koa2 json'
  }
});

module.exports = router;
