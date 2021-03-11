<template>
  <v-container>
    <v-row>
      <v-col cols="9">
        <app-feed :api-url="apiUrl" />
      </v-col>
      <v-col cols="3">
        Tags
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import AppFeed from '@/components/Feed.vue'

export default {
  name: 'AppGlobalFeed',
  components: {
    AppFeed,
  },
  data() {
    return {
      apiUrl: '/articles',
    }
  },
}
</script>
