<template>
  <ul>
    <template v-if="list.length !== 0">
    <li  v-for="(item, index) in list" class="page"><a  @click="routerTo(item.id)">{{item.title}}</a></li>
    </template>
<!--    <li class="page"><a href="/domain-icons/" title="display icons next to DuckDuckGo and Google search results">Domain-->
<!--      Icons</a></li>-->
<!--    <li class="page"><a href="/ghor/" title="dependency injection tool">ghor</a></li>-->
<!--    <li class="page"><a href="/ghu/" title="promising build system">ghu</a></li>-->
<!--    <li class="page"><a href="/h5ai/" title="modern HTTP web server index">h5ai</a></li>-->
<!--    <li class="page"><a href="/jquery-fracs/"-->
<!--                        title="determine the visible fractions of an HTML element">jQ.fracs</a></li>-->
<!--    <li class="page"><a href="/jquery-qrcode/" title="generate QR codes dynamically">jQ.qrcode</a></li>-->
<!--    <li class="page"><a href="/jquery-scrollpanel/" title="simple vertical scrollpanel">jQ.scrollpanel</a></li>-->
<!--    <li class="page"><a href="/kjua/" title="dynamically generated QR codes">kjua</a></li>-->
<!--    <li class="page"><a href="/lo/" title="lightweight utility library for the browser">lo</a></li>-->
<!--    <li class="page"><a href="/lolight/" title="tokenizer and syntax highlighter">lolight</a></li>-->
<!--    <li class="page"><a href="/modulejs/" title="lightweight JavaScript module system">modulejs</a></li>-->
<!--    <li class="page"><a href="/node-cgi/" title="Node as CGI-module">node-cgi</a></li>-->
<!--    <li class="page"><a href="/pagemap/" title="mini map for web pages">pagemap</a></li>-->
<!--    <li class="page"><a href="/scar/" title="test runner for Node.js and the browser">scar</a></li>-->
  </ul>

</template>

<script>

export default {
  name: 'nav',
  created () {
    this.getlist();
  },
  data() {
    return {
      list: [],
      cur :0
    }
  },
  methods :{

    getlist () {

      this.axios.get('/api/list?id=' + this.$route.query.id)
        .then((res) => {
          if (res.data) {
            this.list = res.data
          } else {
            console.log(res)
          }
        })
        .catch(err => {
          console.log(err)
        })
    },
    router2(subId){
      this.$router.push({ path: '/dtl', query: {  id:this.$route.query.id,subId:subId }})
    }


  }
}
</script>
