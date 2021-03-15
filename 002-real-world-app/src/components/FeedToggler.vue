<template>
  <div>
    <v-btn v-if="isLoggedIn" x-small rounded :to="{ name: 'yourFeed' }"
      >Your Feed</v-btn
    >
    <v-btn x-small rounded :to="{ name: 'home' }" exact>Global Feed</v-btn>
    <v-btn v-if="tagName" x-small rounded :to="{ name: 'tagsFeed' }">
      {{ tagName }}
    </v-btn>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import { getterTypes } from '@/store/modules/auth'

export default {
  name: 'AppFeedToogler',
  props: {
    tagName: {
      type: String,
      require: false,
    },
  },
  computed: {
    ...mapGetters({
      isLoggedIn: getterTypes.isLoggedIn,
    }),
  },
}
</script>

<style scoped>
.border {
  border: 2px dashed orange;
}
</style>
