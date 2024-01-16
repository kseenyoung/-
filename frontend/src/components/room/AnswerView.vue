<template>
    <div class="answerbox">
        <!-- <p class="answertitle"># 이건 뭐고 저건 뭡니다.</p> -->
    <div class="answerlabel">답</div>
    <p class="answerdetail">이건 이렇고 저건 저렇기 때문입니다. 어디어디를 참고하세요.</p>
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