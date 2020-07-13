<template>
    <div class="cpicContainer">
        <van-row>
            <van-col span="1">
                <router-link to="/index">
                    <van-icon name="arrow-left" size="0.5rem"
                              style="margin-top: 0.5rem;margin-right: -10px;"></van-icon>
                </router-link>
            </van-col>
            <van-col span="21">
                <form action="/">
                    <van-search
                            id="input-search"
                            v-model="searchValue"
                            placeholder="请输入搜索目的地"
                            @input="changeInput"
                            @search="onSearch">
                        <slot slot="right-icon" name="right-icon"><span @click="audioSearch"><van-icon
                                :name="icon"/></span></slot>
                    </van-search>
                </form>
            </van-col>
            <van-col span="2">
                <router-link to="/parkinglot/list">
                    <van-icon name="bars" size="0.5rem" style="margin-top: 0.5rem;"/>
                </router-link>
            </van-col>
        </van-row>

        <!--地图 -->
        <van-row>
            <div id="container" v-bind:style="{width:'auto',height: mapHeight }"></div>
        </van-row>
        <van-row v-if="!show">
            <van-button type="default" class="van-tabbar--fixed" @click="more()" style="width: 100%;color: #39a9ed">
                点击展开更多附近停车场
            </van-button>
        </van-row>
        <van-action-sheet v-model="show" :title="roadName" :overlay="overlay" style="height: 400px   ">
            <van-dropdown-menu>
                <van-dropdown-item v-model="distance" :options="option" :overlay="overlay"/>
                <van-dropdown-item title="筛选" ref="item">
                    <van-switch-cell v-model="switch1" title="距离优先"/>
                    <van-switch-cell v-model="switch2" title="价格优先"/>
                    <van-button block type="info" @click="onConfirm">确认</van-button>
                </van-dropdown-item>
            </van-dropdown-menu>
            <div style="height: 270px;overflow-y: auto">
                <van-empty v-if="isdata" description="附近暂无停车场"/>
                <div style="margin-top: 15px"></div>
                <van-row v-if="!isdata&&park1" style="padding-left: 10px;padding-right:10px">
                    <van-col span="18">
                        <van-row class="row-margin row-name">中节能停车场</van-row>
                        <van-row class="row-margin"><span>总车位：1045 空车位：<span style="color: #4CCB65">860</span></span>
                        </van-row>
                        <van-row class="row-margin">
                            <van-tag plain type="primary" style="margin-right: 5px">现金支付</van-tag>
                            <van-tag plain type="primary">微信支付</van-tag>
                        </van-row>
                    </van-col>
                    <van-col span="6">
                        <van-row>
                            <van-image style="margin:10px" @click="navigation('高德地图',28.689567,115.994359)"
                                       round
                                       width="1.25rem"
                                       height="1.25rem"
                                       :src="nav"
                            />
                        </van-row>
                        <van-row>距离{{zjnDistance}}米</van-row>
                    </van-col>
                </van-row>
                <van-divider v-if="!isdata&&park1"/>
                <van-row v-if="!isdata&&park2" style="padding-left: 10px;padding-right:10px">
                    <van-col span="18">
                        <van-row class="row-margin row-name">公园停车场</van-row>
                        <van-row class="row-margin">总车位：298 空车位：<span style="color: #4CCB65">2</span></van-row>
                        <van-row class="row-margin">
                            <van-tag plain type="primary">微信支付</van-tag>
                        </van-row>
                    </van-col>
                    <van-col span="6">
                        <van-row>
                            <van-image style="margin:10px" @click="navigation('高德地图', 28.690579,115.993054)"
                                       round
                                       width="1.25rem"
                                       height="1.25rem"
                                       :src="nav"
                            />
                        </van-row>
                        <van-row>距离{{gyDistance}}米</van-row>
                    </van-col>
                </van-row>
                <van-divider v-if="!isdata&&park2"/>
            </div>
        </van-action-sheet>
        <van-action-sheet
                v-model="action"
                :actions="opactions"
                cancel-text="取消"
                @select="onSelect"
                @cancel="onCancel"
        />
    </div>
</template>

<script>
    import Vue from 'vue';
    import {NavBar, Divider} from 'vant';
    import {Search} from 'vant';
    import {Col, Row} from 'vant';
    import {Icon} from 'vant';
    import {Button} from 'vant';
    import {ActionSheet} from 'vant';
    import {DropdownMenu, DropdownItem} from 'vant';
    import {SwitchCell} from 'vant';
    import {Empty} from 'vant';
    import {Tag} from 'vant';
    import {Image as VanImage} from 'vant';
    import audio from '@/assets/images/audio.png'
    import nav from '@/assets/images/nav.png'
    import {lazyAMapApiLoaderInstance} from 'vue-amap';

    Vue.use(VanImage);
    Vue.use(Tag);
    Vue.use(Divider);
    Vue.use(Empty);
    Vue.use(SwitchCell);
    Vue.use(DropdownMenu);
    Vue.use(DropdownItem);
    Vue.use(ActionSheet);
    Vue.use(Button);
    Vue.use(Icon);
    Vue.use(Col);
    Vue.use(Row);
    Vue.use(Search);
    Vue.use(NavBar);

    export default {
        name: "index",
        components: {},
        data() {
            return {
                map: {},
                geoMarker: {},
                marker: {},
                marker1: {},
                distance: 2,
                switch1: false,
                switch2: false,
                park1: true,
                park2: true,
                roadName: '',
                nav: nav,
                lat: '',
                lng: '',
                gdMap: false,
                bdMap: false,
                txMap: false,
                ggMap: false,
                isdata: false,
                opactions: [],
                action: false,
                option: [
                    {text: '500m', value: 0.5},
                    {text: '1km', value: 1},
                    {text: '2km', value: 2},
                    {text: '3km', value: 3},
                ],
                searchValue: '',
                show: true,
                overlay: false,
                mapHeight: (document.body.clientHeight - 85) + 'px',
                icon: audio,
                position: {lat: '', lng: ''},
                //centerPosition: {},
                zjnDistance: 0,
                gyDistance: 0
            }
        },
        mounted() {
            lazyAMapApiLoaderInstance.load().then(() => {
                let that = this;
                that.map = new AMap.Map('container', {
                    touchZoomCenter: 1,
                    features: ['bg', 'road', 'building', 'point'],
                    showBuildingBlock: true,
                    zoom: 16
                });
                that.map.plugin('AMap.Geolocation', function () {
                    var geolocation = new AMap.Geolocation({
                        enableHighAccuracy: true,//是否使用高精度定位，默认:true
                        timeout: 3000,          //超过s秒后停止定位，默认：无穷大
                        maximumAge: 0,           //定位结果缓存0毫秒，默认：0
                        convert: true,           //自动偏移坐标，偏移后的坐标为高德坐标，默认：true
                        showButton: true,        //显示定位按钮，默认：true
                        buttonPosition: 'LT',    //定位按钮停靠位置，默认：'LB'，左下角
                        buttonOffset: new AMap.Pixel(10, 20),//定位按钮与设置的停靠位置的偏移量，默认：Pixel(10, 20)
                        showMarker: true,        //定位成功后在定位到的位置显示点标记，默认：true
                        showCircle: true,        //定位成功后用圆圈表示定位精度范围，默认：true
                        panToLocation: true,     //定位成功后将定位到的位置作为地图中心点，默认：true
                        zoomToAccuracy: true      //定位成功后调整地图视野范围使定位位置及精度范围视野内可见，默认：false
                    });
                    that.map.addControl(geolocation);
                    that.map.on('touchend', function () {
                        that.show = false;
                    }, that);
                    geolocation.getCurrentPosition();
                    AMap.event.addListener(geolocation, 'complete', that.onComplete);//返回定位信息
                    AMap.event.addListener(geolocation, 'error', that.onError);      //返回定位出错信息
                });
                //标注，中节能停车场和公园停车场
                let content = '<div class="marker"><img src="https://webapi.amap.com/theme/v1.3/markers/b/mark_b.png"><span class="markerLable">860</span></div>';
                that.marker = new AMap.Marker({
                    title: '中节能停车场',
                    content: content,
                    //设置基点偏移
                    offset: new AMap.Pixel(-10, -10),
                    map: that.map,
                    showPositionPoint: true,
                    position: new AMap.LngLat(115.994359, 28.689567),
                    zIndex: 100
                });
                let content1 = '<div class="marker"><img src="https://webapi.amap.com/theme/v1.3/markers/b/mark_r.png"><span style=" color: white;\n' +
                    '        position: fixed;\n' +
                    '        margin-top: 0.15rem;\n' +
                    '        margin-left: -0.50rem;\n' +
                    '        font-weight: bold;">2</span></div>';
                that.marker1 = new AMap.Marker({
                    title: '公园停车场',
                    content: content1,
                    //设置基点偏移
                    offset: new AMap.Pixel(-10, -10),
                    map: that.map,
                    showPositionPoint: true,
                    position: new AMap.LngLat(115.993054, 28.690579),
                    zIndex: 100
                });
                that.marker1.on('click', function () {
                    that.show = true;
                    that.action = false;
                    that.park1 = false;
                    that.park2 = true;
                }, that);
                that.marker.on('click', function () {
                    that.show = true;
                    that.action = false;
                    that.park1 = true;
                    that.park2 = false;
                }, that);
                //  });

                let traffic = new AMap.TileLayer.Traffic({
                    'autoRefresh': true,     //是否自动刷新，默认为false
                    'interval': 180,         //刷新间隔，默认180s
                });
                that.map.add(traffic); //通过add方法添加图层
                //AMapUI.setDomLibrary($);
                // const AMapUIProtocol = 'https:';
                AMapUI.loadUI(['misc/PositionPicker'], function (PositionPicker) {
                    let positionPicker = new PositionPicker({
                        mode: 'dragMap',
                        map: that.map
                    });
                    positionPicker.start(that.map.getBounds().getSouthWest());
                    positionPicker.on('success', function (positionResult) {
                        that.roadName = positionResult.nearestRoad;
                        //that.centerPosition = positionResult.position;
                        that.onComputeDistance(positionResult.position);
                        //
                        that.dispaly(that.distance);
                        console.log(positionResult);
                    });
                    positionPicker.on('fail', function (positionResult) {

                    });
                });

                window.onload = function () {
                    document.getElementById("container").height = document.documentElement.clientHeight;
                };

                if (device.platform.toLowerCase() == 'ios') {
                    appAvailability.check(
                        'iosamap://', // 高德地图
                        function () {  // Success callback
                            that.gdMap = true;
                        },
                        function () {  // Error callback
                            that.gdMap = false;
                        }
                    );
                    appAvailability.check(
                        'baidumap://', // 百度地图
                        function () {  // Success callback
                            that.bdMap = true;
                        },
                        function () {  // Error callback
                            that.bdMap = false;
                        }
                    );
                    appAvailability.check(
                        'sosomap://', // 腾讯地图
                        function () {  // Success callback
                            that.txMap = true;
                        },
                        function () {  // Error callback
                            that.txMap = false;
                        }
                    );
                    appAvailability.check(
                        'comgooglemaps://', // 谷歌地图
                        function () {  // Success callback
                            that.ggMap = true;
                        },
                        function () {  // Error callback
                            that.ggMap = false;
                        }
                    );
                }

                if (device.platform.toLowerCase() == 'android') {
                    appAvailability.check(
                        'com.autonavi.minimap', // 高德地图
                        function () {           // Success callback
                            that.gdMap = true;
                        },
                        function () {           // Error callback
                            that.gdMap = false;
                        }
                    );
                    appAvailability.check(
                        'com.baidu.BaiduMap', // 百度地图
                        function () {           // Success callback
                            that.bdMap = true;
                        },
                        function () {           // Error callback
                            that.bdMap = false;
                        }
                    );
                    appAvailability.check(
                        'com.tencent.map', // 腾讯地图
                        function () {           // Success callback
                            that.txMap = true;
                        },
                        function () {           // Error callback
                            that.txMap = false;
                        }
                    );
                    appAvailability.check(
                        'com.google.android.apps.maps', // 谷歌地图
                        function () {  // Success callback
                            that.ggMap = true;
                        },
                        function () {  // Error callback
                            that.ggMap = false;
                        });
                }
            });
        },
        watch: {
            distance: {
                handler(newValue) {
                    //根据距离判断停车场是否显示
                    this.dispaly(newValue);
                },
                immediate: true
            }
        },
        methods: {
            audioSearch() {
                console.log('语音');
            },
            dispaly(newValue) {
                this.isdata = false;
                if (this.gyDistance <= newValue * 1000) {
                    this.park2 = true;
                } else {
                    this.park2 = false;
                }
                if (this.zjnDistance <= newValue * 1000) {
                    this.park1 = true;
                } else {
                    this.park1 = false;
                }
                if (this.gyDistance > newValue * 1000 && this.zjnDistance > newValue * 1000) {
                    this.isdata = true;
                }
            },
            changeInput() {
                let that = this;
                AMap.plugin(['AMap.Autocomplete', 'AMap.PlaceSearch'], function () {
                    // 实例化Autocomplete
                    var autoOptions = {
                        // input 为绑定输入提示功能的input的DOM ID
                        city: '南昌市',
                        input: 'input-search'
                    }
                    let autoComplete = new AMap.Autocomplete(autoOptions);
                    // 无需再手动执行search方法，autoComplete会根据传入input对应的DOM动态触发search
                    let placeSearch = new AMap.PlaceSearch({
                        city: '南昌',
                        pageSize: 1,
                        renderStyle: 'default',
                        map: that.map
                    })
                    AMap.event.addListener(autoComplete, 'select', function (e) {
                        that.searchValue = e.poi.name;
                        placeSearch.getDetails(e.poi.id, function (status, result) {
                            if (status === 'complete' && result.info === 'OK') {
                                that.placeSearch_CallBack(result);
                            }
                        });
                    })
                })
            },
            placeSearch_CallBack(data) {
                this.map.remove(this.geoMarker);
                let poiArr = data.poiList.pois;
                //添加marker
                this.geoMarker = new AMap.Marker({
                    map: this.map,
                    //   icon: 'https://webapi.amap.com/ui/1.1/ui/misc/PositionPicker/assets/position-picker.png',
                    zoom: 13,
                    position: poiArr[0].location
                });
                this.map.setCenter(this.geoMarker.getPosition());

            },
            onSearch() {

            },
            onComputeDistance(centerPosition) {
                //115.992995,28.690999,公园
                //115.994383,28.689559,中能
                //计算到中节能停车场的距离
                let gyPosition = new AMap.LngLat(115.992995, 28.690999);
                this.gyDistance = Math.round(centerPosition.distance(gyPosition));
                //计算到公园停车场的距离
                let zjnPosition = new AMap.LngLat(115.994383, 28.689559);
                this.zjnDistance = Math.round(centerPosition.distance(zjnPosition));
            },
            onCancel() {
                this.action = false;
            },
            onSelect(item) {
                this.action = false;
                if (item.name === '高德地图') {
                    //115.992995,28.690999,公园
                    //115.994383,28.689559,中能
                    if (this.lat == 28.690579) {
                        this.lat = 28.690999;
                    } else {
                        this.lat = 28.689559;
                    }
                    if (this.lng == 115.993054) {
                        this.lng = 115.992995;
                    } else {
                        this.lng = 115.994383;
                    }
                }
                if (item.name === '百度地图') {
                    //115.99978,28.697083 ,公园
                    //中节能116.000483,28.695012
                    if (this.lat == 28.690579) {
                        this.lat = 28.697083;
                    } else {
                        this.lat = 28.695012;
                    }
                    if (this.lng == 115.993054) {
                        this.lng = 115.999780;
                    } else {
                        this.lng = 116.000483;
                    }
                }
                if (item.name === '腾讯地图') {
                    //28.691294,115.993266 ,公园
                    //中节能28.689576,115.994393
                    if (this.lat == 28.690579) {
                        this.lat = 28.691294;
                    } else {
                        this.lat = 28.689576;
                    }
                    if (this.lng == 115.993054) {
                        this.lng = 115.993266;
                    } else {
                        this.lng = 115.994393;
                    }
                }
                this.navClick(item.name, this.lat, this.lng);
            },
            more() {
                this.show = !this.show;
                this.park1 = true;
                this.park2 = true;
            },
            onComplete(geolocationResult) {
                this.position.lat = geolocationResult.position.getLat();
                this.position.lng = geolocationResult.position.getLng()
            },
            onError(error) {
                console.log(error.message);
            },
            onConfirm() {
                this.$refs.item.toggle();
            },
            isWeixinBrowser() {
                let ua = navigator.userAgent.toLowerCase();
                return (/micromessenger/.test(ua)) ? true : false;
            },
            navigation(type, lat, lng) {
                //判断是否微信浏览器
                if (this.isWeixinBrowser()) {
                    this.lat = lat;
                    this.lng = lng;
                    this.opactions = [];
                    this.opactions.push({name: '高德地图'});
                    this.opactions.push({name: '百度地图'});
                    this.opactions.push({name: '腾讯地图'});
                    //this.opactions.push({name: '谷歌地图'});
                    this.action = true;
                    this.show = false;
                  //  window.location.href = 'http://uri.amap.com/navigation?from=114.02597366,22.54605355&to=114.029243,22.609562&mode=car&src=nyx_super';
                } else {
                    this.lat = lat;
                    this.lng = lng;
                    this.opactions = [];
                    if (this.gdMap) {
                        this.opactions.push({name: '高德地图'})
                    }
                    if (this.bdMap) {
                        this.opactions.push({name: '百度地图'})
                    }
                    if (this.txMap) {
                        this.opactions.push({name: '腾讯地图'})
                    }
                    if (this.ggMap) {
                        this.opactions.push({name: '谷歌地图'})
                    }
                    if (!(this.ggMap || this.txMap || this.gdMap || this.bdMap)) {
                        //默认高德下载
                        if (device.platform.toLowerCase() == 'android') {
                            cordova.InAppBrowser.open("http://amapdownload.autonavi.com/down6/C3060/Amap_V10.35.2.2736_android_C3060_(Build2005202240).apk", '_system', 'hardwareback=no')
                        } else {
                            window.location.href = "itms-apps://itunes.apple.com/cn/app/%E9%AB%98%E5%BE%B7%E5%9C%B0%E5%9B%BE-%E7%B2%BE%E5%87%86%E5%9C%B0%E5%9B%BE-%E5%AF%BC%E8%88%AA%E5%87%BA%E8%A1%8C%E5%BF%85%E5%A4%87/id461703208?mt=8";
                        }
                        this.action = false;
                    } else {
                        this.action = true;
                    }
                    this.show = false;
                }
            },
            navClick(type, lat, lng) {//item是传递过来的高德地图、百度地图、腾讯地图
                //   var baiduPackageName = "com.baidu.BaiduMap";//百度地图包名
                //   var gaodePackageName = "com.autonavi.minimap";//高德地图包名
                var destination = new Array(lat, lng);//获取vuex中的想去目的地的经纬度参数
                var u = navigator.userAgent;//判断是android系统还是IOS系统
                var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
                //   var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
                // var Destination = this.fromBottomBoxMsg[0].address;
                var geoCoordinates = this.$store.state.geoCoordinates;
                //判断是否微信
                if (this.isWeixinBrowser()) {
                    if (type === "高德地图") {
                        window.location.href = 'http://uri.amap.com/marker?position='+destination[1]+','+destination[0]+'&name=停车场&coordinate=gaode&callnative=1';
                    }
                    if (type === "百度地图"){
                        window.location.href = 'http://api.map.baidu.com/marker?location='+destination[0]+','+destination[1]+'&title=停车场&output=html';
                    }
                    if (type === "腾讯地图"){
                        window.location.href = ' http://apis.map.qq.com/uri/v1/marker?marker=coord:'+destination[0]+','+destination[1]+'&addr:停车场';
                    }

                }
                if (type === "高德地图") {
                    let scheme;
                    if (isAndroid == true) {
                        scheme = 'com.autonavi.minimap';//获取对应系统的高德地图包名
                    } else {
                        scheme = 'iosamap://';
                    }
                    appAvailability.check(scheme, function () {//使用插件的检测包名的函数，判断手机是否安装该第三方APP!!!!!最重要的地方
                        if (isAndroid == true) {
                            var sApp = startApp.set({
                                "action": "ACTION_VIEW",
                                "category": "CATEGORY_DEFAULT",
                                "type": "text/css",
                                "package": "com.autonavi.minimap",
                                "uri": "androidamap://navi?sourceApplication='智慧停车'&poiname=&lat=" + destination[0] + "&lon=" + destination[1] + "&dev=0&style=2",//传递经纬度参数
                                "flags": ["FLAG_ACTIVITY_CLEAR_TOP", "FLAG_ACTIVITY_CLEAR_TASK"],
                                "intentstart": "startActivity"
                            });
                            sApp.start(function () { /* success */
                                //alert("OK");
                            }, function (error) { /* fail */
                                //this.$toast(error);
                            });
                        } else {//若是IOS,则调起ios版本的
                            window.location.href = "iosamap://navi?sourceApplication=applicationName&poiname=&poiid=BGVIS&lat=" + destination[0] + "&lon=" + destination[1] + "&dev=0&style=2";
                        }
                    }, function () {//若没有相应安装相应第三方APP，则弹出链接，提醒下载相应APK
                        if (isAndroid == true) {
                            cordova.InAppBrowser.open("http://amapdownload.autonavi.com/down6/C3060/Amap_V10.35.2.2736_android_C3060_(Build2005202240).apk", '_system', 'hardwareback=no')
                        } else {
                            window.location.href = "itms-apps://itunes.apple.com/cn/app/%E9%AB%98%E5%BE%B7%E5%9C%B0%E5%9B%BE-%E7%B2%BE%E5%87%86%E5%9C%B0%E5%9B%BE-%E5%AF%BC%E8%88%AA%E5%87%BA%E8%A1%8C%E5%BF%85%E5%A4%87/id461703208?mt=8";
                        }
                    });
                } else if (type === "百度地图") {
                    let scheme;
                    if (isAndroid == true) {
                        scheme = 'com.baidu.BaiduMap';
                    } else {
                        scheme = 'baidumap://';
                    }
                    appAvailability.check(scheme, function () {//success callback
                        if (isAndroid == true) {
                            var sApp = startApp.set({
                                /* params */
                                "action": "ACTION_VIEW",
                                "category": "CATEGORY_DEFAULT",
                                "type": "text/css",
                                "package": "com.baidu.BaiduMap",
                                "uri": "baidumap://map/direction?destination=" + destination[0] + "," + destination[1] + "&mode=driving&coord_type= bd09ll&src=ios.baidu.openAPIdemo",
                                "flags": ["FLAG_ACTIVITY_CLEAR_TOP", "FLAG_ACTIVITY_CLEAR_TASK"],
                                "intentstart": "startActivity"
                            });
                            sApp.start(function () { /* success */
                                //alert("OK");
                            }, function (error) { /* fail */
                                //this.$toast(error);
                            });
                        } else {
                            window.location.href = "baidumap://map/direction?destination=" + destination[0] + "," + destination[1] + "&mode=driving&coord_type= bd09ll&src=webapp.navi.yourCompanyName.yourAppName";
                        }
                    }, function () {//
                        if (isAndroid == true) {
                            cordova.InAppBrowser.open("https://map-mobile-lbsapp.cdn.bcebos.com/baidumaplite_AndroidPhone_v10.8.23(2.1.2)_1021366a.apk", '_system', 'hardwareback=no')
                        } else {
                            window.location.href = "itms-apps://itunes.apple.com/cn/app/%E7%99%BE%E5%BA%A6%E5%9C%B0%E5%9B%BE-%E6%99%BA%E8%83%BD%E5%AF%BC%E8%88%AA%E8%B7%AF%E7%BA%BF%E8%A7%84%E5%88%92%E6%97%85%E6%B8%B8%E5%87%BA%E8%A1%8C/id452186370?mt=8";
                        }
                    });
                } else if (type === "腾讯地图") {
                    let scheme;
                    if (isAndroid == true) {
                        scheme = 'com.tencent.map';
                    } else {
                        scheme = 'sosomap://';
                    }
                    appAvailability.check(scheme, function () {//success callback
                        if (isAndroid === true) {
                            var sApp = startApp.set({
                                /* params */
                                "action": "ACTION_VIEW",
                                "category": "CATEGORY_DEFAULT",
                                "type": "text/css",
                                "package": "com.tencent.map",
                                "uri": "qqmap://map/routeplan?type=drive&to=" + "&tocoord=" + destination[0] + "," + destination[1] + "&coord_type=1&policy=0",
                                "flags": ["FLAG_ACTIVITY_CLEAR_TOP", "FLAG_ACTIVITY_CLEAR_TASK"],
                                "intentstart": "startActivity"
                            });
                            sApp.start(function () { /* success */
                                //alert("OK");
                            }, function (error) { /* fail */
                                this.$toast(error);
                            });
                        } else {
                            window.location.href = "qqmap://map/routeplan?type=drive&fromcoord=CurrentLocation&to=" + "&tocoord=" + destination[0] + "," + destination[1] + ""
                        }
                    }, function () {//
                        if (isAndroid === true) {
                            cordova.InAppBrowser.open("https://map.myapp.com/myapp/map/tencentmap_8.1.5.544_android_00002.apk", '_system', 'hardwareback=no')
                        } else {
                            window.location.href = "itms-apps://itunes.apple.com/cn/app/%E8%85%BE%E8%AE%AF%E5%9C%B0%E5%9B%BE-%E8%B7%AF%E5%86%B5%E7%B2%BE%E5%87%86%E5%87%BA%E8%A1%8C%E5%BF%85%E5%A4%87%E7%9A%84%E5%AF%BC%E8%88%AA%E8%BD%AF%E4%BB%B6/id481623196?mt=8"
                        }
                    })
                } else if (type === "苹果地图") {
                    window.location.href = "http://maps.apple.com/?daddr=" + this.position.lat + "," + this.position.lng + "&dirflg=d&t=h"
                } else {
                    this.show = false;
                }
            }
        }
    }
</script>

<style scoped>
    .row-margin {
        padding-top: 10px;;
    }

    .cpicContainer {
        background-color: #ffffff;
    }

    .row-name {
        font-size: 0.4rem;
        font-weight: bold;
    }

    /deep/ .marker img {
        width: 0.8rem;
    }

    /deep/ .markerLable {
        color: white;
        position: fixed;
        margin-top: 0.20rem;
        margin-left: -0.75rem;
        font-weight: bold;
    }

    /deep/ .amap_lib_placeSearch_poi {
        background: url(http://webapi.amap.com/ui/1.0/assets/position-picker2.png) no-repeat !important;
        height: 1.2rem;
        width: 1.0rem;
        font: 0px arial, simsun, sans-serif;
    }

    /deep/ .amap-logo {
        opacity: 0;
    }

    /deep/ .amap-copyright {
        opacity: 0;
    }
</style>