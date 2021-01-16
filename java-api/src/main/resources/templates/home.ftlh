<#include "common/header.ftlh">
<#include "common/nav.ftlh">
<div class="container" id="main">
    <div class="row">
        <div class="col-md-2" id="nav-left">
           <#include "common/menu.ftlh">
        </div>
        <div class="col-md-10">

            <h2 class="main-h2">${title}(${itemList?size})</span></h2>
            <div class="row" style="margin-bottom: 1rem;">


                <#list itemList as iit>


                <div  class="col-md-12">
                    <div class="cell">
                        <div class="use8">
                            <div  style="display:block">
                                <a style="display:inline-block"  href="/${url}/${iit.fsid}">
                                    <h3>
                                        ${iit.name}
                                    </h3>
                                </a>
                            </div>

                            <button  style="width:100px;height:30px" onclick="rukuList(${iit.fsid},${iit.name},${iit.basePath})">入库子列表</button>
                            <button  style="width:100px;height:30px" onclick="ruku(${iit.fsid},${iit.name},${iit.basePath})">入库</button>
                        </div>

                    </div>
                </div>
                </#list>

            </div>


        </div>
    </div>
</div>

<#include "common/footer.ftlh">

