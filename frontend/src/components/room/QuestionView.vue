<template>
    <div class="questionbox">
    <!-- <p class="questiontitle"># 이건 뭐고 저건 뭔가요?</p> -->
    <div class="questionlabel">문</div>
    <p class="questiondetail">이건 뭐고 저건 뭔가요? 이것이 궁급합니다</p>
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