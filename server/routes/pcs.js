const router = require('koa-router')()
const core = require('../spider/core')
const ray = require("../common/ray");
corr = ray.getInst(core);
router.prefix('/pcs')

router.get('/shareList', async function (ctx, next) {
  let fsid = ctx.query.fsid;
  ctx.body = await corr.get_folder_child_content(fsid)
})

router.get('/bar', function (ctx, next) {
  ctx.body = 'this is a users/bar response'
})

module.exports = router
