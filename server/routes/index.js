const router = require('koa-router')();
const specialmodel = require("../model/specialmodel")

const ray = require("../common/ray");


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

router.get('/api/history', async (ctx, next) => {
  var uid = ctx.state.user.id;
  let specialRepo =  ray.getInst(specialmodel);
  let list = await specialRepo.findhistorylist(uid,ctx.query.start);

  ctx.body = list;
});

router.get('/api/list', async (ctx, next) => {
  let specialRepo =  ray.getInst(specialmodel);
  let list = await specialRepo.findlist(ctx.query.id,ctx.query.cur);
  ctx.body = list;
});

router.get('/api/dtl', async (ctx, next) => {
  let specialRepo =  ray.getInst(specialmodel);
  let result = {};
  result.cur = await specialRepo.findOne(ctx.query.cur);
  result.prev = await specialRepo.findPrev(ctx.query.id,ctx.query.cur);
  result.next = await specialRepo.findNext(ctx.query.id,ctx.query.cur);
  ctx.body = result;
});


router.get('/api/loghis', async (ctx, next) => {
  let specialRepo =  ray.getInst(specialmodel);
  await specialRepo.loghis(ctx.query.id,ctx.query.cur,ctx.query.type,ctx.state.user.id,ctx.query.pname,ctx.query.cname,ctx.query.prefix);
  ctx.body = 'success';
});


router.get('/json', async (ctx, next) => {
  ctx.body = {
    title: 'koa2 json'
  }
});

module.exports = router;
