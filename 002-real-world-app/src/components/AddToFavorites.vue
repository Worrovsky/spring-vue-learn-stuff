<template>
  <div>
    <v-btn
      color="success"
      small
      :outlined="!isFavoritedOptimistic"
      @click="handleLike"
    >
      <v-icon>mdi-heart</v-icon> {{ favoritesCountOptimistic }}
    </v-btn>
  </div>
</template>

<script>
import { actionTypes } from '@/store/modules/favorites'

export default {
  name: 'AppAddToFavorites',
  props: {
    isFavorited: {
      type: Boolean,
      required: true,
    },
    articleSlug: {
      type: String,
      required: true,
    },
    favoritesCount: {
      type: Number,
      required: true,
    },
  },
  data() {
    return {
      isFavoritedOptimistic: this.isFavorited,
      favoritesCountOptimistic: this.favoritesCount,
    }
  },
  methods: {
    handleLike() {
      this.$store.dispatch(actionTypes.addToFavorites, {
        slug: this.articleSlug,
        isFavorited: this.isFavoritedOptimistic,
      })

      if (this.isFavoritedOptimistic) {
        this.favoritesCountOptimistic = this.favoritesCountOptimistic - 1
      } else {
        this.favoritesCountOptimistic = this.favoritesCountOptimistic + 1
      }
      this.isFavoritedOptimistic = !this.isFavoritedOptimistic
    },
  },
}
</script>
