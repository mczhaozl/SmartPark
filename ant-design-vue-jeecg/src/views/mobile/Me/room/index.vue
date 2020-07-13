<template>
    <van-list v-if="!isEmpty"
            v-model="loading"
            :finished="finished"
            finished-text="没有更多了"
            @load="onLoad">
        <van-panel v-for="item in list" :title="item.name" :desc="'容纳人数:'+item.num"  :key="item.id">
            <div style="margin-left: 15px;padding-bottom: 5px">
                <van-tag type="primary">{{item.internet}}</van-tag>
                <van-tag type="primary" style="margin-left: 5px;">{{item.projection}}</van-tag>
            </div>
        </van-panel>
    </van-list>
    <van-empty v-else description="暂无数据" style="background-color: #ffffff"/>
</template>

<script>
    import Vue from 'vue';
    import {Tab, Tabs} from 'vant';
    import {List} from 'vant';
    import {Panel} from 'vant';
    import {Tag} from 'vant';
    import { getAction } from '@/api/manage'
    import { Empty } from 'vant';

    Vue.use(Empty);
    Vue.use(Tag);
    Vue.use(Panel);
    Vue.use(List);
    Vue.use(Tab);
    Vue.use(Tabs);
    export default {
        name: "roomIndex",
        data() {
            return {
                active: 0,
                list: [],
                pageNo:1,
                pageSize:10,
                isEmpty:false,
                loading: false,
                finished: false,
            }
        },
        methods: {
            onLoad() {
                let that = this;

                getAction("/meeting/jsRoom/list",{
                        'pageNo':that.pageNo,
                        'pageSize':that.pageSize,
                    }
                ).then(data => {
                    // 加载状态结束
                  that.loading = false;
                    console.log(data);
                    if(data.result.records !=null&&data.result.records.length>0) {
                      for(var i in data.result.records){
                        that.list.push(data.result.records[i]);
                      }
                      that.pageNo ++;
                    }else{
                      that.finished = true;
                        if(that.pageNo == 1){
                            that.empty = true;
                        }
                    }
                });

            },

        }
    }
</script>

<style scoped>

</style>