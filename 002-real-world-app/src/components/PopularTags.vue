<template>
  <div>
    <app-loading v-if="isLoading" />
    <app-error-message v-if="error" />

    <div v-if="tags" class="grey lighten-3">
      <div>Popular tags</div>
      <router-link
        v-for="(tag, i) in tags"
        :key="i"
        :to="{ name: 'tagsFeed', params: { slug: tag } }"
      >
        <v-btn rounded x-small class="white--text blue-grey lighten-2 mr-1">
          {{ tag }}
        </v-btn>
      </router-link>
    </div>
  </div>
</template>

<script>
import AppLoading from '@/components/Loading'
import AppErrorMessage from '@/components/ErrorMessage'

import { actionTypes } from '@/store/modules/popularTags'
import { mapState } from 'vuex'

export default {
  name: 'AppPopularTags',
  components: {
    AppLoading,
    AppErrorMessage,
  },
  computed: {
    ...mapState({
      isLoading: (state) => state.popularTags.isLoading,
      tags: (state) => state.popularTags.data,
      error: (state) => state.popularTags.error,
    }),
  },
  mounted() {
    this.$store.dispatch(actionTypes.getPopularTags)
  },
}
</script>
