<template>
  <div>
    <h1>学员页</h1>
    <h1>学员页</h1>
    </div>
</template>

<script>
export default {
    name:'Student',
    methods:{
        test(){
            this.axios.get('user/test').then(({data})=>{
                console.log(data)
            })
        }
    }
}
</script>

<style>

</style>