// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import axios from 'axios'
import store from './store'

Vue.config.productionTip = false

axios.defaults.baseURL='http://localhost:3000'
axios.interceptors.request.use(
  config => {
    if (localStorage.getItem('Authorization')) {
      config.headers.Authorization = localStorage.getItem('Authorization');
    }

    return config;
  },
  error => {
    return Promise.reject(error);
});


axios.interceptors.response.use(function (response) {
    return response;
  }, function (error) {
    if (err.response.status === 401) {

       //  localStorage.removeItem('Authorization');
      	// this.$router.push('/login');
        return Proimse.reject(data)
    }
    return Promise.reject(error);
  });

Vue.prototype.axios = axios
/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  components: { App },
  template: '<App/>'
})
