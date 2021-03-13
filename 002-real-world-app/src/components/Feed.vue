<template>
  <div>
    <app-loading v-if="isLoading"></app-loading>

    <app-error-message v-if="error" />

    <div v-if="feed">
      <v-card v-for="(article, i) in feed.articles" :key="i">
        <v-card-title>
          <v-container>
            <v-row>
              <v-col cols="11" class="d-flex">
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
              </v-col>
              <v-col cols="1">
                <div><v-icon>mdi-heart</v-icon></div>
              </v-col>
            </v-row>
          </v-container>
          <p>{{ article.title }}</p>
        </v-card-title>
        <v-card-text>
          {{ article.description }}
        </v-card-text>
      </v-card>
      <!--  -->

      <app-pagination
        :total="feed.articlesCount"
        :limit="limit"
        :url="baseUrl"
        :currentPage="currentPage"
      />
    </div>
  </div>
</template>

<script>
import AppPagination from '@/components/Pagination'
import AppLoading from '@/components/Loading'
import AppErrorMessage from '@/components/ErrorMessage'

import { mapState } from 'vuex'
import { stringify, parseUrl } from 'query-string'

import { actionTypes } from '@/store/modules/feed'
import { itemsLimitForPagination } from '@/utils/variables'

export default {
  name: 'AppFeed',
  components: {
    AppPagination,
    AppLoading,
    AppErrorMessage,
  },
  props: {
    apiUrl: {
      type: String,
      required: true,
    },
  },
  computed: {
    ...mapState({
      isLoading: (state) => state.feed.isLoading,
      feed: (state) => state.feed.data,
      error: (state) => state.feed.error,
    }),
    currentPage() {
      return Number(this.$route.query.page || '1')
    },
    limit() {
      return itemsLimitForPagination
    },
    baseUrl() {
      return this.$route.path
    },
    offset() {
      return this.currentPage * this.limit - this.limit
    },
  },
  watch: {
    currentPage() {
      this.fetchFeed()
    },
  },
  mounted() {
    this.fetchFeed()
  },
  methods: {
    fetchFeed() {
      const parsedUrl = parseUrl(this.apiUrl)
      const queryParams = stringify({
        limit: this.limit,
        offset: this.offset,
        ...parsedUrl.query,
      })
      const urlWithQuery = `${parsedUrl.url}?${queryParams}`
      this.$store.dispatch(actionTypes.getFeed, { apiUrl: urlWithQuery })
    },
  },
}
</script>
