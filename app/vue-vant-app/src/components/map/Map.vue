<template>
    <div class="mappage">
        <div class="gdmap" id="map-container"></div>
    </div>
</template>

<script>
    export default {
        name: "Map",
        mounted() {
            new AMap.Map('map-container', {
                zoom: 15,
                center: [116.39, 39.9]
            });
        },
        methods:{

        }
    }
</script>

<style scoped>

</style>