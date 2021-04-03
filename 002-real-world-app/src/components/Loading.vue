<template>
  <v-container>
    <v-row>
      <v-progress-linear
        color="deep-purple accent-4"
        indeterminate
        rounded
        height="6"
      ></v-progress-linear>
    </v-row>
  </v-container>
</template>

<script setup>
export default {
  name: 'AppLoading',
}
</script>
