<template>
  <div>
    <v-progress-linear
      v-if="isLoading"
      color="deep-purple accent-4"
      indeterminate
      rounded
      height="6"
    ></v-progress-linear>

    <div v-if="tags" class="grey lighten-3">
      <div>Popular tags</div>
      <v-btn
        rounded
        x-small
        class="white--text blue-grey lighten-2 mr-1"
        v-for="(tag, i) in tags"
        :key="i"
        to="{name: tag, params: {slug:tag}}"
      >
        {{ tag }}
      </v-btn>
    </div>

    <div v-if="error">Oops {{ error }}</div>
  </div>
</template>

<script>
import { actionTypes } from '@/store/modules/popularTags'
import { mapState } from 'vuex'

export default {
  name: 'AppPopularTags',
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
