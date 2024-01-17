<template>
    <div class="answerbox">
        <!-- <p class="answertitle"># 이건 뭐고 저건 뭡니다.</p> -->
    <div class="answerlabel">답</div>
    <div class="answerdetail">
    <p><b>mory</b></p>
    <p >dfs로 풀되 예외 처리를 하나 해 줘야 합니다. 모든 경우의 수를 잘 생각해보세요.</p>
    </div>
    </div>
</template>

<script setup>

</script>

<style scoped>

/* .answertitle{
    font-weight: 800;
    text-align: left;
    padding-left: 10px;
    padding-right: 10px;
} */

.answerlabel{
    font-weight: 800;
    padding: 3px;
    font-size: 20px;
    border: 1px black dashed;
}
.answerdetail{
    text-align: left;
    padding-left: 10px;
    padding-right: 10px;
    flex: 7;
}

.answerbox{
    border: 2px solid black;

    background-color: azure;
    margin-left: 5px;
    margin-right: 5px;
    display: flex;
    padding: 3px;
}
</style>