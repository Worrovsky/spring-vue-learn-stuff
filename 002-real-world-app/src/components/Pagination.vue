<template>
  <div>
    <v-pagination
      v-model="page"
      :length="pages"
      :total-visible="8"
      @input="nextPage"
    ></v-pagination>
  </div>
</template>

<script>
export default {
  name: 'AppPagination',
  props: {
    total: {
      type: Number,
      required: true,
    },
    limit: {
      type: Number,
      required: true,
    },
    url: {
      type: String,
      required: true,
    },
    currentPage: {
      type: Number,
      required: true,
    },
  },
  computed: {
    pages() {
      return Math.ceil(this.total / this.limit)
    },
  },
  data() {
    return {
      page: this.currentPage,
    }
  },
  methods: {
    nextPage(page) {
      this.$router.push({ path: this.url, query: { page: page } })
    },
  },
}
</script>
