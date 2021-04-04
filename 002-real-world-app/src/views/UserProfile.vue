<template>
  <v-container v-if="userProfile">
    <v-row>
      <v-col>
        <div class="d-flex flex-column align-center">
          <v-avatar size="80">
            <v-img :src="userProfile.image" alt="user" />
          </v-avatar>
          <h2>{{ userProfile.username }}</h2>
          <p>{{ userProfile.bio }}</p>
          <div>FOLLOW USER BTN</div>
        </div>
        <div class="mx-auto d-flex flex-row">
          <v-spacer></v-spacer>
          <v-btn
            outlined
            small
            v-if="isCurrentUserProfile"
            :to="{ name: 'settings' }"
            >Edit profile</v-btn
          >
        </div>
      </v-col>
    </v-row>

    <v-row>
      <v-col>
        <v-btn
          exact
          x-small
          rounded
          :to="{ name: 'userProfile', params: { slug: userProfile.username } }"
          >My Post</v-btn
        >
        <v-btn
          exact
          x-small
          rounded
          :to="{
            name: 'userProfileFavorites',
            params: { slug: userProfile.username },
          }"
          >Favorites Post</v-btn
        >
        <app-feed :api-url="apiUrl" />
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import { mapGetters, mapState } from 'vuex'
import { actionTypes as userProfileActionTypes } from '@/store/modules/userProfile'
import { getterTypes as authGetterTypes } from '@/store/modules/auth'
import AppFeed from '@/components/Feed'

export default {
  name: 'AppUserProfile',
  components: {
    AppFeed,
  },
  computed: {
    ...mapState({
      isLoading: (state) => state.userProfile.isLoading,
      userProfile: (state) => state.userProfile.data,
      error: (state) => state.userProfile.error,
    }),
    ...mapGetters({
      currentUser: authGetterTypes.currentUser,
    }),
    isCurrentUserProfile() {
      if (!this.currentUser || !this.userProfile) {
        return false
      }
      return this.userProfile.username === this.currentUser.username
    },
    apiUrl() {
      const isFavorites = this.$route.path.includes('favorites')
      return isFavorites
        ? `/articles?favorited=${this.userProfileSlug}`
        : `/articles?author=${this.userProfileSlug}`
    },
    userProfileSlug() {
      return this.$route.params.slug
    },
  },
  watch: {
    userProfileSlug() {
      this.getUserProfile()
    },
  },
  mounted() {
    this.getUserProfile()
  },
  methods: {
    getUserProfile() {
      this.$store.dispatch(userProfileActionTypes.getUserProfile, {
        slug: this.userProfileSlug,
      })
    },
  },
}
</script>
