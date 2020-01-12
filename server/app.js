const Koa = require('koa');
const app = new Koa();
// const views = require('koa-views');
const json = require('koa-json');
const onerror = require('koa-onerror');
const bodyparser = require('koa-bodyparser');
const logger = require('koa-logger');
const secret = require('./config/secret.json')
const jwtKoa = require('koa-jwt');
// const index = require('./routes/index');
const users = require('./routes/users');
const login = require('./routes/login');
const rayRoute = require('./routes/rayroute');
// const testjson= require('./routes/testjson')
// const session = require('koa-session');
var cors = require('koa2-cors');
// error handler
onerror(app);

// middlewares
app.use(bodyparser({
  enableTypes:['json', 'form', 'text']
}));
// const CONFIG = {
//   key: 'koa:sess', /** 默认的cookie签名 **/
//   maxAge: 86400000,/** cookie的最大过期时间 **/
//   autoCommit: true, /** (boolean) automatically commit headers (default true) */
//   overwrite: true, /** 无效属性 */
//   httpOnly: true, /** (boolean) httpOnly or not (default true) */
//   signed: true, /** 默认签名与否 */
//   rolling: false, /** 每次请求强行设置cookie */
//   renew: false, /** cookie快过期时自动重新设置*/
// };
app.use(jwtKoa({secret: secret.sign}).unless({
        path: [/^\/api\/login/] //数组中的路径不需要通过jwt验证
    }));
app.use(cors());
// app.use(session(CONFIG, app));
app.use(json());
app.use(logger());
app.use(require('koa-static')(__dirname + '/vclient'))

// app.use(views(__dirname + '/views', {
//   extension: 'ejs'
// }));
//app.keys = ['san si er bian'];  /* cookie的签名 三思而变*/



// logger
app.use(async (ctx, next) => {
  const start = new Date()
  await next()
  const ms = new Date() - start
  console.log(`${ctx.method} ${ctx.url} - ${ms}ms`)
})

function commonRounter(route){
    const routter = new rayRoute(route)
    app.use(routter.router.routes(), routter.router.allowedMethods())
}

// // routes
// app.use(index.routes(), index.allowedMethods())

commonRounter('video')

commonRounter('dedao')

commonRounter('zhuanlan')
app.use(users.routes(), users.allowedMethods())
app.use(login.routes(), login.allowedMethods())

// error-handling
app.on('error', (err, ctx) => {
  console.error('server error', err, ctx)
});

module.exports = app
