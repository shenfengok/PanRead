<template>
  <div>
    <main>
      <header><h1>workspace</h1>
        <h2>latest updates ...</h2></header>
      <section>
        <ul>
          <li itemscope itemtype="https://schema.org/SoftwareApplication"><a itemprop="url" href="/domain-icons/"><span
            class="name" itemprop="name">Domain Icons</span><span class="version"
                                                                  itemprop="softwareVersion">0.12.0</span><span
            class="desc"
            itemprop="description">display icons next to DuckDuckGo and Google search results</span></a>
          </li>
          <li itemscope itemtype="https://schema.org/SoftwareApplication"><a itemprop="url" href="/ghu/"><span
            class="name"
            itemprop="name">ghu</span><span
            class="version" itemprop="softwareVersion">0.23.0</span><span class="desc" itemprop="description">promising build system</span></a>
          </li>
          <li itemscope itemtype="https://schema.org/SoftwareApplication"><a itemprop="url" href="/scar/"><span
            class="name"
            itemprop="name">scar</span><span
            class="version" itemprop="softwareVersion">2.2.0</span><span class="desc" itemprop="description">test runner for Node.js and the browser</span></a>
          </li>
          <li itemscope itemtype="https://schema.org/SoftwareApplication"><a itemprop="url" href="/jquery-qrcode/"><span
            class="name" itemprop="name">jQ.qrcode</span><span class="version"
                                                               itemprop="softwareVersion">0.17.0</span><span
            class="desc" itemprop="description">generate QR codes dynamically</span></a></li>
          <li itemscope itemtype="https://schema.org/SoftwareApplication"><a itemprop="url" href="/node-cgi/"><span
            class="name" itemprop="name">node-cgi</span><span class="version"
                                                              itemprop="softwareVersion">0.7.0</span><span
            class="desc" itemprop="description">Node as CGI-module</span></a></li>
          <li itemscope itemtype="https://schema.org/SoftwareApplication"><a itemprop="url" href="/kjua/"><span
            class="name"
            itemprop="name">kjua</span><span
            class="version" itemprop="softwareVersion">0.6.0</span><span class="desc" itemprop="description">dynamically generated QR codes</span></a>
          </li>
          <li itemscope itemtype="https://schema.org/SoftwareApplication"><a itemprop="url" href="/pagemap/"><span
            class="name" itemprop="name">pagemap</span><span class="version"
                                                             itemprop="softwareVersion">1.2.0</span><span
            class="desc" itemprop="description">mini map for web pages</span></a></li>
          <li itemscope itemtype="https://schema.org/SoftwareApplication"><a itemprop="url" href="/buz/"><span
            class="name"
            itemprop="name">buz</span><span
            class="version" itemprop="softwareVersion">1.4.0</span><span class="desc" itemprop="description">list JS file sizes - babeled, uglified and zipped</span></a>
          </li>
          <li itemscope itemtype="https://schema.org/SoftwareApplication"><a itemprop="url" href="/ghor/"><span
            class="name"
            itemprop="name">ghor</span><span
            class="version" itemprop="softwareVersion">0.9.0</span><span class="desc" itemprop="description">dependency injection tool</span></a>
          </li>
          <li itemscope itemtype="https://schema.org/SoftwareApplication"><a itemprop="url" href="/lolight/"><span
            class="name" itemprop="name">lolight</span><span class="version"
                                                             itemprop="softwareVersion">1.3.0</span><span
            class="desc" itemprop="description">tokenizer and syntax highlighter</span></a></li>
          <li itemscope itemtype="https://schema.org/SoftwareApplication"><a itemprop="url" href="/modulejs/"><span
            class="name" itemprop="name">modulejs</span><span class="version"
                                                              itemprop="softwareVersion">2.7.0</span><span
            class="desc" itemprop="description">lightweight JavaScript module system</span></a></li>
          <li itemscope itemtype="https://schema.org/SoftwareApplication"><a itemprop="url" href="/lo/"><span
            class="name"
            itemprop="name">lo</span><span
            class="version" itemprop="softwareVersion">0.40.0</span><span class="desc" itemprop="description">lightweight utility library for the browser</span></a>
          </li>
          <li itemscope itemtype="https://schema.org/SoftwareApplication"><a itemprop="url"
                                                                             href="/jquery-scrollpanel/"><span
            class="name"
            itemprop="name">jQ.scrollpanel</span><span
            class="version" itemprop="softwareVersion">1.1.0</span><span class="desc" itemprop="description">simple vertical scrollpanel</span></a>
          </li>
          <li itemscope itemtype="https://schema.org/SoftwareApplication"><a itemprop="url" href="/jquery-fracs/"><span
            class="name" itemprop="name">jQ.fracs</span><span class="version"
                                                              itemprop="softwareVersion">1.0.1</span><span
            class="desc" itemprop="description">determine the visible fractions of an HTML element</span></a></li>
          <li itemscope itemtype="https://schema.org/SoftwareApplication"><a itemprop="url" href="/h5ai/"><span
            class="name"
            itemprop="name">h5ai</span><span
            class="version" itemprop="softwareVersion">0.29.2</span><span class="desc" itemprop="description">modern HTTP web server index</span></a>
          </li>
        </ul>
      </section>
    </main>
    <div id="fail"><span class="noJsMsg">Works best with JavaScript enabled!</span><a class="noBrowserMsg"
                                                                                      href="http://browsehappy.com">Works
      best in modern browsers!</a></div>
    <div class="paypal">
      <form class="paypal-form" action="//www.paypal.com/cgi-bin/webscr" method="post" target="_blank"><input
        type="hidden"
        name="cmd"
        value="_s-xclick"><input
        type="hidden" name="lc" value="US"><input type="hidden" name="hosted_button_id" value="8WSPKWT7YBTSQ"></form>
    </div>
  </div>
</template>

<script>

export default {
  name: 'home',
  data() {
    return {
      msg: 'Welcome to Your Vue.js App'
    }
  }
}
</script>

