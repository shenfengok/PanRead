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
          <ul>
            <li class="page"><a href="/buz/" title="list JS file sizes - babeled, uglified and zipped">buz</a></li>
            <li class="page"><a href="/domain-icons/" title="display icons next to DuckDuckGo and Google search results">Domain
              Icons</a></li>
            <li class="page"><a href="/ghor/" title="dependency injection tool">ghor</a></li>
            <li class="page"><a href="/ghu/" title="promising build system">ghu</a></li>
            <li class="page"><a href="/h5ai/" title="modern HTTP web server index">h5ai</a></li>
            <li class="page"><a href="/jquery-fracs/"
                                title="determine the visible fractions of an HTML element">jQ.fracs</a></li>
            <li class="page"><a href="/jquery-qrcode/" title="generate QR codes dynamically">jQ.qrcode</a></li>
            <li class="page"><a href="/jquery-scrollpanel/" title="simple vertical scrollpanel">jQ.scrollpanel</a></li>
            <li class="page"><a href="/kjua/" title="dynamically generated QR codes">kjua</a></li>
            <li class="page"><a href="/lo/" title="lightweight utility library for the browser">lo</a></li>
            <li class="page"><a href="/lolight/" title="tokenizer and syntax highlighter">lolight</a></li>
            <li class="page"><a href="/modulejs/" title="lightweight JavaScript module system">modulejs</a></li>
            <li class="page"><a href="/node-cgi/" title="Node as CGI-module">node-cgi</a></li>
            <li class="page"><a href="/pagemap/" title="mini map for web pages">pagemap</a></li>
            <li class="page"><a href="/scar/" title="test runner for Node.js and the browser">scar</a></li>
          </ul>
        </nav>
        <div class="links social"><h2>find me</h2><a href="//github.com/lrsjng">GitHub</a><a href="//twitter.com/lrsjng">Twitter</a><a
          href="//keybase.io/lrsjng">Keybase</a><a href="/feed.xml">RSS·Feed</a></div>
        <div class="links"><h2>write me</h2><a class="obfusma2"
                                               href="?">lrsj<i>i</i>ng<i>o</i>@gmai<i>e</i>l.c<i>d</i>om</a></div>
        <div class="links"><h2>buy me a coffee</h2><a class="paypal-btn" href="#">PayPal</a></div>
        <div class="links"><a class="soft" href="/disclaimer/">imprint &amp; disclaimer</a></div>
      </div>
    </aside>
    <router-view/>
  </div>
</template>

<script>
import '../static/styles.css'
export default {
  name: 'App'
}
</script>
