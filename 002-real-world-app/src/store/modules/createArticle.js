import articleApi from '@/api/article'

const state = {
  validationErrors: null,
  isSubmitting: false,
}

const preffix = '[create article]'

export const mutationTypes = {
  createArticleStart: `${preffix} Create article start`,
  createArticleSuccess: `${preffix} Create article success`,
  createArticleFailure: `${preffix} Create article failure`,
}

const mutations = {
  [mutationTypes.createArticleStart](state) {
    state.isSubmitting = true
    state.validationErrors = null
  },
  [mutationTypes.createArticleSuccess](state) {
    state.isSubmitting = false
    state.validationErrors = null
  },
  [mutationTypes.createArticleFailure](state, payload) {
    state.isSubmitting = false
    state.validationErrors = payload
  },
}

export const actionTypes = {
  createArticle: `${preffix} create article`,
}

const actions = {
  [actionTypes.createArticle](context, { articleInput }) {
    return new Promise((resolve) => {
      context.commit(mutationTypes.createArticleStart)
      articleApi
        .createArticle(articleInput)
        .then((article) => {
          context.commit(mutationTypes.createArticleSuccess)
          resolve(article)
        })
        .catch((result) => {
          context.commit(
            mutationTypes.createArticleFailure,
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
