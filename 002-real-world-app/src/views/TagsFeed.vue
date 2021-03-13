<template>
  <v-container>
    <v-row>
      <v-col cols="9">
        <app-feed-toggler :tagName="tagName" />
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
import AppFeedToggler from '@/components/FeedToggler.vue'

export default {
  name: 'AppTagsFeed',
  components: {
    AppFeed,
    AppPopularTags,
    AppFeedToggler,
  },
  computed: {
    tagName() {
      return this.$route.params.slug
    },
    apiUrl() {
      return `/articles?tag=${this.tagName}`
    },
  },
}
</script>
