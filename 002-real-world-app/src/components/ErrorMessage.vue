<template>
  <div>{{ message }}</div>
</template>

<script setup>
export default {
  name: 'AppErrorMessage',
  props: {
    message: {
      type: String,
      require: false,
      default: 'Oops!',
    },
  },
}
</script>
