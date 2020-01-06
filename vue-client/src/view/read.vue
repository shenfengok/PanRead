<template>
  <div>
    <main>
      <header><h1>workspace</h1>
        <h2>latest updates ...</h2></header>
      <section>
        <ul>
          <template v-if="zlist.length !== 0">
            <li itemscope itemtype="https://schema.org/SoftwareApplication" v-for="(item, index) in zlist"><a itemprop="url" href="/domain-icons/"><span
              class="name" itemprop="name"> {{item.title}}</span><span class="version"
                                                                       itemprop="softwareVersion">{{item.finish ?'finish':'updating'}}</span><span
              class="desc"
              itemprop="description">{{item.ititle}}</span></a>
            </li>
          </template>
          <template v-else>
            <li itemscope itemtype="https://schema.org/SoftwareApplication">no items
            </li>
          </template>

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
    name: 'read',
    created () {
      this.getZlist(false);
    },
    data() {
      return {
        zlist: [],
        cur :0
      }
    },
    methods :{

      getZlist (is_next) {
        if(is_next){
          this.cur = this.cur + 1;
        }
        this.axios.get('/api/zlist?start=' + this.cur)
          .then((res) => {
            if (res.data) {
              this.zlist = res.data
            } else {
              console.log(res)
            }
          })
          .catch(err => {
            console.log(err)
          })
      },

    }
  }
</script>

