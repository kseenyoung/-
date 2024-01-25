<template>
	<video autoplay/>
</template>

<script>
export default {
	name: 'OvVideo',

	props: {
		streamManager: Object,
	},

	mounted () {
		this.streamManager.addVideoElement(this.$el);
	},
};
</script>
<style>
video {
	width: 100%;
	height: 100%;
  object-fit: cover;
}

</style>
