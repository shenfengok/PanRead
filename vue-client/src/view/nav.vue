<template>
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

</template>

<script>

export default {
  name: 'nav',
  created () {
    // this.getZlist(false);
  },
  data() {
    return {
      zlist: [],
      cur :0
    }
  },
}
</script>
