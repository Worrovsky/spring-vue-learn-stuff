<template>
  <v-container>
    <v-row>
      <v-col cols="9">
        <app-feed-toggler />
        <app-feed :api-url="apiUrl" />
      </v-col>
      <v-col cols="3">
        <app-popular-tags />
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import AppFeed from '@/components/Feed'
import AppPopularTags from '@/components/PopularTags'
import AppFeedToggler from '@/components/FeedToggler'

export default {
  name: 'AppGlobalFeed',
  components: {
    AppFeed,
    AppPopularTags,
    AppFeedToggler,
  },
  data() {
    return {
      apiUrl: '/articles',
    }
  },
}
</script>
