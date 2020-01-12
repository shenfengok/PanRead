<template>
  <div id="app">
    <aside class="left">
      <div id="sidemenu">
        <nav>
          <ul>
            <li class="home"><a href="/" title="larsjung.de">
              <svg xmlns="http://www.w3.org/2000/svg" width="512" height="512" viewBox="0 0 512 512">
                <path d="M256 28 496 202 404 484 108 484 16 202z"></path>
              </svg>
            </a></li>
          </ul>
          <router-view name="nav" ></router-view>


        </nav>
        <div class="links social"><h2>find me</h2><a @click="routerTo" >阅读历史</a><a href="//twitter.com/lrsjng">Twitter</a><a
          href="//keybase.io/lrsjng">Keybase</a><a href="/feed.xml">RSS·Feed</a></div>
        <div class="links"><h2>write me</h2><a class="obfusma2"
                                               href="?">lrsj<i>i</i>ng<i>o</i>@gmai<i>e</i>l.c<i>d</i>om</a></div>
        <div class="links"><h2>buy me a coffee</h2><a class="paypal-btn" href="#">PayPal</a></div>
        <div class="links"><a class="soft" href="/disclaimer/">imprint &amp; disclaimer</a></div>
      </div>
    </aside>
    <router-view name="content"> </router-view>
  </div>
</template>

<script>
import '../static/styles.css'
export default {
  name: 'App',
  methods:{
  routerTo(){
    this.$router.push({ path: '/history'})
  }
}
}
</script>
<style>
  a {cursor: pointer !important;}
</style>
