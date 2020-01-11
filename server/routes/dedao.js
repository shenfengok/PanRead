const rout = require('koa-router');
const videoModel = require("../model/commonVideoModel")

const ray = require("../common/ray");


/**
 * 文章+音频类通用
 */
class dedao{

  constructor(name,type) {

    this.name = name;
    this.type = type;
    this.router = rout()
    this.fillRoutes()
  }

  fillRoutes(){

    // this.router.get('/', async (ctx, next) => {
    //   var uid = ctx.session.userid;
    //   let videoRepo =  this.getModel();
    //   let list = await videoRepo.findlist(uid,ctx.request.query.cur);
    //
    //   ctx.body = list;
    // });

    this.router.get('/api/'+this.name+'list', async (ctx, next) => {
      var uid = ctx.state.user.id;
      let videoRepo = this.getModel();
      let list = await videoRepo.findvlist(uid,ctx.query.start);

      ctx.body = list;
    });

    this.router.get('/api/'+this.name+'/list', async (ctx, next) => {
      let videoRepo = this.getModel();
      let list = await videoRepo.findlist(ctx.query.id,ctx.query.cur);
      ctx.body = list;
    });

    this.router.get('/api/'+this.name+'/dtl', async (ctx, next) => {
      let videoRepo = this.getModel();
      let result = {};
      result.cur = await videoRepo.findOne(ctx.query.cur);
      result.prev = await videoRepo.findPrev(ctx.query.id,ctx.query.cur);
      result.next = await videoRepo.findNext(ctx.query.id,ctx.query.cur);
      ctx.body = result;
    });


    this.router.get('/api/'+this.name+'/loghis', async (ctx, next) => {
      let videoRepo = this.getModel();
      await videoRepo.loghis(ctx.query.id,ctx.query.cur,this.type,ctx.state.user.id);
      ctx.body = 'success';
    });
  }

  getModel(){
    return  new videoModel(this.name,this.type);
  }
}

module.exports = dedao;
