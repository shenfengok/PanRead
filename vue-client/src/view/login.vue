<!--https://www.cnblogs.com/web-record/p/9876916.html-->
<template>
  <main>
    <header><h1>workspace</h1>
      <h2>latest updates ...</h2></header>
    <section>
      <ul>
        <li style="margin: 8px "><input type="text" v-model="loginForm.username" placeholder="name"/></li>
        <li style="margin: 8px "><input type="password" v-model="loginForm.password" placeholder="password"/></li>
        </li>
        <li style="margin: 8px ">
          <button @click="login">login</button>
        </li>
      </ul>
    </section>
  </main>

</template>

<script>
import {mapMutations} from 'vuex'

export default {
  data() {
    return {
      loginForm: {
        username: '',
        password: ''
      }
    };
  },

  methods: {
    ...mapMutations(['changeLogin']),
    login() {
      let _this = this;
      if (this.loginForm.username === '' || this.loginForm.password === '') {
        alert('账号或密码不能为空')
      } else {
        this.axios({
          method: 'post',
          url: '/api/login.php',
          data: _this.loginForm
        }).then(res => {
          console.log(res.data)
          _this.userToken =  res.data.token;
          _this.changeLogin({Authorization: _this.userToken});
          _this.$router.push({ path: '/jing', query: { menu:'极客专栏',prefix:'zhuanlan' }})
        }).catch(error => {
          alert('账号或密码错误')
          console.log(error)
        })
      }
    }
  }
}
</script>
