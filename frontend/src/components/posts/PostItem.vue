<template>
  <div class="card" >
    <div class="card-body">
      <h5 class="card-title">{{ title }}</h5>
      <button class="btn tag">{{ tag }}</button>
      <p class="card-text">
        {{ content }}
      </p>
      <p class="date-text">{{ createdDate }}</p>
    </div>
  </div>
</template>

<script setup>
defineProps({
  title: {
    type: String,
    required: true
  },
  tag: {
    type: [String, Number, Object]
  },
  content: {
    type: String
  },
  createdDate: {
    type: [String, Date, Number]
  }
})
</script>

<style lang="scss" scoped>
.tag {
  background-color: orange;
  color: white;
  font-size: 12px; 
  padding: 2px 5px;
}
.card {
 width: 70%;
 background-color:transparent;
 color: white;
 border: 2px solid white;
}
.date-text {
  color: white;
}
</style>
