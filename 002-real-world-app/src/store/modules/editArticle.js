import articleApi from '@/api/article'

const state = {
  validationErrors: null,
  isSubmitting: false,
  isLoading: false,
  article: null,
}

const preffix = '[edit article]'

export const mutationTypes = {
  updateArticleStart: `${preffix} Update article start`,
  updateArticleSuccess: `${preffix} Update article success`,
  updateArticleFailure: `${preffix} Update article failure`,

  getArticleStart: `${preffix} Get article start`,
  getArticleSuccess: `${preffix} Get article success`,
  getArticleFailure: `${preffix} Get article failure`,
}

const mutations = {
  [mutationTypes.updateArticleStart](state) {
    state.isSubmitting = true
    state.validationErrors = null
    state.isLoading = true
    state.article = null
  },
  [mutationTypes.updateArticleSuccess](state, payload) {
    state.isSubmitting = false
    state.validationErrors = null
    state.isLoading = false
    state.article = payload
  },
  [mutationTypes.updateArticleFailure](state, payload) {
    state.isSubmitting = false
    state.validationErrors = payload
    state.isLoading = false
    state.article = null
  },

  [mutationTypes.getArticleStart](state) {
    state.isSubmitting = true
    state.validationErrors = null
    state.isLoading = true
    state.article = null
  },
  [mutationTypes.getArticleSuccess](state, payload) {
    state.isSubmitting = false
    state.validationErrors = null
    state.isLoading = false
    state.article = payload
  },
  [mutationTypes.getArticleFailure](state, payload) {
    state.isSubmitting = false
    state.validationErrors = payload
    state.isLoading = false
    state.article = null
  },
}

export const actionTypes = {
  updateArticle: `${preffix} Update article`,
  getArticle: `${preffix} Get article`,
}

const actions = {
  [actionTypes.updateArticle](context, { slug, articleInput }) {
    return new Promise((resolve) => {
      context.commit(mutationTypes.updateArticleStart)
      articleApi
        .updateArticle(slug, articleInput)
        .then((article) => {
          context.commit(mutationTypes.updateArticleSuccess, article)
          resolve(article)
        })
        .catch((result) => {
          context.commit(
            mutationTypes.updateArticleFailure,
            result.response.data.errors
          )
        })
    })
  },

  [actionTypes.getArticle](context, { slug }) {
    return new Promise((resolve) => {
      context.commit(mutationTypes.getArticleStart)
      articleApi
        .getArticle(slug)
        .then((article) => {
          context.commit(mutationTypes.getArticleSuccess, article)
          resolve(article)
        })
        .catch((result) => {
          context.commit(
            mutationTypes.getArticleFailure,
            result.response.data.errors
          )
        })
    })
  },
}

export default {
  state,
  mutations,
  actions,
}
