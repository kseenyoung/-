<template>
  <div class="card-wrapper common-pointer">
    <div class="btn card-tag">{{ tag }}</div>
    <div class="my-card-body">
      <div class="card-body-top">
        <div class="card-title">{{ title }}</div>
        <div class="card-text">
          {{ content }}
        </div>
      </div>
      <div class="card-body-bottom">
        <div class="card-id">{{ userId }}</div>
        <div class="card-date">{{ createdDate }}</div>
      </div>
    </div>
  </div>
</template>

<script setup>
defineProps({
  title: {
    type: String,
    required: true,
  },
  tag: {
    type: [String, Number, Object],
  },
  content: {
    type: String,
  },
  createdDate: {
    type: [String, Date, Number],
  },
  userId: {
    type: String,
  },
});
</script>

<style lang="scss" scoped>
.card-wrapper {
  width: 250px;
  min-height: 200px;
  background-color: transparent;
  color: white;
  border: 2px solid white;
  padding: 15px 18px;
  margin: 15px;
  border-radius: 8px;

  &:hover {
    transform: translateY(-5px);
    box-shadow: 6px 6px 2px rgba(122, 122, 122, 0.486);
  }
  .card-tag {
    background-color: $color-light-3;
    color: black;
    padding: 2px 5px;
    font-weight: bold;
  }
  .my-card-body {
    min-height: 125px;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    .card-body-top {
      padding-top: 10px;
    }
  }
  .card-date {
    font-size: 1rem;
    color: rgb(204, 204, 204);
  }
}
</style>
