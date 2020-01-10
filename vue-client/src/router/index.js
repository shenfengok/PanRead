import Vue from 'vue'
import Router from 'vue-router'
import home from '../view/home'
import login from '../view/login'
import nav from '../view/nav'
import list from "../view/list";
import dtl from  '../view/dtl'
import video from "../view/video";
import videolist from "../view/videolist";
import videodtl from "../view/videodtl";
Vue.use(Router)

const router = new Router({
  routes: [
    {
      path: '/',
      redirect: '/login',
      component: {nav:nav,content:login}
    },
    {
      path: '/login',
      name: 'login',
      components: {nav:nav,content:login}
    },
    {
      path: '/home',
      name: 'home',
      components: {nav:nav,content:home}
    },
    {
      path: '/video',
      name: 'video',
      components: {nav:nav,content:video}
    },
    {
      path: '/list',
      name: 'list',
      components: {nav:nav,content:list}
    },
    {
      path: '/videolist',
      name: 'videolist',
      components: {nav:nav,content:videolist}
    },
    {
      path: '/dtl',
      name: 'dtl',
      components: {nav:nav,content:dtl}
    },
    {
      path: '/videodtl',
      name: 'videodtl',
      components: {nav:nav,content:videodtl}
    }
  ]
})

// 导航守卫
// 使用 router.beforeEach 注册一个全局前置守卫，判断用户是否登陆
router.beforeEach((to, from, next) => {
  if (to.path === '/login') {
    next();
  } else {
    let token = localStorage.getItem('Authorization');

    if (token === 'null' || token === '') {
      next('/login');
    } else {
      next();
    }
  }
})

export default router
