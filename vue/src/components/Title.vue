<template>
   <div class="title">
        <div>
            <h1>{{this.$route.name}}</h1>
        </div>
    </div>
</template>

<script>
export default {
    name:'Title'
}
</script>

<style scoped>
.title{
    position: relative;
    width: auto;
    height: 40px;
}
.title div{
    position: absolute;
    left: -21px;
    line-height: 23px;
    border-left:3px solid rgb(50, 166, 255);
}
.title div h1{
    font-weight: bold;
    margin-left: 20px;
}
</style>