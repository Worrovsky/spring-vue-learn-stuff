<template>
  <div>
    <app-loading v-if="isLoading" />
    <div v-if="article">
      <h1>{{ article.title }}</h1>
      <div class="d-flex">
        <div class="ma-1 align-self-center">
          <router-link
            :to="{
              name: 'userProfile',
              params: { slug: article.author.username },
            }"
          >
            <v-avatar size="40">
              <v-img :src="article.author.image" alt="user" />
            </v-avatar>
          </router-link>
        </div>
        <div class="d-flex flex-column justify-center">
          <router-link
            class="text-body-1 text-decoration-none green--text text--darken-3"
            :to="{
              name: 'userProfile',
              params: { slug: article.author.username },
            }"
            >{{ article.author.username }}</router-link
          >
          <span class="text-body-2">{{ article.createdAt }}</span>
        </div>
        <div class="ma-3" v-if="isAuthor">
          <v-btn
            outlined
            rounded
            small
            class="ma-2"
            :to="{ name: 'editArticle', params: { slug: article.slug } }"
          >
            <v-icon small>mdi-pencil</v-icon> Edit Article</v-btn
          >
          <v-btn outlined rounded small color="error" @click="onDeleteArticle">
            <v-icon small>mdi-delete</v-icon>
            Delete Article</v-btn
          >
        </div>
      </div>

      <v-container>
        <v-row>
          <v-col>
            {{ article.body }}
          </v-col>
        </v-row>

        <v-row>
          <v-col>
            <app-tag-list :tags="article.tagList" />
          </v-col>
        </v-row>
      </v-container>
    </div>
  </div>
</template>

<script>
import { actionTypes as articleActionTypes } from '@/store/modules/article'
import { getterTypes as authGetterTypes } from '@/store/modules/auth'
import { mapState, mapGetters } from 'vuex'

import AppLoading from '@/components/Loading'
import AppTagList from '@/components/TagList'

export default {
  name: 'AppArticle',
  components: {
    AppLoading,
    AppTagList,
  },
  computed: {
    ...mapState({
      isLoading: (state) => state.article.isLoading,
      article: (state) => state.article.data,
      error: (state) => state.article.error,
    }),
    ...mapGetters({
      currentUser: authGetterTypes.currentUser,
    }),
    isAuthor() {
      if (!this.currentUser || !this.article) {
        return false
      }
      return this.currentUser.username === this.article.author.username
    },
    slug() {
      return this.$route.params.slug
    },
  },
  mounted() {
    this.$store.dispatch(articleActionTypes.getArticle, { slug: this.slug })
  },
  methods: {
    onDeleteArticle() {
      this.$store
        .dispatch(articleActionTypes.deleteArticle, { slug: this.slug })
        .then(() => {
          this.$router.push({ name: 'globalFeed' })
        })
    },
  },
}
</script>
