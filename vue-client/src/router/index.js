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
import dedao from "../view/dedao";
import dedaolist from "../view/dedaolist";
import dedaodtl from "../view/dedaodtl";
import history from "../view/history";
import ray from "../view/ray";
import raylist from "../view/raylist";
import raydtl from "../view/raydtl";
import play from '../view/play'
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
      path: '/ray',
      name: 'ray',
      components: {nav:nav,content:ray}
    },
    {
      path: '/history',
      name: 'history',
      components: {nav:nav,content:history}
    },
    {
      path: '/video',
      name: 'video',
      components: {nav:nav,content:video}
    },
    {
      path: '/play',
      name: 'play',
      components: {nav:nav,content:play}
    },
    {
      path: '/dedao',
      name: 'dedao',
      components: {nav:nav,content:dedao}
    },
    {
      path: '/list',
      name: 'list',
      components: {nav:nav,content:list}
    },
    {
      path: '/raylist',
      name: 'raylist',
      components: {nav:nav,content:raylist}
    },
    {
      path: '/videolist',
      name: 'videolist',
      components: {nav:nav,content:videolist}
    },
    {
      path: '/dedaolist',
      name: 'dedaolist',
      components: {nav:nav,content:dedaolist}
    },
    {
      path: '/dtl',
      name: 'dtl',
      components: {nav:nav,content:dtl}
    },
    {
      path: '/raydtl',
      name: 'raydtl',
      components: {nav:nav,content:raydtl}
    },
    {
      path: '/videodtl',
      name: 'videodtl',
      components: {nav:nav,content:videodtl}
    },
    {
      path: '/dedaodtl',
      name: 'dedaodtl',
      components: {nav:nav,content:dedaodtl}
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
