const rout = require('koa-router');
const raymodel = require("../model/raymodel")

const ray = require("../common/ray");


/**
 * 通用
 */

class rayroute{

  constructor(name) {

    this.name = name;
    this.router = rout()
    this.fillRoutes()
  }

  fillRoutes(){


    this.router.get('/api/'+this.name+'list', async (ctx, next) => {
      var uid = ctx.state.user.id;
      let rayRepo = this.getModel();
      let list = await rayRepo.findvlist(uid,ctx.query.start);

      ctx.body = list;
    });

    this.router.get('/api/'+this.name+'/list', async (ctx, next) => {
      let rayRepo = this.getModel();
      let list = await rayRepo.findlist(ctx.query.id,ctx.query.cur);
      ctx.body = list;
    });

    this.router.get('/api/'+this.name+'/dtl', async (ctx, next) => {
      let rayRepo = this.getModel();
      let result = {};
      result.cur = await rayRepo.findOne(ctx.query.cur);
      result.prev = await rayRepo.findPrev(ctx.query.id,ctx.query.cur);
      result.next = await rayRepo.findNext(ctx.query.id,ctx.query.cur);
      ctx.body = result;
    });


    this.router.get('/api/loghis', async (ctx, next) => {
      let rayRepo =  this.getModel();
      await rayRepo.loghis(ctx.query.id,ctx.query.cur,ctx.query.prefix,ctx.state.user.id,ctx.query.pname,ctx.query.cname);
      ctx.body = 'success';
    });
    this.router.get('/api/history', async (ctx, next) => {
      let uid = ctx.state.user.id;
      let rayRepo =  this.getModel();
      let list = await rayRepo.findhistorylist(uid,ctx.query.start);

      ctx.body = list;
    });
  }

  getModel(){
    return  new raymodel(this.name);
  }
}

module.exports = rayroute;
