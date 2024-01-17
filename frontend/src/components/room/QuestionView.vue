<template>
    <div class="questionbox">
    <!-- <p class="questiontitle"># 이건 뭐고 저건 뭔가요?</p> -->
    <div class="questionlabel">문</div>
    <div class="questiondetail">
    <p><b>dory</b></p>
    <p >백준 10928번 문제가 dfs로 풀리나요? 시도중인데 잘 안 되네요ㅠㅠ</p>
    </div>
    </div>
</template>

<script setup>

</script>

<style scoped>
/* .questiontitle{
    font-weight: 800;
    text-align: left;
    padding-left: 10px;
    padding-right: 10px;
} */

.questionlabel{
    font-weight: 800;
    padding: 3px;
    font-size: 20px;
    border: 1px black dashed;
}

.questiondetail{
    text-align: left;
    padding-left: 10px;
    padding-right: 10px;
    flex:7;

}

.questionbox{
    border: 2px solid black;
    background-color: white;
    margin-left: 5px;
    margin-right: 5px;
    display: flex;
    padding: 3px;
}
</style>