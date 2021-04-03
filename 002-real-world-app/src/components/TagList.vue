<template>
  <div>
    <span
      v-for="tag in tags"
      :key="tag"
      class="px-2 py-1 mr-1 white--text blue-grey lighten-1 rounded-pill"
    >
      {{ tag }}
    </span>
  </div>
</template>

<script>
export default {
  name: 'AppTagList',
  props: {
    tags: {
      type: Array,
      require: true,
    },
  },
}
</script>
