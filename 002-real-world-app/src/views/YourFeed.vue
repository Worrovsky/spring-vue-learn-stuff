<template>
  <v-container>
    <v-row>
      <v-col cols="9">
        <app-feed :api-url="apiUrl" />
      </v-col>
      <v-col cols="3">
        <app-popular-tags />
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import AppFeed from '@/components/Feed.vue'
import AppPopularTags from '@/components/PopularTags.vue'

export default {
  name: 'AppYourFeed',
  components: {
    AppFeed,
    AppPopularTags,
  },
  data() {
    return {
      apiUrl: '/articles/feed',
    }
  },
}
</script>
