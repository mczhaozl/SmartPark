<template>
    <div class="cpicContainer">
        <van-nav-bar
                title="我要预约"
                left-text=""
                left-arrow
                @click-left="onClickLeft"
        />
        <van-form @submit="onSubmit">
            <van-field
                    v-model="card"
                    name="车牌号"
                    label="车牌号"
                    placeholder="请输入车牌号"
            />
            <van-field
                    readonly
                    clickable
                    type="datetime"
                    name="datetimePicker"
                    :value="value"
                    label="预约时间"
                    placeholder="点击选择时间"
                    @click="showPicker = true"
            />
            <van-popup v-model="showPicker" position="bottom">
                <van-datetime-picker
                        type="datetime"
                        :min-date="minDate"
                        @confirm="onConfirm"
                        @cancel="showPicker = false"
                />
            </van-popup>
            <div style="margin: 16px;">
                <van-button round block type="info" native-type="submit">
                    提交
                </van-button>
            </div>
        </van-form>
    </div>
</template>

<script>
    import Vue from 'vue';
    import { DatetimePicker,Divider } from 'vant';

    Vue.use(DatetimePicker);
    Vue.use(Divider);
    export default {
        name: "aform",
        components:{},
        data(){
            return {
                card:'',
                value:'',
                showPicker:false,
                minDate: new Date()
            }
        },
        methods:{
            onClickLeft() {
                this.$router.push("/appointment")
            },
            onSubmit(){

            },
            onConfirm(value){
                //时间转换
                this.showPicker = false;
                var datejson = new Date(value).toJSON();
                var date = new Date( + new Date(datejson)
                    + 8*3600*1000).toISOString().
                replace(/T/g,' ').
                replace(/\.[\d]{3}Z/,'')
                this.value = date;
            }
        }
    }
</script>

<style scoped>
    /deep/ .van-nav-bar__title{
        color: #3282F9;
    }
</style>